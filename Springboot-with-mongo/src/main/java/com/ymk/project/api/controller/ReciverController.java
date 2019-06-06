package com.ymk.project.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ymk.project.api.model.User;
import com.ymk.project.api.projections.NameOnly;
import com.ymk.project.api.service.interfaces.UserService;


@RestController
public class ReciverController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public List<NameOnly> greeting() {
		String name = "Test User";
		return userService.getNameOnly(name);
	}
	@PostMapping("/")
	public String getMessage(){
		long startTime = System.nanoTime();
		User user = new User();
		user.setName("Name");
		user.setUserId("1");
		//userService.getNameOnly("test");
		try{
		System.out.println(new Gson().toJson(user));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		//User user1 = new Gson().fromJson(new Gson().toJson(user),User.class);
		
		long endTime = System.nanoTime();
		long durationInNano = (endTime - startTime);
		System.out.println(TimeUnit.NANOSECONDS.toMillis(durationInNano));
		return "Hello World";
	}
}
