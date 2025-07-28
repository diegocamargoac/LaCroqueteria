package com.lacroqueteria.service;

import java.util.List;

import com.lacroqueteria.model.SalesModel;

public interface SalesService {

	public List<SalesModel> getAllVentas();
	public SalesModel saleForKg(SalesModel salesModel);
	public SalesModel saleForPrice(SalesModel salesModel);
	
}
