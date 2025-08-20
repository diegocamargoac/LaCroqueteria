package com.lacroqueteria.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lacroqueteria.config.JwtUtil;
import com.lacroqueteria.model.EarningsModel;
import com.lacroqueteria.model.ProductModel;
import com.lacroqueteria.model.ResponseModel;
import com.lacroqueteria.model.SalesModel;
import com.lacroqueteria.model.UserModel;
import com.lacroqueteria.service.EarningsService;
import com.lacroqueteria.service.ProductService;
import com.lacroqueteria.service.SalesService;
import com.lacroqueteria.service.UserService;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private EarningsService earningsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private String ROLE_ADMIN = "ROLE_ADMIN";
	private String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
	
	@PostMapping("/login")
	public ResponseEntity<ResponseModel<Map<String, Object>>> login(
	        @RequestBody UserModel userModel,
	        HttpServletResponse response) {  // <-- inyectamos HttpServletResponse para agregar la cookie

	    if (userModel == null || userModel.getName() == null || userModel.getPassword() == null ||
	        userModel.getName().trim().isEmpty() || userModel.getPassword().trim().isEmpty()) {
	        return ResponseEntity.badRequest()
	                .body(new ResponseModel<>(false, "Nombre de usuario y contraseña requeridos", null));
	    }

	    try {
	        // Autenticación con Spring Security
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(userModel.getName(), userModel.getPassword())
	        );

	        // Obtener detalles del usuario autenticado
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	        // Obtener roles como lista de strings
	        List<String> roles = userDetails.getAuthorities().stream()
	            .map(grantedAuthority -> grantedAuthority.getAuthority())
	            .collect(Collectors.toList());

	        // Generar token JWT
	        String token = jwtUtil.generateToken(userModel.getName(), roles);

	        // Crear cookie con el token JWT
	        Cookie jwtCookie = new Cookie("jwt", token);
	        jwtCookie.setHttpOnly(true);               // No accesible desde JS
	        jwtCookie.setSecure(false);                 // Cambia a true en producción con HTTPS
	        jwtCookie.setPath("/");                      // Cookie válida para toda la app
	        jwtCookie.setMaxAge(24 * 60 * 60);          // Expira en 1 día
	        response.addCookie(jwtCookie);

	        // Buscar usuario para enviar info (puedes simplificar o crear un DTO)
	        UserModel user = userService.findUserByName(userModel.getName());

	        Map<String, Object> responseData = new HashMap<>();
	        responseData.put("token", token); // Opcional, si quieres que también venga en el body
	        responseData.put("user", user);

	        return ResponseEntity.ok(new ResponseModel<>(true, "Bienvenido, " + user.getName(), responseData));

	    } catch (BadCredentialsException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ResponseModel<>(false, "Acceso denegado: credenciales incorrectas", null));
	    }
	}
	
	/*
	// Login for any user
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
*/
	
	// Come back to the session
	@PostMapping("/logout")
	public ResponseEntity<ResponseModel<String>> logout(HttpServletResponse response) {
	    // Crear cookie con mismo nombre "jwt" y valor null, que expire inmediatamente
	    Cookie cookie = new Cookie("jwt", null);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false);       // Cambia a true si usas HTTPS en producción
	    cookie.setPath("/");
	    cookie.setMaxAge(0);           // Expira inmediatamente para borrar la cookie
	    
	    response.addCookie(cookie);

	    return ResponseEntity.ok(new ResponseModel<>(true, "Sesión cerrada correctamente", null));
	}

	
	// Show all users
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
	
	// Add new user
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
	
	// Show all products
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
	
	// Add new product
	@PostMapping("/addProduct")
	public ResponseEntity<ResponseModel<ProductModel>> addProduct(@RequestBody ProductModel productModel) {
	    if (productModel.getBrand() != null && productModel.getPriceCostal() != null && productModel.getPriceKg() != null && productModel.getType() != null) {
	        
	        ProductModel createdProduct = productService.addProduct(productModel);
	        
	        ResponseModel<ProductModel> response = new ResponseModel<>(true, "Producto añadido correctamente", createdProduct);
	        return ResponseEntity.ok(response);
	    } else {
	        ResponseModel<ProductModel> response = new ResponseModel<>(false, "Error al añadir nuevo producto: campos incompletos", null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}
	
	// Delete a product by id
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<ResponseModel<String>> deleteProduct(@PathVariable Long id) {
	    if (id == null) {
	        ResponseModel<String> response = new ResponseModel<>(false, "ID del producto es requerido", null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }

	    Boolean deleted = productService.deleteProductById(id);

	    if (deleted) {
	        ResponseModel<String> response = new ResponseModel<>(true, "Producto eliminado correctamente", null);
	        return ResponseEntity.ok(response);
	    } else {
	        ResponseModel<String> response = new ResponseModel<>(false, "Producto no encontrado", null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}
	
	// New sale
	@PostMapping("/addSale")
	public ResponseEntity<ResponseModel<List<SalesModel>>> addSales(@RequestBody List<SalesModel> salesModel, Authentication auth) {
		try {
			
			if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN) || r.getAuthority().equals(ROLE_EMPLOYEE))) {

			}
			
		    if (salesModel == null || salesModel.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body(new ResponseModel<>(false, "Debe proporcionar al menos una venta válida", null));
		    }

		    List<SalesModel> registredSales = new ArrayList<>();

		    for (SalesModel sale : salesModel) {
		        if (sale.getProduct() == null || sale.getProduct().getId() == null) {
		            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                    .body(new ResponseModel<>(false, "Debe proporcionar un producto válido en todas las ventas", null));
		        }

		        SalesModel registredSale = null;

		        if (sale.getKg() != null && sale.getPrice() == null) {
		        	registredSale = salesService.saleForKg(sale);
		        } else if (sale.getPrice() != null && sale.getKg() == null) {
		        	registredSale = salesService.saleForPrice(sale);
		        } else {
		            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                    .body(new ResponseModel<>(false, "Cada venta debe proporcionar solo KG o solo Precio", null));
		        }

		        if (registredSale != null) {
		        	registredSales.add(registredSale);
		        }
		    }

		    if (!registredSales.isEmpty()) {
		        return ResponseEntity.ok(new ResponseModel<>(true, "Ventas registradas correctamente", registredSales));
		    } else {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body(new ResponseModel<>(false, "Error al registrar las ventas", null));
		    }
		} catch (Exception e) {
			ResponseModel<List<SalesModel>> response = new ResponseModel<>(false, "Error en añadir una venta: " + e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	// Show all sales
	@GetMapping("/getAllSales")
	public ResponseEntity<ResponseModel<List<SalesModel>>> getAllSales(Authentication auth) {
		try {
			
			if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN) || r.getAuthority().equals(ROLE_EMPLOYEE))) {
			    List<SalesModel> sales = salesService.getAllVentas();

			    LocalDate today = LocalDate.now();
			    List<SalesModel> salesToday = sales.stream()
			        .filter(sale -> sale.getDate().equals(today))
			        .collect(Collectors.toList());

			    if (salesToday != null && !salesToday.isEmpty()) {
			        ResponseModel<List<SalesModel>> response = new ResponseModel<>(true, "Ventas de hoy", salesToday);
			        return ResponseEntity.ok(response);
			    } else {
			        ResponseModel<List<SalesModel>> response = new ResponseModel<>(false, "No hay ventas hoy", null);
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			    }
			}
			
		} catch (Exception e) {
			ResponseModel<List<SalesModel>> response = new ResponseModel<>(false, "Error al obtener ventas: " + e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
	// Show all earnings separated for dates
	@GetMapping("/getAllEarnings")
	public ResponseEntity<ResponseModel<List<EarningsModel>>> getAllEarnings(Authentication auth, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		try {

			if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN))) {
				
				List<EarningsModel> earnings = earningsService.getAllEarnings();
				earnings = earnings.stream()
					    .filter(e -> !e.getDate().isBefore(startDate) && !e.getDate().isAfter(endDate))
					    .collect(Collectors.toList());
				if (earnings != null) {
					ResponseModel<List<EarningsModel>> response = new ResponseModel<>(true, "Todas las ganancias", earnings);
			        return ResponseEntity.ok(response);
				} else {
			        ResponseModel<List<EarningsModel>> response = new ResponseModel<>(false, "No hay ganancias", null);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			}

		} catch (Exception e) {
			ResponseModel<List<EarningsModel>> response = new ResponseModel<>(false, "Ocurrió un error: " + e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
