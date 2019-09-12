package org.net5ijy.springcloud.admin.user.dao;

import com.baomidou.mybatisplus.plugins.Page;
import java.util.List;
import javax.annotation.Resource;
import org.net5ijy.springcloud.admin.common.entity.User;
import org.net5ijy.springcloud.admin.user.repository.UserRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * UserDaoImpl 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:51
 */
@CacheConfig(cacheNames = "userCache")
@Repository
public class UserDaoImpl implements UserDao {

  @Resource
  private UserRepository userRepository;

  @CachePut(key = "#p0.id+'_org.net5ijy.springcloud.admin.common.entity.User'")
  @Override
  public int save(User user) {
    return userRepository.save(user);
  }

  @CachePut(key = "#p0.id+'_org.net5ijy.springcloud.admin.common.entity.User'")
  @Override
  public int update(User user) {
    return userRepository.update(user);
  }

  @CacheEvict(key = "#p0+'_org.net5ijy.springcloud.admin.common.entity.User'")
  @Override
  public int delete(Integer id) {
    return userRepository.delete(id);
  }

  @Cacheable(key = "#p0+'_org.net5ijy.springcloud.admin.common.entity.User'")
  @Override
  public User findOne(Integer id) {
    return userRepository.findOne(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public List<User> findByPage(Page<User> page) {
    return userRepository.findByPage(page);
  }

  @Override
  public int count() {
    return userRepository.count();
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
