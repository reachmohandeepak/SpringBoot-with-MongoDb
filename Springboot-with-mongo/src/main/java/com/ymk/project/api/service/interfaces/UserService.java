package com.ymk.project.api.service.interfaces;

import java.util.List;

import com.ymk.project.api.model.User;
import com.ymk.project.api.projections.NameOnly;

public interface UserService {
	
	User save(User user);
	
	List<NameOnly> getNameOnly(String name);
}
