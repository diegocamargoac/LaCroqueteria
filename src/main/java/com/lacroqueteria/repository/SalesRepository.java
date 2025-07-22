package com.lacroqueteria.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lacroqueteria.model.SalesModel;

public interface SalesRepository extends JpaRepository<SalesModel, Long> {

	SalesModel findByDate(LocalDate fecha);
	
}
