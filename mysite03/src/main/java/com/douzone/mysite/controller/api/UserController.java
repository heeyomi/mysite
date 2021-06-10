package com.douzone.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("userApiController")
@RequestMapping("/user/api")
public class UserController {
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public Object checkEmail(@RequestParam(value="email", required = true, defaultValue = "") String email) {
		System.out.println("==========>" + email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		result.put("exit", false); //존재하지 않는다.
		
		return result;
	}
}