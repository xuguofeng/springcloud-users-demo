package org.net5ijy.springcloud.admindemo.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.net5ijy.springcloud.admindemo.common.entity.User;
import org.net5ijy.springcloud.admindemo.common.util.PageObject;
import org.net5ijy.springcloud.admindemo.common.util.ResponseMessage;
import org.net5ijy.springcloud.admindemo.user.service.UserService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Resource
  private UserService userService;

  @PostMapping("/add")
  @ResponseBody
  public ResponseMessage add(@RequestBody User user) {
    this.userService.addUser(user);
    return ResponseMessage.success();
  }

  @PostMapping("/update")
  @ResponseBody
  public ResponseMessage update(@RequestBody User user) {
    this.userService.updateUser(user);
    return ResponseMessage.success();
  }

  @PostMapping("/{id}")
  public ResponseMessage del(@PathVariable Integer id) {
    this.userService.delUser(id);
    return ResponseMessage.success();
  }

  @GetMapping("/{id}")
  @ResponseBody
  public User user(@PathVariable Integer id) {
    return this.userService.getUser(id);
  }

  @GetMapping("/username/{username}")
  public User getUserByUsername(@PathVariable String username) {
    return this.userService.getUser(username);
  }

  @GetMapping("/list")
  public List<User> list() {
    return this.userService.getUsers();
  }

  @PostMapping("/users")
  @HystrixCommand(fallbackMethod = "listFallBack", commandProperties = {
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public PageObject<User> list(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return this.userService.getUsers(page, size);
  }

  public PageObject<User> listFallBack(int page, int size) {
    Assert.isTrue(page >= 1, "");
    Assert.isTrue(size >= 1, "");
    List<User> users = new ArrayList<>();
    return new PageObject<>(users, 0, page, size);
  }
}
