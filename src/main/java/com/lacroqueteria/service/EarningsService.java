package com.lacroqueteria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacroqueteria.model.EarningsModel;
import com.lacroqueteria.repository.EarningsRepository;

@Service
public class EarningsService {
	
	@Autowired
	private EarningsRepository earningsRepository;

	public List<EarningsModel> getAllEarnings() {
		return earningsRepository.findAll();
	}
	
}
