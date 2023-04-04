package com.example.demo.driver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.driver.model.Driver;
import com.example.demo.manager.model.Manager;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, UUID>, JpaRepository<Driver, UUID> {
	
	List<Driver> findAll();
	
	Page<Driver> findAllByEnterprise_Managers(Manager manager, Pageable pageable);

	
//	List<Driver> saveAll(List<Driver> drivers);
	
}
