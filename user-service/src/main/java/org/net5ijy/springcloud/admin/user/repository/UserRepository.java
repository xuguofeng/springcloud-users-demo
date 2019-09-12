package org.net5ijy.springcloud.admin.user.repository;

import com.baomidou.mybatisplus.plugins.Page;
import java.util.List;
import org.net5ijy.springcloud.admin.common.entity.User;

/**
 * UserRepository 类
 *
 * @author xuguofeng
 * @date 2019/5/29 8:51
 */
public interface UserRepository {

  /**
   * 保存用户
   *
   * @param user 用户对象
   * @return int
   * @author xuguofeng
   * @date 2019/5/29 9:45
   */
  int save(User user);

  /**
   * 修改用户
   *
   * @param user 用户对象
   * @return int
   * @author xuguofeng
   * @date 2019/5/29 9:45
   */
  int update(User user);

  /**
   * 删除用户
   *
   * @param id 用户id
   * @return int
   * @author xuguofeng
   * @date 2019/5/29 9:45
   */
  int delete(Integer id);

  /**
   * 根据id查询用户
   *
   * @param id 用户id
   * @return User
   * @author xuguofeng
   * @date 2019/5/29 9:45
   */
  User findOne(Integer id);

  /**
   * 查询全部用户
   *
   * @return java.util.List<User>
   * @author xuguofeng
   * @date 2019/5/29 9:47
   */
  List<User> findAll();

  /**
   * 分页查询用户
   *
   * @param page 分页信息
   * @return java.util.List<User>
   * @author xuguofeng
   * @date 2019/5/29 9:48
   */
  List<User> findByPage(Page<User> page);

  /**
   * 统计行数
   *
   * @return int
   * @author xuguofeng
   * @date 2019/5/29 9:48
   */
  int count();

  /**
   * 根据用户名查询用户
   *
   * @param username 用户名
   * @return User
   * @author xuguofeng
   * @date 2019/5/29 9:48
   */
  User findByUsername(String username);
}
