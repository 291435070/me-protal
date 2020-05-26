package com.protal.me.service.impl;

import com.protal.me.dao.BlogMapper;
import com.protal.me.model.Blog;
import com.protal.me.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    public boolean delete(Blog blog) {
        return blogMapper.delete(blog);
    }

    @Override
    public boolean update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    public Blog select(Blog blog) {
        return blogMapper.select(blog);
    }

    @Override
    public List<Blog> list(Blog blog) {
        return blogMapper.list(blog);
    }
}