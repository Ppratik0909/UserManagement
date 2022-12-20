package com.user.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.bindings.UserRegForm;
import com.user.service.UserManagementService;

@RestController
public class RegistrationRestController {
	@Autowired
	private UserManagementService service;

	@GetMapping("/email/{emailId}")
	public String emailcheck(@PathVariable String emailId) {
		return service.emailCheck(emailId);

	}

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {
		return service.loadCountries();
	}

	@GetMapping("/states/{countryId")
	public Map<Integer, String> getStates(@PathVariable("countryId") Integer countryId) {
		return service.loadStates(countryId);

	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable("stateId") Integer statesId) {
		return service.loadCities(statesId);
	}

	@PostMapping("/user")
	public String userReg(@RequestBody UserRegForm form) {
		return service.registerUser(form);
	}

}
