package com.lacroqueteria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lacroqueteria.model.ProductModel;
import com.lacroqueteria.model.ResponseModel;
import com.lacroqueteria.model.UserModel;
import com.lacroqueteria.service.ProductService;
import com.lacroqueteria.service.UserService;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	// Login para cualquier usuario
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
	
	// Mostrar todo el inventario
	@GetMapping("/getAllProducts")
	public ResponseEntity<ResponseModel<List<ProductModel>>> getAllProducts() {
		List<ProductModel> products = productService.getAllProducts();
		if (products != null) {
			ResponseModel<List<ProductModel>> response = new ResponseModel<>(true, "Todos los productos", products);
	        return ResponseEntity.ok(response);
		} else {
	        ResponseModel<List<ProductModel>> response = new ResponseModel<>(false, "No hay inventario", null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	// Agregar nuevo producto
	@PostMapping("/addProduct")
	public ResponseEntity<ResponseModel<ProductModel>> addProduct(@RequestBody ProductModel productModel) {
	    if (productModel.getInversion() != null && productModel.getMarca() != null && 
	        productModel.getPrecioCostal() != null && productModel.getPrecioKg() != null && 
	        productModel.getTipo() != null) {
	        
	        ProductModel createdProduct = productService.addProduct(productModel);
	        
	        ResponseModel<ProductModel> response = new ResponseModel<>(true, "Producto añadido correctamente", createdProduct);
	        return ResponseEntity.ok(response);
	    } else {
	        ResponseModel<ProductModel> response = new ResponseModel<>(false, "Error al añadir nuevo producto: campos incompletos", null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<ResponseModel<String>> deleteProduct(@PathVariable Long id) {
	    if (id == null) {
	        ResponseModel<String> response = new ResponseModel<>(false, "ID del producto es requerido", null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }

	    boolean deleted = productService.deleteProductById(id);

	    if (deleted) {
	        ResponseModel<String> response = new ResponseModel<>(true, "Producto eliminado correctamente", null);
	        return ResponseEntity.ok(response);
	    } else {
	        ResponseModel<String> response = new ResponseModel<>(false, "Producto no encontrado", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}


	
}
