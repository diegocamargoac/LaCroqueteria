package com.lacroqueteria.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "earnings")
public class EarningsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date; // fecha
	private BigDecimal totalSales; // totalVentas
    private BigDecimal totalEarnings; // totalGanancias
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public EarningsModel(Long id, LocalDate date, BigDecimal totalSales, BigDecimal totalEarnings) {
		this.id = id;
		this.date = date;
		this.totalSales = totalSales;
		this.totalEarnings = totalEarnings;
	}
	
	public EarningsModel() {

	}
	
}
