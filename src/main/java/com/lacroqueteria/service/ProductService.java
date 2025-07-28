package com.lacroqueteria.service;

import java.util.List;

import com.lacroqueteria.model.ProductModel;

public interface ProductService {

	public List<ProductModel> getAllProducts();
	public ProductModel addProduct(ProductModel productModel);
	public Boolean deleteProductById(Long id);
	
}
