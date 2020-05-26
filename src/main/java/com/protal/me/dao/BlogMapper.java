package com.protal.me.dao;

import com.protal.me.model.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface BlogMapper extends BaseInterface<Blog> {

}