package com.lacroqueteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lacroqueteria.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

	UserModel findByName(String name);
	
}
