package com.dataPoa.dataPoaRestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("dataPoa.dataPoaRestAPI.models")
@SpringBootApplication
public class DataPoaRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataPoaRestApiApplication.class, args);
	}
}
