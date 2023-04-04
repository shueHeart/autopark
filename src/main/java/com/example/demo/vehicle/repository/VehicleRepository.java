package com.example.demo.vehicle.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.manager.model.Manager;
import com.example.demo.vehicle.model.Brand;
import com.example.demo.vehicle.model.Vehicle;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, UUID>, JpaRepository<Vehicle, UUID> {
	
	
	public List<Vehicle> findAllByBrand(Brand brand);
	
//	public List<Vehicle> findAllByEnterprise();
	
	Page<Vehicle> findAllByEnterprise_Managers(Manager manager, Pageable pageable);

//    Page<Vehicle> findByEnterprise_ManagersAndVehicleIsNotNull(Manager manager, Pageable pageable);
	
//	public List<Vehicle> saveAll(List<Vehicle> vehicles);

}
