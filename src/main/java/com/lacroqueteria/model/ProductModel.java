package com.lacroqueteria.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Para croquetas
@Entity
@Table(name = "product")
public class ProductModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String brand; // marca
	private String type; // tipo
	private BigDecimal kg;
	private BigDecimal priceKg; // precioKg
	private BigDecimal priceCostal; // precioCostal
	private BigDecimal percentageKg; // porcentajeKg
	private BigDecimal percentagePriceKg; // precioPorcentajeKg
	private BigDecimal salePriceKg; // precioVentaKg
	private BigDecimal percentageCostal; // porcentajeCostal
	private BigDecimal percentagePriceCostal; // precioPorcentajeCostal
	private BigDecimal salePriceCostal; // precioVentaCostal
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getKg() {
		return kg;
	}

	public void setKg(BigDecimal kg) {
		this.kg = kg;
	}

	public BigDecimal getPriceKg() {
		return priceKg;
	}

	public void setPriceKg(BigDecimal priceKg) {
		this.priceKg = priceKg;
	}

	public BigDecimal getPriceCostal() {
		return priceCostal;
	}

	public void setPriceCostal(BigDecimal priceCostal) {
		this.priceCostal = priceCostal;
	}

	public BigDecimal getPercentageKg() {
		return percentageKg;
	}

	public void setPercentageKg(BigDecimal percentageKg) {
		this.percentageKg = percentageKg;
	}

	public BigDecimal getPercentagePriceKg() {
		return percentagePriceKg;
	}

	public void setPercentagePriceKg(BigDecimal percentagePriceKg) {
		this.percentagePriceKg = percentagePriceKg;
	}

	public BigDecimal getSalePriceKg() {
		return salePriceKg;
	}

	public void setSalePriceKg(BigDecimal salePriceKg) {
		this.salePriceKg = salePriceKg;
	}

	public BigDecimal getPercentageCostal() {
		return percentageCostal;
	}

	public void setPercentageCostal(BigDecimal percentageCostal) {
		this.percentageCostal = percentageCostal;
	}

	public BigDecimal getPercentagePriceCostal() {
		return percentagePriceCostal;
	}

	public void setPercentagePriceCostal(BigDecimal percentagePriceCostal) {
		this.percentagePriceCostal = percentagePriceCostal;
	}

	public BigDecimal getSalePriceCostal() {
		return salePriceCostal;
	}

	public void setSalePriceCostal(BigDecimal salePriceCostal) {
		this.salePriceCostal = salePriceCostal;
	}

	public ProductModel(Long id, String brand, String type, BigDecimal kg, BigDecimal priceKg, BigDecimal priceCostal,
			BigDecimal percentageKg, BigDecimal percentagePriceKg, BigDecimal salePriceKg, BigDecimal percentageCostal,
			BigDecimal percentagePriceCostal, BigDecimal salePriceCostal) {
		this.id = id;
		this.brand = brand;
		this.type = type;
		this.kg = kg;
		this.priceKg = priceKg;
		this.priceCostal = priceCostal;
		this.percentageKg = percentageKg;
		this.percentagePriceKg = percentagePriceKg;
		this.salePriceKg = salePriceKg;
		this.percentageCostal = percentageCostal;
		this.percentagePriceCostal = percentagePriceCostal;
		this.salePriceCostal = salePriceCostal;
	}
	
	public ProductModel() {

	}
	
}
