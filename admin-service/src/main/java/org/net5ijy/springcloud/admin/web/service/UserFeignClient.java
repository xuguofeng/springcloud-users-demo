package org.net5ijy.springcloud.admin.web.service;

import org.net5ijy.springcloud.admin.common.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * org.net5ijy.springcloud.admin.web.service.UserFeignClient 类
 *
 * @author xuguofeng
 * @date 2019/5/29 11:23
 */
@FeignClient(name = "user-service", fallback = UserFeignClientFactory.class)
public interface UserFeignClient {

	/**
	 * get
	 *
	 * @author xuguofeng
	 * @param username 用户名
	 * @return org.net5ijy.springcloud.admin.common.entity.User
	 * @date 2019/5/29 11:23
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}")
	User get(@PathVariable("username") String username);
}

@Component
class UserFeignClientFactory implements UserFeignClient {

	@Override
	public User get(String username) {
		return new User();
	}
}
