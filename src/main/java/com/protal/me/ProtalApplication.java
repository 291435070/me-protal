package com.protal.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class ProtalApplication {

    public static void main(String[] args) {
        //解决elasticsearch与redis关于netty初始化冲突问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(ProtalApplication.class, args);
    }

}