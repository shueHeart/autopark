package com.example.demo.creator.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.LoginController;
import com.example.demo.creator.model.Creator;
import com.example.demo.driver.model.Driver;
import com.example.demo.driver.repository.DriverRepository;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.model.EnterpriseDTO;
import com.example.demo.enterprise.service.EnterpriseService;
import com.example.demo.manager.model.Manager;
import com.example.demo.manager.repository.ManagerRepository;
import com.example.demo.route.model.Route;
import com.example.demo.route.model.RoutePoint;
import com.example.demo.route.repository.RoutePointRepository;
import com.example.demo.route.repository.RouteRepository;
import com.example.demo.vehicle.model.Brand;
import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.repository.BrandRepository;
import com.example.demo.vehicle.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreatorService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
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
	
	@Autowired 
	private RouteRepository routeRepository;
	
	@Autowired 
	private RoutePointRepository routePointRepository;
	
	public EnterpriseDTO create(Creator creator, Manager manager) {
		
		Enterprise enterprise = creator.getEnterprise();

		enterprise.setTimezone(timezones[randomNumber(names.length - 1)]);
		
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
		log.info(drivers.size() / creator.getVehicleNum() + "");
		for (int vehicleNum = 0; vehicleNum < creator.getVehicleNum(); ++vehicleNum) {
			
			Vehicle vehicle = new Vehicle();
			
			vehicle.setBodyColor(colors[randomNumber(colors.length - 1)]);
			vehicle.setBrand(brands.get(randomNumber(brands.size() - 1)));
			vehicle.setMileage(randomNumber(500000));
			vehicle.setNumberOfOwners(randomNumber(15) + 1);
			vehicle.setPrice(randomNumber(5000000) + 100000);
			vehicle.setProductionYear(randomNumber(33) + 1990);
			
			vehicle.setEnterprise(enterprise);
			
			long time = 1649112805000L;
//			Long randomTime = randomNumber(2592000000)
			
			vehicle.setSellDate(randomLongNumber(2592000000L) + time);
			
			
			
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
			vehicle.setBodyColor(colors[randomNumber(colors.length - 1)]);
			vehicle.setBrand(brands.get(randomNumber(brands.size() - 1)));
			vehicle.setMileage(randomNumber(500000));
			vehicle.setNumberOfOwners(randomNumber(15) + 1);
			vehicle.setPrice(randomNumber(5000000) + 100000);
			vehicle.setProductionYear(randomNumber(33) + 1990);
			
			vehicle.setEnterprise(enterprise);
			
			
			vehicle = vehicleRepository.save(vehicle);
			
			vehicles.add(vehicle);
			
//			Route route = new Route();
//			route.setVehicle(vehicle);
//			route = routeRepository.save(route);
//			
//			List<RoutePoint> routePoints = new ArrayList<RoutePoint>();
//			
//			
//			long startDate = randomLongNumber(2592000000L) + time;
//			for (int i = 0; i < 10; ++i) {
				
//			RoutePoint routePoint = new RoutePoint(56.845329, 53.212816);
//			
//			routePoint.setVisitDate(startDate + 1000 * 10);
//			routePoint.setRoute(route);
//			routePoint = routePointRepository.save(routePoint);
//			
//			routePoints.add(routePoint);
//			
//			RoutePoint routePoint1 = new RoutePoint(56.845873, 53.217420);
//			
//			routePoint1.setVisitDate(startDate + 1000 * 10);
//			routePoint1.setRoute(route);
//			routePoint1 = routePointRepository.save(routePoint1);
//			
//			routePoints.add(routePoint1);
//			
//			RoutePoint routePoint2 = new RoutePoint(56.846226, 53.220842);
//			
//			routePoint2.setVisitDate(startDate + 1000 * 10);
//			routePoint2.setRoute(route);
//			routePoint2 = routePointRepository.save(routePoint2);
//			
//			routePoints.add(routePoint2);
//			
//			RoutePoint routePoint3 = new RoutePoint(56.846382, 53.222481);
//			
//			routePoint3.setVisitDate(startDate + 1000 * 10);
//			routePoint3.setRoute(route);
//			routePoint3 = routePointRepository.save(routePoint3);
//			
//			routePoints.add(routePoint3);
//			
//			RoutePoint routePoint4 = new RoutePoint(56.846476, 53.223713);
//			
//			routePoint4.setVisitDate(startDate + 1000 * 10);
//			routePoint4.setRoute(route);
//			routePoint4 = routePointRepository.save(routePoint4);
//			
//			routePoints.add(routePoint4);
//			
//			RoutePoint routePoint5 = new RoutePoint(56.846639, 53.225691);
//			
//			routePoint5.setVisitDate(startDate + 1000 * 10);
//			routePoint5.setRoute(route);
//			routePoint5 = routePointRepository.save(routePoint5);
//			
//			routePoints.add(routePoint5);
//			
//			RoutePoint routePoint6 = new RoutePoint(56.846770, 53.227376);
//			
//			routePoint6.setVisitDate(startDate + 1000 * 10);
//			routePoint6.setRoute(route);
//			routePoint6 = routePointRepository.save(routePoint6);
//			
//			routePoints.add(routePoint6);
//			
//			RoutePoint routePoint7 = new RoutePoint(56.846770, 53.227376);
//			
//			routePoint7.setVisitDate(startDate + 1000 * 10);
//			routePoint7.setRoute(route);
//			routePoint7 = routePointRepository.save(routePoint7);
//			
//			routePoints.add(routePoint7);
//			}
			
//			route.setRoutePoints(routePoints);
//			route.setStartDate(new Date().getTime());
//			route.setEndDate(route.getRoutePoints().get(route.getRoutePoints().size() - 1).getVisitDate());
			
//			routeRepository.save(route);
			
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
	
	public long randomLongNumber(long n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must not be negative");
		}

		return new Random().nextLong();
	}
	

	private final String[] colors = new String[] { "Белый", "Черный", "Красный", "Синий", "Зеленый", "Бежевый",
			"Бежевый", "Сиреневый", "Графит" };

	private final String[] names = new String[] { "Алексей", "Максим", "Гай", "Кристобаль", "Янус", "Константин",
			"Яков", "Александр", "Виктор" };

	private final String[] secondNames = new String[] { "Иванов", "Каммеррер", "Гаал", "Хунта", "Полуэктович",
			"Сидоров", "Петров", "Федоров", "Корнеев" };

	private final String[] timezones = new String[] { "Asia/Singapore", "Europe/Monaco", "America/Adak", "America/Atka", "Canada/Mountain",
			"America/Chicago", "America/Bogota", "America/Detroit", "Chile/Continental" };

}
