package org.net5ijy.springcloud.admin.web.service.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import org.net5ijy.springcloud.admin.common.entity.Role;
import org.net5ijy.springcloud.admin.common.entity.User;
import org.net5ijy.springcloud.admin.web.service.UserFeignClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * org.net5ijy.springcloud.admin.web.service.security.UserDetailsServiceImpl 类
 *
 * @author xuguofeng
 * @date 2019/5/29 12:12
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource
  private UserFeignClient userFeignClient;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userFeignClient.get(username);
    if (user == null) {
      throw new UsernameNotFoundException("Username not found: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), true, true, true, true,
        getGrantedAuthorities(user));
  }

  private Collection<? extends GrantedAuthority> getGrantedAuthorities(User user) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }
    return authorities;
  }
}
