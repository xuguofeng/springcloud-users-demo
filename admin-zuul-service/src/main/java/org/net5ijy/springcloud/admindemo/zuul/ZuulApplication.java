package org.net5ijy.springcloud.admindemo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * org.net5ijy.springcloud.admin.zuul.ZuulApplication 类
 *
 * @author xuguofeng
 * @date 2019/5/29 12:27
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
