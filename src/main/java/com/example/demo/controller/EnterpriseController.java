package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.model.EnterpriseDTO;
import com.example.demo.enterprise.service.EnterpriseService;

@RestController
public class EnterpriseController extends BaseController {

	@Autowired
	private EnterpriseService enterpriseService;
	
	@GetMapping("/enterprises")
	public ModelAndView findAllEnterprises() {
		
		return enterpriseService.getEnterprisesTable(getCurrentUser());
		
	}
	
	@GetMapping("/json/enterprises")
	public List<Enterprise> findAllEnterprisesFull() {
		
		return enterpriseService.findAllEnterprisesFull();
		
	}
	
	@PostMapping("/enterprise")
	public Enterprise saveOrUpdate(@RequestBody Enterprise enterprise) {
		return enterpriseService.saveOrUpdate(enterprise);
	}
	
	@GetMapping("/manager/{managerUuid}/enterprises")
	public List<EnterpriseDTO> findAllEnterprisesForManager(@PathVariable("managerUuid") UUID managerUuid) {
		
		return enterpriseService.findAllEnterprisesForManager(managerUuid);
		
	}
	
}
