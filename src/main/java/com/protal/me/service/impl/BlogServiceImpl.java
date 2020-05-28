package com.protal.me.service.impl;

import com.protal.me.dao.BlogMapper;
import com.protal.me.model.Blog;
import com.protal.me.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "protal:blog")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    @Transactional
    public boolean insert(Blog blog) {
        boolean result = blogMapper.insert(blog);
//        int i = 1 / 0;
        return result;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean delete(Blog blog) {
        return blogMapper.delete(blog);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    @Cacheable(key = "'vo_'+#blog.id", condition = "#blog!=null", unless = "#result==null")
    public Blog select(Blog blog) {
        return blogMapper.select(blog);
    }

    @Override
    public List<Blog> list(Blog blog) {
        return blogMapper.list(blog);
    }
}