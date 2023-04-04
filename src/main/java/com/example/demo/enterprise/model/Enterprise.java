package com.example.demo.enterprise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.example.demo.driver.model.Driver;
import com.example.demo.manager.model.Manager;
import com.example.demo.vehicle.model.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Enterprise {

	@Id
	@GeneratedValue
	private UUID uuid;
	
	private String name;
	
	private String country;
	
	private String city;
	
	private OrganizationalForm organizationalForm;
	
	@ManyToMany
//	@JoinTable(
//			name = "enterprise_manager",
//            joinColumns = @JoinColumn(name = "enterprise_id"),
//            inverseJoinColumns = @JoinColumn(name = "manager_id")
//		)
	private List<Manager> managers = new ArrayList<Manager>();
	
//	@JsonIgnore
	@OneToMany(mappedBy = "enterprise")
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	
//	@JsonIgnore
	@OneToMany(mappedBy = "enterprise")
	private List<Driver> drivers = new ArrayList<Driver>();

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public OrganizationalForm getOrganizationalForm() {
		return organizationalForm;
	}

	public void setOrganizationalForm(OrganizationalForm organizationalForm) {
		this.organizationalForm = organizationalForm;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public List<Manager> getManagers() {
		return managers;
	}

	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

	
	
}
