package com.moa.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.moa.common.entity", "com.moa.admin.user"})
public class MealkitBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealkitBackEndApplication.class, args);
	}

}
