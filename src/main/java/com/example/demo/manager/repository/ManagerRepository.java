package com.example.demo.manager.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.manager.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {

	List<Manager> findAll();
	
	Manager findByUsername(String username);
	
	List<Manager> findAllByEnterprisesIn(List<Enterprise> enterprises);
		
}
