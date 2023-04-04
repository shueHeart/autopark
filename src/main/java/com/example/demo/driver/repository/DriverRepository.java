package com.example.demo.driver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.driver.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
	
	List<Driver> findAll();
	
//	List<Driver> saveAll(List<Driver> drivers);
	
}
