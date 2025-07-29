package com.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class Test {

	/**
	 * 测试接口
	 * @return
	 */
	@GetMapping("/")
	public String get(){
		return "<h3 style=\"text-align: center;\">服务已启动...</h3>";
	}
}
