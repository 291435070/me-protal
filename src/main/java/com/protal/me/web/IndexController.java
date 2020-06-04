package com.protal.me.web;

import com.protal.me.config.LoginInterceptor;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("index")
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger("gds_process_error");

    @PostMapping("login")
    public Object login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        request.getSession().setAttribute(LoginInterceptor.SESSION_USER, username);
        LOG.warn("已登录成功...{}-{}", username, password);

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        LOG.warn("{}...{}", userAgent.getBrowser(), userAgent.getOperatingSystem());

        return "redirect:/index.html";
    }

}