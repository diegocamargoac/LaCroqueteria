package com.lacroqueteria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacroqueteria.model.ProductModel;
import com.lacroqueteria.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<ProductModel> getAllProducts() {
		return productRepository.findAll();
	}
	
	public ProductModel addProduct(ProductModel productModel) {
		return productRepository.save(productModel);
	}
	
	public boolean deleteProductById(Long id) {
	    if (productRepository.existsById(id)) { // Verifica si existe
	        productRepository.deleteById(id);
	        return true;
	    }
	    return false;
	}
	
}
