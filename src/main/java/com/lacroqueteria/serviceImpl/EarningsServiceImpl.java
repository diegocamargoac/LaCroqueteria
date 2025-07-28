package com.lacroqueteria.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacroqueteria.model.EarningsModel;
import com.lacroqueteria.repository.EarningsRepository;
import com.lacroqueteria.service.EarningsService;

@Service
public class EarningsServiceImpl implements EarningsService {
	
	@Autowired
	private EarningsRepository earningsRepository;

	@Override
	public List<EarningsModel> getAllEarnings() {
		return earningsRepository.findAll();

	}
}
