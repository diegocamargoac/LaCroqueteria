package com.lacroqueteria.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lacroqueteria.model.SalesModel;

public interface SalesRepository extends JpaRepository<SalesModel, Long> {

	SalesModel findByDate(LocalDate fecha);
	
    @Query("SELECT COALESCE(MAX(s.numSale), 0) FROM SalesModel s WHERE s.date = :date")
    Integer findLastNumSale(@Param("date") LocalDate date);
	
}
