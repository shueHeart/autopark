package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.driver.model.Driver;
import com.example.demo.driver.model.DriverDTO;
import com.example.demo.driver.service.DriverService;

@RestController
public class DriverController {

	@Autowired
	private DriverService driverService;
	
	@GetMapping("/drivers") 
	public List<DriverDTO> findAllDrivers() {
		return driverService.getAllDrivers();
	}
	
	@PostMapping("/driver") 
	public Driver saveOrUpdate(@ModelAttribute Driver driver) {
		return driverService.saveOrUpdate(driver);
	}
	
	@GetMapping("/create/driver")
	public ModelAndView createDriver() {
		
		return driverService.createDriver();
		
	}
	
}
