package com.protal.me.web;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("captcha")
public class CaptchaController {

    private static final Logger LOG = LoggerFactory.getLogger(CaptchaController.class);

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("get")
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.算术
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48, 3);
//        LOG.info("captcha : {} -> {}", captcha.getArithmeticString(), captcha.text());

        //2.中文
//        ChineseCaptcha captcha = new ChineseCaptcha(130, 48, 4);
//        LOG.info("captcha : {}", captcha.text());

        //3.中文Gif
//        ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48, 4);
//        LOG.info("captcha : {}", captcha.text());

        //4.Gif
//        GifCaptcha captcha = new GifCaptcha(130, 48, 4);
//        LOG.info("captcha : {}", captcha.text());

        //5.其他
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        captcha.setFont(Captcha.FONT_5);
        LOG.info("captcha : {}", captcha.text());

        CaptchaUtil.out(captcha, request, response);
    }

}