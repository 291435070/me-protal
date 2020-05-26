package com.protal.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProtalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProtalApplication.class, args);
    }

}