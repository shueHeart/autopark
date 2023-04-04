package com.example.demo.manager.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.LoginController;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.repository.EnterpriseRepository;
import com.example.demo.manager.model.Manager;
import com.example.demo.manager.model.ManagerDTO;
import com.example.demo.manager.repository.ManagerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerService {
	
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ManagerRepository managerRepository;
	
//	зкшмфеу ManagerDetailsService
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Manager createOrUpdateManager(Manager manager) {
		log.info(manager.getPassword());
		manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		
//		Manager manager = new Manager(username, "123", true, true, true, true, new ArrayList());
		
		return managerRepository.save(manager);
		
	}
	
	public List<ManagerDTO> findAll() { 
		return managerRepository.findAll().stream().map(manager -> ManagerDTO.fromManager(manager)).collect(Collectors.toList());
	}

	
	public Manager loadUserByUsername(String username) throws UsernameNotFoundException {
		return managerRepository.findByUsername(username);
	}
	
	public ModelAndView createManager() {
		
		ModelAndView createManager = new ModelAndView("createManager");
				
		List<Enterprise> enterpriseList = enterpriseRepository.findAll();
		createManager.addObject("enterpriseList", enterpriseList);
		
		return createManager;
	}
	
}
