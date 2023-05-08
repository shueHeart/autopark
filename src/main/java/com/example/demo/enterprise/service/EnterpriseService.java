package com.example.demo.enterprise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.LoginController;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.model.EnterpriseDTO;
import com.example.demo.enterprise.repository.EnterpriseRepository;
import com.example.demo.manager.model.Manager;
import com.example.demo.manager.repository.ManagerRepository;

@Service
public class EnterpriseService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	public ModelAndView getEnterprisesTable(Manager manager) {
		
		ModelAndView enterprises = new ModelAndView("enterprises");
		
		enterprises.addObject("enterpriseList", findAllEnterprisesFull());
		
		return enterprises;
		
	}
	
	public List<EnterpriseDTO> findAllEnterprises() {
		return enterpriseRepository.findAll().stream()
				.map(enterprise -> EnterpriseDTO.fromEnterprise(enterprise)).collect(Collectors.toList());
	}
	
	public List<Enterprise> findAllEnterprisesFull() {
		return enterpriseRepository.findAll();
	}
	
	public Enterprise saveOrUpdate(Enterprise enterprise) {
		return enterpriseRepository.save(enterprise);
	}
	
	public List<Enterprise> findAllEnterprisesByManagerId(UUID managerId) {
//		
//		Manager manager = managerRepository.findById(managerId)
//				.orElseThrow(() -> new RuntimeException("Manager not found"));
		
//		List<Manager> managers = new ArrayList<Manager>();
//		
//		managers.add(manager);
//		log.info("их вот столько - " + manager.getEnterprises().size());
		return enterpriseRepository.findEnterprisesByManagers_Uuid(managerId);
	}
	
	public List<EnterpriseDTO> findAllEnterprisesForManager(UUID managerId) {
		return findAllEnterprisesByManagerId(managerId)
				.stream().map(enterprise -> EnterpriseDTO.fromEnterprise(enterprise)).collect(Collectors.toList());
	}
	
}
