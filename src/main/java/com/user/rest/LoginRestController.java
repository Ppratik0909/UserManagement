package com.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.bindings.LoginForm;
import com.user.service.UserManagementService;

@RestController
public class LoginRestController {
	@Autowired
	private UserManagementService service;

	@PostMapping("/login")
	public String login(@RequestBody LoginForm form) {
		return service.Login(form);
	}
}
