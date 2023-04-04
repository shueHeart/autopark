package com.example.demo.creator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.creator.model.Creator;
import com.example.demo.driver.model.Driver;
import com.example.demo.driver.repository.DriverRepository;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.model.EnterpriseDTO;
import com.example.demo.enterprise.service.EnterpriseService;
import com.example.demo.manager.model.Manager;
import com.example.demo.manager.repository.ManagerRepository;
import com.example.demo.vehicle.model.Brand;
import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.repository.BrandRepository;
import com.example.demo.vehicle.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreatorService {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private EnterpriseService enterpriseService;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	public EnterpriseDTO create(Creator creator, Manager manager) {
		
		Enterprise enterprise = creator.getEnterprise();

		
		List<Manager> managers = new ArrayList<Manager>();
		managers.add(manager);
	
		enterprise.setManagers(managers);
		
		enterprise = enterpriseService.saveOrUpdate(enterprise);		
		
		List<Enterprise> enterprises = new ArrayList<Enterprise>();
		enterprises.add(enterprise);
		
		manager.setEnterprises(enterprises);
		
		managerRepository.save(manager);
		
		List<Driver> drivers = new ArrayList<Driver>();

		for (int driverNum = 0; driverNum < creator.getDriverNum(); ++driverNum) {

			Driver driver = new Driver();

			driver.setAge(randomNumber(18) + 18);
			driver.setFirstName(names[randomNumber(names.length - 1)]);
			driver.setLastName(secondNames[randomNumber(secondNames.length - 1)]);
			driver.setSalary(randomNumber(200000) + 120000);
			
			driver.setEnterprise(enterprise);
			
			drivers.add(driver);
		}
		drivers = driverRepository.saveAll(drivers);

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		List<Brand> brands = brandRepository.findAll();
		
		for (int vehicleNum = 0; vehicleNum < creator.getVehicleNum(); ++vehicleNum) {
			
			Vehicle vehicle = new Vehicle();
			
			vehicle.setBodyColor(colors[randomNumber(colors.length - 1)]);
			vehicle.setBrand(brands.get(randomNumber(brands.size() - 1)));
			vehicle.setMileage(randomNumber(500000));
			vehicle.setNumberOfOwners(randomNumber(15) + 1);
			vehicle.setPrice(randomNumber(5000000) + 100000);
			vehicle.setProductionYear(randomNumber(33) + 1990);
			
			vehicle.setEnterprise(enterprise);
			
			
			vehicle = vehicleRepository.save(vehicle);
			
			List<Driver> driversForVehicle = new ArrayList<Driver>();
			vehicle.setDrivers(driversForVehicle);		
		
			for (int i = vehicleNum * (drivers.size() / creator.getVehicleNum()); i < vehicleNum * (drivers.size() / creator.getVehicleNum()) + (drivers.size() / creator.getVehicleNum()) && i < drivers.size(); ++i) {
			
				drivers.get(i).setVehicle(vehicle);
				driverRepository.save(drivers.get(i));
				driversForVehicle.add(drivers.get(i));
			}
			
			if (driversForVehicle.size() != 0 && vehicleNum % 5 == 0) {
				vehicle.setCurrentDriverUuid(driversForVehicle.get(0).getUuid());
			}
			
			vehicles.add(vehicle);
			
			
		}
		vehicles = vehicleRepository.saveAll(vehicles);
		
		
		enterprise.setDrivers(drivers);
		enterprise.setVehicles(vehicles);
		
		return EnterpriseDTO.fromEnterprise(enterpriseService.saveOrUpdate(enterprise));

	}

	public int randomNumber(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must not be negative");
		}

		return new Random().nextInt(n + 1);
	}
	

	private final String[] colors = new String[] { "Белый", "Черный", "Красный", "Синий", "Зеленый", "Бежевый",
			"Бежевый", "Сиреневый", "Графит" };

	private final String[] names = new String[] { "Алексей", "Максим", "Гай", "Кристобаль", "Янус", "Константин",
			"Яков", "Александр", "Виктор" };

	private final String[] secondNames = new String[] { "Иванов", "Каммеррер", "Гаал", "Хунта", "Полуэктович",
			"Сидоров", "Петров", "Федоров", "Корнеев" };

}
