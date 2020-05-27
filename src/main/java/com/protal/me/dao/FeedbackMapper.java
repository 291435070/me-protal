package com.protal.me.dao;

import com.protal.me.model.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FeedbackMapper extends BaseInterface<Feedback> {

    @Override
    @Select("SELECT * FROM `t_feedback` WHERE id=#{id}")
    Feedback select(Feedback feedback);
}