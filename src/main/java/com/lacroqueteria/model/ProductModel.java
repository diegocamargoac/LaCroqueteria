package com.lacroqueteria.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String marca;
	private String tipo;
	private BigDecimal inversion;
	private BigDecimal precioKg;
	private BigDecimal precioCostal;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getInversion() {
		return inversion;
	}

	public void setInversion(BigDecimal inversion) {
		this.inversion = inversion;
	}

	public BigDecimal getPrecioKg() {
		return precioKg;
	}

	public void setPrecioKg(BigDecimal precioKg) {
		this.precioKg = precioKg;
	}

	public BigDecimal getPrecioCostal() {
		return precioCostal;
	}

	public void setPrecioCostal(BigDecimal precioCostal) {
		this.precioCostal = precioCostal;
	}
	
	public ProductModel(Long id, String marca, String tipo, BigDecimal inversion, BigDecimal precioKg,
			BigDecimal precioCostal) {
		this.id = id;
		this.marca = marca;
		this.tipo = tipo;
		this.inversion = inversion;
		this.precioKg = precioKg;
		this.precioCostal = precioCostal;
	}

	public ProductModel() {
	}	
	
}
