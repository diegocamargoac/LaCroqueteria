package com.lacroqueteria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lacroqueteria.model.ResponseModel;
import com.lacroqueteria.model.UserModel;
import com.lacroqueteria.repository.UserRepository;
import com.lacroqueteria.service.UserService;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	// Login
	@PostMapping("/login")
	public ResponseEntity<ResponseModel<UserModel>> login(@RequestBody UserModel userModel) {
		
	    if (userModel == null || userModel.getName() == null || userModel.getPassword() == null ||
	        userModel.getName().trim().isEmpty() || userModel.getPassword().trim().isEmpty()) {
	        return ResponseEntity.badRequest()
	                .body(new ResponseModel<>(false, "Nombre de usuario y contraseña requeridos", null));
	    }

	    if (!userService.authenticate(userModel.getName(), userModel.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ResponseModel<>(false, "Acceso denegado: credenciales incorrectas", null));
	    }

	    UserModel user = userService.findUserByName(userModel.getName());

	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new ResponseModel<>(false, "Usuario no encontrado", null));
	    }

	    UserModel userResponse = new UserModel();
	    userResponse.setId(user.getId());
	    userResponse.setName(user.getName());
	    userResponse.setRole(user.getRole());

	    ResponseModel<UserModel> response = new ResponseModel<>(true, "Bienvenido, " + user.getName(), userResponse);
	    return ResponseEntity.ok(response);
	}

	
	// Mostrar todos los usuarios
	@GetMapping("/getAllUsers")
	public ResponseEntity<ResponseModel<List<UserModel>>> getAllUsers() {
	    List<UserModel> users = userService.findAllUsers();
	    
	    if (users.isEmpty()) {
	        ResponseModel<List<UserModel>> response = new ResponseModel<>(false, "No se encontraron usuarios", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	    
	    ResponseModel<List<UserModel>> response = new ResponseModel<>(true, "Todos los usuarios", users);
	    return ResponseEntity.ok(response);
	}
	
	// Agregar nuevo usuario
	@PostMapping("/addUser")
	public ResponseEntity<ResponseModel<UserModel>> addUser(@RequestBody UserModel userModel) {
		UserModel createdUser = userService.addUser(userModel);
		if (userModel.getName() != null && userModel.getPassword() != null) {

		    ResponseModel<UserModel> response = new ResponseModel<>(true, "Usuario creado correctamente", createdUser);
		    return ResponseEntity.ok(response);
		} else {
			ResponseModel<UserModel> response = new ResponseModel<>(false, "Creación de usuario incorrecta", createdUser);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}
	
}
