package com.lacroqueteria.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacroqueteria.model.EarningsModel;
import com.lacroqueteria.model.ProductModel;
import com.lacroqueteria.model.SalesModel;
import com.lacroqueteria.repository.EarningsRepository;
import com.lacroqueteria.repository.ProductRepository;
import com.lacroqueteria.repository.SalesRepository;
import com.lacroqueteria.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {
	
	@Autowired
	private SalesRepository salesRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private EarningsRepository earningsRepository;
	
	@Override
	public List<SalesModel> getAllVentas() {
		return salesRepository.findAll();
	}
	
	// Method for sales based on KG
	@Override
	public SalesModel saleForKg(SalesModel salesModel) {
	    if (salesModel.getProduct() != null && salesModel.getProduct().getId() != null) {
	        ProductModel productModel = productRepository.findById(salesModel.getProduct().getId()).orElse(null);

	        if (productModel != null && salesModel.getKg() != null) {
	            BigDecimal ventaRegistradaKg = productModel.getSalePriceKg().multiply(salesModel.getKg());
	            salesModel.setPrice(ventaRegistradaKg);
	            salesModel.setTotalSales(ventaRegistradaKg);

	            BigDecimal gananciasPorKg = salesModel.getKg().multiply(productModel.getPercentagePriceKg());
	            salesModel.setEarnings(gananciasPorKg);

	            salesModel.setDate(LocalDate.now());
	            salesModel.setHour(LocalTime.now());

	            SalesModel ventaGuardada = salesRepository.save(salesModel);

	            updateDailyEarnings(ventaGuardada);

	            return ventaGuardada;
	        }
	    }
	    return null;
	}

	// Method for sales based on PRICE
	@Override
	public SalesModel saleForPrice(SalesModel salesModel) {
		
	    if (salesModel.getProduct() != null && salesModel.getProduct().getId() != null) {
	        ProductModel productModel = productRepository.findById(salesModel.getProduct().getId()).orElse(null);

	        if (productModel != null && salesModel.getPrice() != null) {
	            BigDecimal precioAKg = salesModel.getPrice().divide(productModel.getSalePriceKg(), 2, RoundingMode.HALF_UP);
	            salesModel.setKg(precioAKg);
	            salesModel.setTotalSales(salesModel.getPrice());

	            BigDecimal gananciasPorKg = salesModel.getKg().multiply(productModel.getPercentagePriceKg());
	            salesModel.setEarnings(gananciasPorKg);
	            
	            salesModel.setDate(LocalDate.now());
	            salesModel.setHour(LocalTime.now());

	            SalesModel savedSale = salesRepository.save(salesModel);

	            updateDailyEarnings(savedSale);

	            return savedSale;
	        }
	        
	    }
	    
	    return null;
	}
    
	private void updateDailyEarnings(SalesModel sale) {
        LocalDate dateSale = sale.getDate();
        BigDecimal totalSale = sale.getTotalSales();
        BigDecimal EarningsSale = sale.getEarnings();

        EarningsModel dailyEarnings = earningsRepository.findByDate(dateSale);

        if (dailyEarnings != null) {
        	dailyEarnings.setTotalSales(dailyEarnings.getTotalSales().add(totalSale));
        	dailyEarnings.setTotalEarnings(dailyEarnings.getTotalEarnings().add(EarningsSale));
        } else {
        	dailyEarnings = new EarningsModel(null, dateSale, EarningsSale, totalSale);
        }

        earningsRepository.save(dailyEarnings);
        
    }
    
	@Override
    public int getNextNumSale(LocalDate fecha) {
        Integer lastNum = salesRepository.findLastNumSale(fecha);
        return lastNum + 1;
    }

}
