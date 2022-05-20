package com.boot.aws.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
public class SpringBootAwsS3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAwsS3Application.class, args);
	}

}
