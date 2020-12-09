package com.test.SpringTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {
//            new Thread(() -> {
//                try {
//                    testService.testA();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();

            testService.testB();
        };
    }

}
