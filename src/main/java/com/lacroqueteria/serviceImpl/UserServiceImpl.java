package com.lacroqueteria.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lacroqueteria.model.UserModel;
import com.lacroqueteria.repository.UserRepository;
import com.lacroqueteria.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public UserModel findUserByName(String name) {
		return userRepository.findByName(name);
	}
	
	@Override
	public Boolean authenticate(String name, String password) {
	    UserModel user = findUserByName(name);
	    if (user != null) {
	        return passwordEncoder.matches(password, user.getPassword());
	    }
	    return false;
	}
	
	@Override
    public String getUserRole(String username) {
        UserModel user = findUserByName(username);
        if (user != null && user.getRole() != null) {
            return user.getRole().getRole();
        }
        return null;
    }

	@Override
	public List<UserModel> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public UserModel addUser(UserModel userModel) {
	    userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
	    return userRepository.save(userModel);
	}

}
