package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.manager.model.Manager;
import com.example.demo.manager.model.ManagerDTO;
import com.example.demo.manager.service.ManagerService;

@RestController
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/manager")
	public Manager createOrUpdateManager(@ModelAttribute Manager manager) {
		
		return managerService.createOrUpdateManager(manager);
		
	}
	
	@GetMapping("/managers")
	public List<ManagerDTO> getAllManagers() {
		return managerService.findAll();
	}
	
	@GetMapping("/{username}/manager")
	public Manager getManagerByUsername(@PathVariable("username") String username) {
		return managerService.loadUserByUsername(username);
	}
	
	@GetMapping("/create/manager")
	public ModelAndView createManager() {
		
		return managerService.createManager();
		
	}
	
}
