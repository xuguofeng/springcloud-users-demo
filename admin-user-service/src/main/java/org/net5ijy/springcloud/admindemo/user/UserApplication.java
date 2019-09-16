package org.net5ijy.springcloud.admindemo.user;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.net5ijy.springcloud.admindemo.common.springmvc.DateConverter;
import org.net5ijy.springcloud.admindemo.common.springmvc.GlobalControllerExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * UserApplication 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:31
 */
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("org.net5ijy.springcloud.admindemo.user.repository")
@EnableCaching
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class UserApplication {

  @Bean
  public DateConverter dateConverter() {
    return new DateConverter();
  }

  @Bean
  public GlobalControllerExceptionHandler globalControllerExceptionHandler() {
    return new GlobalControllerExceptionHandler();
  }

  @Bean
  public ServletRegistrationBean getServlet() {
    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
    ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>(streamServlet);
    registrationBean.setLoadOnStartup(1);
    registrationBean.addUrlMappings("/hystrix.stream");
    registrationBean.setName("HystrixMetricsStreamServlet");
    return registrationBean;
  }

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
