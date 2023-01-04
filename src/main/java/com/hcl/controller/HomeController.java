package com.hcl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

	public String welcome(){
		return "Welcome To Surabhi Restaurant";
	}
}
