package com.lacroqueteria.service;

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

@Service
public class SalesService {
	
	@Autowired
	private SalesRepository salesRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private EarningsRepository earningsRepository;
	
	public List<SalesModel> getAllVentas() {
		return salesRepository.findAll();
	}
	
	// Method for sales based on KG
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
	public SalesModel saleForPrice(SalesModel salesModel) {
		
		System.out.println("Precio: " + salesModel.getPrice());
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
    
    /*
    // Método para venta basada en KG
    public VentasModel ventaPorKg(VentasModel ventasModel) {
        if (ventasModel.getProduct() != null && ventasModel.getProduct().getId() != null) {
            ProductModel productModel = productRepository.findById(ventasModel.getProduct().getId()).orElse(null);

            if (productModel != null && ventasModel.getKg() != null) {

                BigDecimal ventaRegistradaKg = productModel.getPrecioVentaKg().multiply(ventasModel.getKg());
                ventasModel.setPrecio(ventaRegistradaKg);
                ventasModel.setTotalVenta(ventaRegistradaKg);

                BigDecimal gananciasPorKg = ventasModel.getKg().multiply(productModel.getPrecioPorcentajeKg());
                ventasModel.setGanancia(gananciasPorKg);

                ventasModel.setFecha(LocalDate.now());
                ventasModel.setHora(LocalTime.now());

                return ventasRepository.save(ventasModel);
            }
        }
        return null;
    }

    // Método para venta basada en Precio
    public VentasModel ventaPorPrecio(VentasModel ventasModel) {
        if (ventasModel.getProduct() != null && ventasModel.getProduct().getId() != null) {
            ProductModel productModel = productRepository.findById(ventasModel.getProduct().getId()).orElse(null);

            if (productModel != null && ventasModel.getPrecio() != null) {

                BigDecimal precioAKg = ventasModel.getPrecio().divide(productModel.getPrecioVentaKg(), 2, RoundingMode.HALF_UP);
                ventasModel.setKg(precioAKg);
                ventasModel.setTotalVenta(ventasModel.getPrecio());

                BigDecimal gananciasPorKg = ventasModel.getKg().multiply(productModel.getPrecioPorcentajeKg());
                ventasModel.setGanancia(gananciasPorKg);

                ventasModel.setFecha(LocalDate.now());
                ventasModel.setHora(LocalTime.now());

                return ventasRepository.save(ventasModel);
            }
        }
        return null;
    }
*/
}
