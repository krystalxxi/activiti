package com.demo.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class ActivitiDemoApplication {
	public static void main(String[] args){
		SpringApplication.run(ActivitiDemoApplication.class,args);
	}

}
