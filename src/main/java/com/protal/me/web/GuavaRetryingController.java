package com.protal.me.web;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.protal.me.model.Feedback;
import com.protal.me.util.ResultBody;
import com.protal.me.util.ResultBody.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("guava")
public class GuavaRetryingController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

    @RequestMapping("retry")
    public Object retry(@RequestBody Feedback feedback) throws Exception {
        LOG.info(feedback.toString());

        AtomicInteger atomic = new AtomicInteger(0);//模拟数据
        Callable<ResultBody> callable = () -> {
            //调用业务逻辑
            atomic.getAndIncrement();
            LOG.info("AtomicInteger -- {} --- {}", atomic.get(), feedback.toString());
            Thread.sleep(300);
            if (atomic.get() == 1) {
                LOG.info("第1次调用失败...");
                return new ResultBody(StatusEnum.FAILURE, atomic.get());
            }
            if (atomic.get() == 2) {
                LOG.info("第2次调用失败......");
                return null;
            }
            return new ResultBody(StatusEnum.SUCCESS, atomic.get());
        };

        Retryer<ResultBody> retryer = RetryerBuilder.<ResultBody>newBuilder()
                .retryIfResult(Predicates.<ResultBody>isNull())//返回对象为null重试
                .retryIfResult(result -> result.getStatus() != 0)//返回状态码不等于0重试
                .retryIfExceptionOfType(Exception.class)//返回异常类型重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))//重试3次
                .withWaitStrategy(WaitStrategies.fixedWait(300, TimeUnit.MICROSECONDS))//重试间隔时长300ms
                .build();

        ResultBody result = retryer.call(callable);
        return result;
    }

}