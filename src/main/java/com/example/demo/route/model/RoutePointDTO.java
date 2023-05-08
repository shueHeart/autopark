package com.example.demo.route.model;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.model.VehicleDTO;

public class RoutePointDTO{
	
	
	private UUID uuid;
	
	private double x;
	
	private double y;
	
	private String visitDate;
	
	private UUID routeId;
	
	protected RoutePointDTO() {}
	
	protected RoutePointDTO(RoutePoint routePoint) {
	    	
    	this.uuid = routePoint.getUuid();
    	this.x = routePoint.getX();
    	this.y = routePoint.getY();
    	
    	TimeZone timezone = TimeZone.getTimeZone(routePoint.getRoute().getVehicle().getEnterprise().getTimezone());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date.setTimeZone(timezone);
		
		this.visitDate = date.format(routePoint.getVisitDate());
		
		this.setRouteId(routePoint.getUuid());
    	
    }
    
    public static RoutePointDTO fromRoutePoint(RoutePoint routePoint) {
    	return new RoutePointDTO(routePoint);
    }

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public UUID getRouteId() {
		return routeId;
	}

	public void setRouteId(UUID routeId) {
		this.routeId = routeId;
	}
}
