package com.example.demo.vehicle.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.vehicle.model.Brand;
import com.example.demo.vehicle.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
	
	public List<Vehicle> findAll();
	
	public List<Vehicle> findAllByBrand(Brand brand);
	
//	public List<Vehicle> saveAll(List<Vehicle> vehicles);

}
