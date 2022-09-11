package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repositories.LoginRepo;
import com.example.demo.models.Login_page;
import java.util.*;

@RestController
public class LoginController {

	@Autowired
	private LoginRepo loginRepo;

	//just to test and see if user created is there in the database
	@GetMapping("/login/{id}")
	Optional<Login_page> getId(@PathVariable Integer id) {
		return loginRepo.findById(id);
	}
	
	@PostMapping(path = "/login/register")
	public Map registration(@RequestBody Login_page login) {

		Map<String, String> exists = Collections.singletonMap("Status","Username Exists");
		Map<String, String> success = Collections.singletonMap("Status","Successful");

		if(loginRepo.findByUsername(login.getUsername())==null) {
			loginRepo.save(login);

			return success;
		}

		else return exists;

	}


	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/login")
	public Map authenticate(@RequestBody Login_page login) {

		Map<String, String> success = Collections.singletonMap("Status","Login Success");
		Map<String, String> fail = Collections.singletonMap("Status","Login Fail");
		Map<String, String> name_fail = Collections.singletonMap("Status","Username not found!");

		if(loginRepo.findByUsername(login.getUsername())==null) {
			return name_fail;
		}

		String pass=login.getPassword();
		String correctPass=loginRepo.findByUsername(login.getUsername()).getPassword();


		if(pass.equals(correctPass)) {
			return success;
		}

		else return fail;

	}

}
