package com.lacroqueteria.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacroqueteria.model.ProductModel;
import com.lacroqueteria.repository.ProductRepository;
import com.lacroqueteria.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductModel> getAllProducts() {
		return productRepository.findAll();
	}
	/*
	 * Spanish version
	 * 
	public ProductModel addProduct(ProductModel productModel) {
		
		if (productModel.getPorcentajeCostal() != null) {
	        BigDecimal porcentajeCostal = productModel.getPorcentajeCostal().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			productModel.setPorcentajeCostal(porcentajeCostal);
			BigDecimal gananciaPorcentajeCostal = productModel.getPrecioCostal().multiply(porcentajeCostal).setScale(2, RoundingMode.HALF_UP);
			productModel.setPrecioPorcentajeCostal(gananciaPorcentajeCostal);
			BigDecimal ventaFinalCostal = productModel.getPrecioCostal().add(gananciaPorcentajeCostal).setScale(2, RoundingMode.HALF_UP);
			productModel.setPrecioVentaCostal(ventaFinalCostal);
		}
		
		if (productModel.getPorcentajeKg() != null) {
	        BigDecimal porcentajeKg = productModel.getPorcentajeKg().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			productModel.setPorcentajeKg(porcentajeKg);
			BigDecimal gananciaPorcentajeKg = productModel.getPrecioKg().multiply(porcentajeKg).setScale(2, RoundingMode.HALF_UP);
			productModel.setPrecioPorcentajeKg(gananciaPorcentajeKg);
			BigDecimal ventaFinalKg = productModel.getPrecioKg().add(gananciaPorcentajeKg).setScale(2, RoundingMode.HALF_UP);
			productModel.setPrecioVentaKg(ventaFinalKg);
		}
		
		return productRepository.save(productModel);
	}
	*/
	@Override
	public ProductModel addProduct(ProductModel productModel) {
		
		if (productModel.getPercentageCostal() != null) {
	        BigDecimal percentageCostal = productModel.getPercentageCostal().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			productModel.setPercentageCostal(percentageCostal);
			
			BigDecimal percentageEarningsCostal = productModel.getPriceCostal().multiply(percentageCostal).setScale(2, RoundingMode.HALF_UP);
			productModel.setPercentagePriceCostal(percentageEarningsCostal);
			
			BigDecimal finalSendCostal = productModel.getPriceCostal().add(percentageEarningsCostal).setScale(2, RoundingMode.HALF_UP);
			productModel.setSalePriceCostal(finalSendCostal);
		}
		
		if (productModel.getPercentageKg() != null) {
	        BigDecimal percentageKg = productModel.getPercentageKg().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			productModel.setPercentageKg(percentageKg);
			
			BigDecimal percentageEarningsKg = productModel.getPriceKg().multiply(percentageKg).setScale(2, RoundingMode.HALF_UP);
			productModel.setPercentagePriceKg(percentageEarningsKg);
			
			BigDecimal finalSendKg = productModel.getPriceKg().add(percentageEarningsKg).setScale(2, RoundingMode.HALF_UP);
			productModel.setSalePriceKg(finalSendKg);
		}
		
		return productRepository.save(productModel);
	}
	
	@Override
	public Boolean deleteProductById(Long id) {
	    if (productRepository.existsById(id)) {
	        productRepository.deleteById(id);
	        return true;
	    }
	    return false;
	}
	
}
