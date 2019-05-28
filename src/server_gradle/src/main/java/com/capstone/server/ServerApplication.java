package com.capstone.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ServerApplication {

//    private static final String PROPERTIES = "spring.config.location=classpath:/google.yml";

    public static void main(String[] args) {
//        new SpringApplicationBuilder(ServerApplication.class)
//                .properties(PROPERTIES)
//                .run(args);


        SpringApplication.run(ServerApplication.class, args);
    }

}
