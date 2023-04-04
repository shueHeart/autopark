package com.example.demo.vehicle.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.driver.model.Driver;
import com.example.demo.enterprise.model.Enterprise;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vehicle")
public class Vehicle {
	
	@Id
	@GeneratedValue
	private UUID uuid;
	
	private int productionYear;
	
	private String bodyColor;
		
	private int mileage;
	
	private long price;

	private int numberOfOwners;
	
	private UUID currentDriverUuid;
	
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
	
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

    @OneToMany(mappedBy="vehicle")
    private List<Driver> drivers;
	
	public int getProductionYear() {
		return productionYear;
	}

	public void setProductionYear(int productionYear) {
		this.productionYear = productionYear;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(String bodyColor) {
		this.bodyColor = bodyColor;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getNumberOfOwners() {
		return numberOfOwners;
	}

	public void setNumberOfOwners(int numberOfOwners) {
		this.numberOfOwners = numberOfOwners;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public UUID getCurrentDriverUuid() {
		return currentDriverUuid;
	}

	public void setCurrentDriverUuid(UUID currentDriverUuid) {
		this.currentDriverUuid = currentDriverUuid;
	}
	
	
	
}
