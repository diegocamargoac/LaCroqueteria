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
	private Long gramos;
	private BigDecimal pesos;
	
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
	public Long getGramos() {
		return gramos;
	}
	public void setGramos(Long gramos) {
		this.gramos = gramos;
	}
	public BigDecimal getPesos() {
		return pesos;
	}
	public void setPesos(BigDecimal pesos) {
		this.pesos = pesos;
	}
	public ProductModel(Long id, String marca, String tipo, Long gramos, BigDecimal pesos) {
		this.id = id;
		this.marca = marca;
		this.tipo = tipo;
		this.gramos = gramos;
		this.pesos = pesos;
	}
	public ProductModel() {
	}	
	
}
