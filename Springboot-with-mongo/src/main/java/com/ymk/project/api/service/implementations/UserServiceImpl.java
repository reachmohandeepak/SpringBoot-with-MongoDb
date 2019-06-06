package com.ymk.project.api.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymk.project.api.model.User;
import com.ymk.project.api.projections.NameOnly;
import com.ymk.project.api.repository.UserRepository;
import com.ymk.project.api.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<NameOnly> getNameOnly(String name) {
		return userRepository.findByNameContaining(name);
	}
}
