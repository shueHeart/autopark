package com.example.demo.enterprise.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.demo.driver.model.Driver;
import com.example.demo.vehicle.model.Vehicle;

@Entity
public class Enterprise {

	@Id
	@GeneratedValue
	private UUID uuid;
	
	private String name;
	
	private String country;
	
	private String city;
	
	private OrganizationalForm organizationalForm;
	
	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vehicle> vehicles;
	
	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Driver> drivers;

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

	
	
}
