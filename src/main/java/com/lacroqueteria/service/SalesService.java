package com.lacroqueteria.service;
import java.time.LocalDate;
import java.util.List;

import com.lacroqueteria.model.SalesModel;

public interface SalesService {

	public List<SalesModel> getAllVentas();
	public SalesModel saleForKg(SalesModel salesModel);
	public SalesModel saleForPrice(SalesModel salesModel);
	public void updateDailyEarnings(SalesModel sale);
    public int getNextNumSale(LocalDate fecha);
    public List<SalesModel> findBySeller(String seller);
	
}
