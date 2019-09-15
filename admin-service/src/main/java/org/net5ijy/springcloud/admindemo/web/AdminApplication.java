package org.net5ijy.springcloud.admindemo.web;

import org.net5ijy.springcloud.admindemo.common.springmvc.ControllerExceptionHandler;
import org.net5ijy.springcloud.admindemo.common.springmvc.DateConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;

/**
 * org.net5ijy.springcloud.admin.web.AdminApplication 类
 *
 * @author xuguofeng
 * @date 2019/5/29 11:05
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class AdminApplication {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public DateConverter dateConverter() {
    return new DateConverter();
  }

  @Bean
  public ControllerExceptionHandler controllerExceptionHandler() {
    return new ControllerExceptionHandler();
  }

  public static void main(String[] args) {
    SpringApplication.run(AdminApplication.class, args);
  }
}
