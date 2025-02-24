package com.lacroqueteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lacroqueteria.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

}
