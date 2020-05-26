package com.protal.me.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.protal.me.model.Blog;
import com.protal.me.service.BlogService;
import com.protal.me.util.ResultBody;
import com.protal.me.util.ResultBody.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blogs")
public class BlogsController {

    private static final Logger LOG = LoggerFactory.getLogger(BlogsController.class);

    @Autowired
    private BlogService blogService;

    @PostMapping("insert")
    public Object insert(@RequestBody Blog blog) {
        LOG.info(blog.toString());
        boolean result = blogService.insert(blog);
        return new ResultBody(StatusEnum.SUCCESS, result);
    }

    @PostMapping("delete")
    public Object delete(@RequestBody Blog blog) {
        LOG.info(blog.toString());
        boolean result = blogService.delete(blog);
        return new ResultBody(StatusEnum.SUCCESS, result);
    }

    @PostMapping("update")
    public Object update(@RequestBody Blog blog) {
        LOG.info(blog.toString());
        boolean result = blogService.update(blog);
        return new ResultBody(StatusEnum.SUCCESS, result);
    }

    @GetMapping("select")
    public Object select(@RequestBody Blog blog) {
        LOG.info(blog.toString());
        Blog result = blogService.select(blog);
        return new ResultBody(StatusEnum.SUCCESS, result);
    }

    @GetMapping("list")
    public Object list(@RequestBody Blog blog) {
        LOG.info(blog.toString());
        Page<Object> page = PageHelper.startPage(1, 10);
        List<Blog> result = blogService.list(blog);
        LOG.info("count:{}", page.getTotal());
        return new ResultBody(StatusEnum.SUCCESS, result);
    }

}