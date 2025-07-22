package com.lacroqueteria.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lacroqueteria.model.EarningsModel;

public interface EarningsRepository extends JpaRepository<EarningsModel, Long>{

	EarningsModel findByDate(LocalDate fecha);
	
}
