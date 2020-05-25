package com.protal.me.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class IndexController {

	@RequestMapping("search")
	public Object save() {
		return "/search";
	}

}