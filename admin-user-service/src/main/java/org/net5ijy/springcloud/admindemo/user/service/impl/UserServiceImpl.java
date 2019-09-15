package org.net5ijy.springcloud.admindemo.user.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.net5ijy.springcloud.admindemo.common.entity.User;
import org.net5ijy.springcloud.admindemo.common.util.PageObject;
import org.net5ijy.springcloud.admindemo.common.util.PasswordEncoderUtil;
import org.net5ijy.springcloud.admindemo.user.dao.UserDao;
import org.net5ijy.springcloud.admindemo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:58
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {

  private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Resource
  private UserDao userDao;

  @Resource
  private JdbcTemplate jdbcTemplate;

  @Override
  public void addUser(User user) {
    user.setPassword(PasswordEncoderUtil.bCryptPasswordEncode(user
        .getPassword()));
    user.setCreateTime(new Date());
    int row = this.userDao.save(user);
    if (row != 1) {
      throw new RuntimeException("创建用户失败");
    }
  }

  @Override
  public void delUser(Integer id) {
    int row = this.userDao.delete(id);
    if (row != 1) {
      throw new RuntimeException("删除用户失败");
    }
  }

  @Override
  public void updateUser(User user) {
    User tmp = this.getUser(user.getId());
    if (!tmp.getPassword().equals(user.getPassword())) {
      user.setPassword(PasswordEncoderUtil.bCryptPasswordEncode(user
          .getPassword()));
    }
    user.setCreateTime(tmp.getCreateTime());
    int row = this.userDao.update(user);
    if (row != 1) {
      throw new RuntimeException("修改用户失败");
    }
  }

  @Override
  public User getUser(Integer id) {
    return this.userDao.findOne(id);
  }

  @Override
  public User getUser(String username) {
    return this.userDao.findByUsername(username);
  }

  @Override
  public List<User> getUsers() {
    logger.info(String.format("日志框架: %s", logger));
    logger.info(String.format("JdbcTemplate: %s", jdbcTemplate));
    return this.userDao.findAll();
  }

  @Override
  public PageObject<User> getUsers(int page, int size) {
    Page<User> p = new Page<>(page, size);
    List<User> users = this.userDao.findByPage(p);
    p = p.setRecords(users);
    return new PageObject<>(users, Long.valueOf(p.getTotal()).intValue(), p.getCurrent(), p.getSize());
  }
}
