package com.lacroqueteria.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sale")
public class SalesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal kg;
	private BigDecimal price; // precio
	private BigDecimal totalSales; //totalVenta
	private BigDecimal earnings;// ganancia
	private LocalDate date; // fecha
	private LocalTime hour; // hora
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = true)
	private ProductModel product;
	/*
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserModel userModel;
*/
	
	private String seller; // vendedor
	private Integer numSale; // numeroVenta
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getKg() {
		return kg;
	}

	public void setKg(BigDecimal kg) {
		this.kg = kg;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public BigDecimal getEarnings() {
		return earnings;
	}

	public void setEarnings(BigDecimal earnings) {
		this.earnings = earnings;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getHour() {
		return hour;
	}

	public void setHour(LocalTime hour) {
		this.hour = hour;
	}

	public ProductModel getProduct() {
		return product;
	}

	public void setProduct(ProductModel product) {
		this.product = product;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Integer getNumSale() {
		return numSale;
	}

	public void setNumSale(Integer numSale) {
		this.numSale = numSale;
	}

	public SalesModel(Long id, BigDecimal kg, BigDecimal price, BigDecimal totalSales, BigDecimal earnings,
			LocalDate date, LocalTime hour, ProductModel product, String seller, Integer numSale) {
		this.id = id;
		this.kg = kg;
		this.price = price;
		this.totalSales = totalSales;
		this.earnings = earnings;
		this.date = date;
		this.hour = hour;
		this.product = product;
		this.seller = seller;
		this.numSale = numSale;
	}

	public SalesModel() {

	}
	
}
