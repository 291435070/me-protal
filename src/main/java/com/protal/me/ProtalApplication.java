package com.protal.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    //开启事务注解
@EnableCaching                  //开启缓存注解
@EnableScheduling               //定时任务注解
public class ProtalApplication {

    public static void main(String[] args) {
        //解决elasticsearch与redis关于netty初始化冲突问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(ProtalApplication.class, args);
    }

}