package com.lacroqueteria.service;

import java.util.List;

import com.lacroqueteria.model.UserModel;

public interface UserService {

	public UserModel findUserByName(String name);
	public Boolean authenticate(String name, String password);
	public String getUserRole(String username);
	public List<UserModel> findAllUsers();
	public UserModel addUser(UserModel userModel);
	
}
