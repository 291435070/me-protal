package com.protal.me.dao;

import com.protal.me.model.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BlogMapper extends BaseInterface<Blog> {

    @Select("SELECT * FROM `t_blog` WHERE id <#{id} ORDER BY id DESC LIMIT 1")
    Blog prev(@Param("id") long id);

    @Select("SELECT * FROM `t_blog` WHERE id >#{id} ORDER BY id  LIMIT 1")
    Blog next(@Param("id") long id);

}