package com.lacroqueteria.service;

import java.util.List;

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
	
}
