package com.capstone.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private UserMongoDBRepository repository ;

    public static void main(String[] args) {

        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args)throws Exception{
       // repository.deleteAll();

        System.out.println("findall");
        for (UserDTO user : repository.findAll()){
            System.out.println(user);
        }
        System.out.println();
    }

}
