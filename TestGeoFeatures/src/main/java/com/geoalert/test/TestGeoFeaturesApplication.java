package com.geoalert.test;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestGeoFeaturesApplication {
	
	static Logger logger = LoggerFactory.getLogger(TestGeoFeaturesApplication.class);

	public static void main(String[] args) {
		try{
			SpringApplication.run(TestGeoFeaturesApplication.class, args);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

}
