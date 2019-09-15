package org.net5ijy.springcloud.admindemo.user.service;

import java.util.List;
import org.net5ijy.springcloud.admindemo.common.entity.User;
import org.net5ijy.springcloud.admindemo.common.util.PageObject;

/**
 * UserService 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:55
 */
public interface UserService {

	/**
	 * 保存用户
	 *
	 * @author xuguofeng
	 * @param user 用户对象
	 * @date 2019/5/29 9:55
	 */
	void addUser(User user);

	/**
	 * 删除用户
	 *
	 * @author xuguofeng
	 * @param id 用户id
	 * @date 2019/5/29 9:55
	 */
	void delUser(Integer id);

	/**
	 * 修改用户
	 *
	 * @author xuguofeng
	 * @param user 用户对象
	 * @date 2019/5/29 9:55
	 */
	void updateUser(User user);

	/**
	 * 根据id查询用户
	 *
	 * @param id 用户id
	 * @return User
	 * @author xuguofeng
	 * @date 2019/5/29 9:45
	 */
	User getUser(Integer id);

	/**
	 * 根据用户名查询用户
	 *
	 * @param username 用户名
	 * @return User
	 * @author xuguofeng
	 * @date 2019/5/29 9:48
	 */
	User getUser(String username);

	/**
	 * 查询全部用户
	 *
	 * @return java.util.List<User>
	 * @author xuguofeng
	 * @date 2019/5/29 9:47
	 */
	List<User> getUsers();

	/**
	 * 分页查询用户
	 *
	 * @param page 页码
	 * @param size 每页数据量
	 * @return java.util.List<User>
	 * @author xuguofeng
	 * @date 2019/5/29 9:48
	 */
	PageObject<User> getUsers(int page, int size);
}
