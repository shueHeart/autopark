package com.example.demo.enterprise.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.manager.model.Manager;

@Repository
public interface EnterpriseRepository extends CrudRepository<Enterprise, UUID> {

	List<Enterprise> findAll();
	
	List<Enterprise> findAllByManagersIn(List<Manager> manager);
	
//	List<Enterprise> findAllByManagerId(UUID managerId);
	
    List<Enterprise> findEnterprisesByManagers_Uuid(UUID id); // метод для получения списка EntityA связанных с EntityV по id

	
}
