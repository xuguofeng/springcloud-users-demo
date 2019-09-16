package org.net5ijy.springcloud.admindemo.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * org.net5ijy.springcloud.admin.turbine.TurbineApplication 类
 *
 * @author xuguofeng
 * @date 2019/5/29 12:32
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
@EnableHystrix
public class TurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineApplication.class, args);
	}
}
