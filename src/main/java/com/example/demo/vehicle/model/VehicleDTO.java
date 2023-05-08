package com.example.demo.vehicle.model;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

public class VehicleDTO {

	private UUID uuid;
	
	private int productionYear;
	
	private String bodyColor;
		
	private int mileage;
	
	private long price;

	private int numberOfOwners;

    private UUID brandUuid;
    
	private UUID currentDriverUuid;
	
	private String sellDate;
	
	
    private VehicleDTO(Vehicle vehicle) {
    	
    	this.uuid = vehicle.getUuid();
    	this.productionYear = vehicle.getProductionYear();
    	this.bodyColor = vehicle.getBodyColor();
    	this.mileage = vehicle.getMileage();
    	this.price = vehicle.getPrice();
    	this.numberOfOwners = vehicle.getNumberOfOwners();
    	this.currentDriverUuid = vehicle.getCurrentDriverUuid();
    	
    	if (vehicle.getBrand() != null) {
    		this.brandUuid = vehicle.getBrand().getUuid();
    	}
    	
    	TimeZone timezone = TimeZone.getTimeZone(vehicle.getEnterprise().getTimezone());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		date.setTimeZone(timezone);
		
		this.sellDate = date.format(vehicle.getSellDate());
    	
    }
    
    public static VehicleDTO fromVehicle(Vehicle vehicle) {
    	return new VehicleDTO(vehicle);
    }
    
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

	public UUID getBrandUuid() {
		return brandUuid;
	}

	public void setBrandUuid(UUID brandUuid) {
		this.brandUuid = brandUuid;
	}

	public UUID getCurrentDriverUuid() {
		return currentDriverUuid;
	}

	public void setCurrentDriverUuid(UUID currentDriverUuid) {
		this.currentDriverUuid = currentDriverUuid;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}
	
	
}
