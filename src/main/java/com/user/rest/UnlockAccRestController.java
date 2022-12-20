package com.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.bindings.UnlockAccForm;
import com.user.service.UserManagementService;

@RestController
public class UnlockAccRestController {
	@Autowired
	private UserManagementService service;
	
	@PostMapping("/unlock")
	public String unlockAccount(@RequestBody UnlockAccForm unlockAccform) {
		return service.unlockAccount(unlockAccform);
	}
	
	
	}
	
