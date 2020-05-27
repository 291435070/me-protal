package com.protal.me.web;

import com.protal.me.model.Feedback;
import com.protal.me.service.FeedbackService;
import com.protal.me.util.ResultBody;
import com.protal.me.util.ResultBody.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("save")
    public Object save(@Validated @RequestBody Feedback vo, BindingResult bindingResult) {
        LOG.info(vo.toString());
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBody(StatusEnum.FAILURE, null, msg);
        }

        Feedback feedback = feedbackService.select(vo);
        LOG.info(feedback.toString());

        return new ResultBody(StatusEnum.SUCCESS, vo);
    }

}