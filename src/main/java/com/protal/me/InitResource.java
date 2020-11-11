package com.protal.me;

import com.protal.me.dao.BlogRepository;
import com.protal.me.model.Blog;
import com.protal.me.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitResource implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(InitResource.class);

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("所有Bean初始化完成，获取mysql数据导入ES...");
        long count = blogRepository.count();
        if (0 == count) {
            List<Blog> blogs = blogService.list(null);
//            blogs.forEach(blog -> blogRepository.save(blog));
        }
    }
}