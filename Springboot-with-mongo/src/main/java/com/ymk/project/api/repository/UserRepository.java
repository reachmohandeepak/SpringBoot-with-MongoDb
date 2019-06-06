package com.ymk.project.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ymk.project.api.model.User;
import com.ymk.project.api.projections.NameOnly;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	List<NameOnly> findByNameContaining(String name);
}
