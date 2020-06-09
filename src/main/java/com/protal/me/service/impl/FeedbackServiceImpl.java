package com.protal.me.service.impl;

import com.protal.me.dao.FeedbackMapper;
import com.protal.me.model.Feedback;
import com.protal.me.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public boolean insert(Feedback feedback) {
        return feedbackMapper.insert(feedback);
    }

    @Override
    public boolean delete(Feedback feedback) {
        return false;
    }

    @Override
    public boolean update(Feedback feedback) {
        return false;
    }

    @Override
    public Feedback select(Feedback feedback) {
        return feedbackMapper.select(feedback);
    }

    @Override
    public List<Feedback> list(Feedback feedback) {
        return null;
    }
}