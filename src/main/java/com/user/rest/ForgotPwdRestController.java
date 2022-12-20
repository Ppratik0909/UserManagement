package com.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserManagementService;

@RestController
public class ForgotPwdRestController {
	@Autowired
	private UserManagementService service;
	
	@GetMapping("/forgotPwd/{emailId}")
	public String ForgotPwd(@PathVariable   String emailId) {
		return service.forgotPwd(emailId);
	}
	

}
