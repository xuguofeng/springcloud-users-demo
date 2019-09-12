package org.net5ijy.springcloud.admin.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.net5ijy.springcloud.admin.common.entity.User;
import org.net5ijy.springcloud.admin.common.util.PageObject;
import org.net5ijy.springcloud.admin.common.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * org.net5ijy.springcloud.admin.web.controller.UserController 类
 *
 * @author xuguofeng
 * @date 2019/5/29 11:14
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private static final String USER_SERVICE_URL = "http://user-service/user/";

  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  @Resource
  private RestTemplate restTemplate;

  @GetMapping("/add")
  public ModelAndView add() {
    return new ModelAndView("user/add");
  }

  @PostMapping("/create")
  public void create(User user, final MultipartFile photo,
      HttpServletResponse response) throws IOException {

    if (photo != null) {
      System.out.println(photo.getOriginalFilename());
      System.out.println(photo.getSize());
    }

    // 设置创建时间
    user.setCreateTime(new Date());
    // User对象转JSON
    ObjectMapper mapper = new ObjectMapper();
    String mapJakcson = mapper.writeValueAsString(user);
    // 请求头
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
    // 发送请求
    HttpEntity<String> strEntity = new HttpEntity<>(mapJakcson, headers);
    ResponseMessage resp = this.restTemplate
        .postForObject(USER_SERVICE_URL + "add", strEntity, ResponseMessage.class);
    // DEBUG
    logger.info(String.format("日志框架: %s", logger));
    logger.info(String.format("%s请求获得相应%s", USER_SERVICE_URL + "add", resp));
    response.sendRedirect("/user/list");
  }

  @PostMapping("/d/{id}")
  public ResponseMessage del(@PathVariable Integer id) {
    ResponseMessage resp = this.restTemplate
        .postForObject(USER_SERVICE_URL + id, null, ResponseMessage.class);
    logger.info(String.format("%s请求获得相应%s", USER_SERVICE_URL + id, resp));
    return resp;
  }

  @GetMapping("/e/{id}")
  public ModelAndView edit(@PathVariable("id") Integer id, Model model) {
    User user = this.restTemplate.getForObject(USER_SERVICE_URL + id, User.class);
    model.addAttribute("user", user);
    return new ModelAndView("user/edit", "userModel", model);
  }

  @PostMapping("/update")
  public void update(User user, HttpServletResponse response)
      throws IOException {
    // User对象转JSON
    ObjectMapper mapper = new ObjectMapper();
    String mapJakcson = mapper.writeValueAsString(user);
    // 请求头
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
    // 发送请求
    HttpEntity<String> strEntity = new HttpEntity<>(mapJakcson, headers);
    ResponseMessage resp = this.restTemplate
        .postForObject(USER_SERVICE_URL + "update", strEntity, ResponseMessage.class);
    logger.info(String.format("%s请求获得相应%s", USER_SERVICE_URL + "update", resp));
    response.sendRedirect("/user/list");
  }

  @GetMapping("/list")
  public ModelAndView list() {
    return new ModelAndView("user/list");
  }

  @SuppressWarnings("unchecked")
  @PostMapping("/page")
  @ResponseBody
  @HystrixCommand(fallbackMethod = "pageListFallBack", commandProperties = {
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public PageObject<User> pageList(
      @RequestParam(name = "page", required = false, defaultValue = "1") int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") int size,
      HttpServletRequest request) {

    logger.info("IP: " + request.getRemoteAddr() + ", PORT: "
        + request.getRemotePort() + ", 访问用户列表");

    String u = String.format("users?page=%s&size=%s", page, size);
    return this.restTemplate.postForObject(USER_SERVICE_URL + u, null, PageObject.class);
  }

  @SuppressWarnings({"unused"})
  public PageObject<User> pageListFallBack(int page, int size,
      HttpServletRequest request) {
    List<User> users = new ArrayList<>();
    return new PageObject<>(null, 0, page, size);
  }
}
