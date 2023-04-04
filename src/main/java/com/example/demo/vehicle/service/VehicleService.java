package com.example.demo.vehicle.service;

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
import com.example.demo.driver.model.Driver;
import com.example.demo.driver.model.DriverDTO;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.repository.EnterpriseRepository;
import com.example.demo.enterprise.service.EnterpriseService;
import com.example.demo.manager.model.Manager;
import com.example.demo.manager.repository.ManagerRepository;
import com.example.demo.manager.service.UserDetailsManager;
import com.example.demo.vehicle.model.Brand;
import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.model.VehicleDTO;
import com.example.demo.vehicle.model.VehicleType;
import com.example.demo.vehicle.repository.BrandRepository;
import com.example.demo.vehicle.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private EnterpriseService enterpriseService;
	
	@Autowired
	private UserDetailsManager userDetailsManager;
	
	@Autowired 
	private ManagerRepository managerRepository;
	
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	
	public ModelAndView getAllVehicles() {
		
		ModelAndView vehicles = new ModelAndView("vehicles");
	    
		List<Vehicle> vehicleList = vehicleRepository.findAll();
		
		vehicles.addObject("vehicleList", vehicleList);
		
		return vehicles;
		
	}
	
	public List<VehicleDTO> findAll() {
		
		List<Vehicle> vehicles = vehicleRepository.findAll(); 
		
		return vehicles.stream().map(vehicle -> VehicleDTO.fromVehicle(vehicle)).collect(Collectors.toList());
				
	}
	
	public ModelAndView saveVehicle(Vehicle vehicle) {
		
		vehicleRepository.save(vehicle);
		
		ModelAndView vehicles = new ModelAndView("vehicles");
		
		vehicles.addObject("vehicleList", vehicleRepository.findAll());
		
		return vehicles; 
	}
	
	public VehicleDTO saveJsonVehicle(Vehicle vehicle, String username) {
		
		if (!managerExists(vehicle.getEnterprise().getUuid(), username)) {
			throw new RuntimeException("Ð�ÐµÐ´Ð¾Ñ�Ñ‚Ð°Ñ‚Ð¾Ñ‡Ð½Ð¾ Ð¿Ñ€Ð°Ð²");
		}
		
		vehicle.setBrand(brandRepository.findById(vehicle.getBrand().getUuid()).get());
		
		vehicle.setEnterprise(enterpriseRepository.findById(vehicle.getEnterprise().getUuid()).get());
		
		return VehicleDTO.fromVehicle(vehicleRepository.save(vehicle));
		
	}
	
	public ModelAndView updateVehicle(UUID vehicleUuid) {
		
		ModelAndView updateVehicle = new ModelAndView("updateVehicle");
		
		Vehicle vehicle = vehicleRepository.findById(vehicleUuid)
				.orElseThrow(() -> new RuntimeException("Ð¢Ñ€Ð°Ð½Ñ�Ð¿Ð¾Ñ€Ñ‚Ð½Ð¾Ðµ Ñ�Ñ€ÐµÐ´Ñ�Ñ‚Ð²Ð¾ Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð¾."));
		
		List<Brand> brandList = brandRepository.findAll();
		
		updateVehicle.addObject("vehicle", vehicle);
		updateVehicle.addObject("brandList", brandList);
		
		return updateVehicle;
	}
	
	public ModelAndView saveVehicleForm () {
		
		ModelAndView createVehicle = new ModelAndView("createVehicle");
		
		createVehicle.addObject("brandList", findAllBrands());
		
		List<Enterprise> enterpriseList = enterpriseRepository.findAll();
		createVehicle.addObject("enterpriseList", enterpriseList);
		
		return createVehicle;
	}
	
	public ModelAndView deleteVehicle(UUID vehicleUuid) {
		
		vehicleRepository.deleteById(vehicleUuid);
		
		ModelAndView vehiclesForm = new ModelAndView("vehicles");
		
		vehiclesForm.addObject("vehicleList", vehicleRepository.findAll());
		
		return vehiclesForm;
		
	}
	
	public boolean deleteVehicle(UUID vehicleUuid, String username) {
		
		Vehicle vehicle = vehicleRepository.findById(vehicleUuid).orElseThrow(() -> new RuntimeException("Vehicle not found"));
		
		Enterprise enterprise = enterpriseRepository.findById(vehicle.getEnterprise().getUuid()).orElseThrow(() -> new RuntimeException("Enterprise not found"));
		
		List<Enterprise> enterprises = new ArrayList<Enterprise>();
		
		enterprises.add(enterprise);
		
		List<Manager> managers = managerRepository.findAllByEnterprisesIn(enterprises);
		
		if (managers == null) {
			return false;
		}
		
		for (Manager manager : managers) {
			if (manager.getUsername().equals(username)) { 
				vehicleRepository.delete(vehicle);
				return true;
			}
		}
		
		return false;

	}
	
	public List<Brand> findAllBrands() {
		return brandRepository.findAll();
	}
	
	public ModelAndView getAllBrands() {
		
	 	ModelAndView brands = new ModelAndView("brands");
	    
		List<Brand> brandList = findAllBrands();
		
		brands.addObject("brandList", brandList);
		
		return brands;
		
	}
	
	public ModelAndView saveBrand(Brand brand) {
		
//		List<Vehicle> vehicles = vehicleRepository.findAllByBrand(brand);
//		brand.setVehicles(vehicles);
		
		brandRepository.save(brand);
		
		ModelAndView brands = new ModelAndView("brands");
		
		brands.addObject("brandList", brandRepository.findAll());
		
		return brands; 
	}
	
	public ModelAndView updateBrand(UUID brandUuid) {
		
		ModelAndView updateBrand = new ModelAndView("updateBrand");
		
		Brand brand = brandRepository.findById(brandUuid)
				.orElseThrow(() -> new RuntimeException("Ð¢Ñ€Ð°Ð½Ñ�Ð¿Ð¾Ñ€Ñ‚Ð½Ð¾Ðµ Ñ�Ñ€ÐµÐ´Ñ�Ñ‚Ð²Ð¾ Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð¾."));
				
		updateBrand.addObject("brand", brand);
		updateBrand.addObject("typeList", VehicleType.values());
		
		return updateBrand;
	}
	
	public ModelAndView saveBrandForm() {
		
		ModelAndView createBrandForm = new ModelAndView("createBrand");
		
		createBrandForm.addObject("typeList", VehicleType.values());
		
		return createBrandForm;
	}
	
	public ModelAndView deleteBrand(UUID brandUuid) {
		
		brandRepository.deleteById(brandUuid);
		
		ModelAndView brandForm = new ModelAndView("brands");
		
		brandForm.addObject("brandList", brandRepository.findAll());
		
		return brandForm;
		
	}
	
	public List<VehicleDTO> findAllVehiclesForManager(String managerUsername) {
		
		Manager manager = (Manager) userDetailsManager.loadUserByUsername(managerUsername);
		
		List<Enterprise> enterprises = enterpriseService.findAllEnterprisesByManagerId(manager.getUuid());
		
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for (Enterprise enterprise : enterprises) {
			vehicles.addAll(enterprise.getVehicles());
		}
		
		return vehicles.stream().map(vehicle -> VehicleDTO.fromVehicle(vehicle)).collect(Collectors.toList());
		
	}
	
	public List<DriverDTO> findAllDriversForManager(String managerUsername) {
		
		Manager manager = (Manager) userDetailsManager.loadUserByUsername(managerUsername);
		
		List<Enterprise> enterprises = enterpriseService.findAllEnterprisesByManagerId(manager.getUuid());
		
		List<Driver> drivers = new ArrayList<Driver>();
		
		for (Enterprise enterprise : enterprises) {
			drivers.addAll(enterprise.getDrivers());
		}
		
		return drivers.stream().map(driver -> DriverDTO.fromDriver(driver)).collect(Collectors.toList());
		
	}
	
	private boolean managerExists(UUID enterpriseId, String username) {
		log.info(username);
		Enterprise enterprise = enterpriseRepository.findById(enterpriseId).orElseThrow(() -> new RuntimeException("Enterprise not found"));
		
		List<Enterprise> enterprises = new ArrayList<Enterprise>();
		
		enterprises.add(enterprise);
		
		List<Manager> managers = managerRepository.findAllByEnterprisesIn(enterprises);
		log.info(managers.size() + "");

		for (Manager manager : managers) {
			log.info(manager.getUsername());
			if (manager.getUsername().equals(username)) {
				return true;
			}
		}
		log.info("false");
		return false;
		
	}
	
}
