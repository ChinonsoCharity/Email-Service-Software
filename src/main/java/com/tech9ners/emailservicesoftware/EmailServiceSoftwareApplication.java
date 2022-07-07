package com.tech9ners.emailservicesoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EmailServiceSoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceSoftwareApplication.class, args);
    }

}
