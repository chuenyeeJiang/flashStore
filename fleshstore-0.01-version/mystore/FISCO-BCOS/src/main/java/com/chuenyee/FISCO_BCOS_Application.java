package com.chuenyee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FISCO_BCOS_Application {

	
	
	public static void main(String[] args) {
	//	ApplicationContext applicationContext =
				SpringApplication.run(FISCO_BCOS_Application.class, args);
	}

}
