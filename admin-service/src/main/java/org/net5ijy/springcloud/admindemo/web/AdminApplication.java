package org.net5ijy.springcloud.admindemo.web;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.net5ijy.springcloud.admindemo.common.springmvc.ControllerExceptionHandler;
import org.net5ijy.springcloud.admindemo.common.springmvc.DateConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
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
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
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

  @Bean
  public ServletRegistrationBean getServlet() {
    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
    ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>();
    registrationBean.setServlet(streamServlet);
    registrationBean.setLoadOnStartup(1);
    registrationBean.addUrlMappings("/hystrix.stream");
    registrationBean.setName("HystrixMetricsStreamServlet");
    return registrationBean;
  }

  public static void main(String[] args) {
    SpringApplication.run(AdminApplication.class, args);
  }
}
