package org.joe.ocw.service.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 方便扫描到放在common下的类
@ComponentScan({"org.joe.ocw"})
@EnableDiscoveryClient
// @EnableFeignClients注解默认也是会扫描注解所在包的当前包及子包，如果需要扫描其他包下的FeignClient，需要单独使用属性指定
// @SpringBootApplicatoin是用的@ComponentScan扫描，扫描的是Component，包括@Component, @Controller, @Service, @Repository等，
// 而@EnableFeignClients扫描的是@FeignClient，所以在指定扫描路径时要分别指定
@EnableFeignClients(basePackages = "org.joe.ocw.feign")
public class ServiceEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEduApplication.class, args);
    }
}
