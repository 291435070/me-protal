package com.protal.me.job;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimerTask {

    private static final Logger LOG = LoggerFactory.getLogger(TimerTask.class);

    @Scheduled(cron = "0 0/10 * * * ?")
    public void refresh() {
        LOG.info("{} --->>> {}", LocalDateTime.now(), RandomStringUtils.randomAlphanumeric(10));
    }
}