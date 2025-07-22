package com.lacroqueteria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lacroqueteria.model.UserModel;
import com.lacroqueteria.service.UserService;

@Controller
public class UserThymleafController {
	
	@Autowired
	private UserService userService;
	
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/navegador")
    public String navPage() {
        return "navegador";
    }
    
    // Emplooys
    @GetMapping("/inicio")
    public String inicio() {
    	return "inicio";
    }
    
    // Admin
    @GetMapping("/inicioAdmin")
    public String inicioAdmin() {
    	return "inicioAdmin";
    }
    
    @GetMapping("/resumenAdmin")
    public String resumenAdmin() {
    	return "resumenAdmin";
    }
    
    @GetMapping("/inventarioAdmin")
    public String inventarioAdmin() {
    	return "inventarioAdmin";
    }
    
    @GetMapping("/inventario")
    public String inventario() {
    	return "inventario";
    }
    
    @GetMapping("/resumen")
    public String resumen() {
    	return "resumen";
    }
    
    /*
    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
	*/
    /*
    @PostMapping("/login")
    public String login(@RequestBody String name, String password, Model model) {
    	if (userService.authenticate(name, password)) {
    		String role = userService.getUserRole(name);
    		
    		if (role != null) {
				if (role.equals("ADMIN")) {
					model.addAttribute("message", "Hola admin");
				} else if (role.equals("EMPLOYEE")) {
					model.addAttribute("message", "Hola usuario");
				}
			} else {
				model.addAttribute("message", "Rol no encontrado");
			}
    		return "error";
    	} else {
    		model.addAttribute("error", "Credenciales incorrectas");
    		return "login";
    	}
    }
    */
    @GetMapping("/getAllUsers")
    public String getAllUsers(Model model) {
        List<UserModel> users = userService.findAllUsers();
        
        if (users.isEmpty()) {
            model.addAttribute("message", "No se encontraron usuarios");
            return "error";
        }
        
        model.addAttribute("users", users);
        return "userList";
    }

}
