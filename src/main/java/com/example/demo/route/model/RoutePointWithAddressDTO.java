package com.example.demo.route.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.model.Address;
import fr.dudie.nominatim.model.BoundingBox;

import org.apache.http.impl.client.HttpClients;


public class RoutePointWithAddressDTO extends RoutePointDTO {
	
	private UUID uuid;
	
	private double x;
	
	private double y;
	
	private String visitDate;
	
	private String address;
	
	private UUID routeId;
	
	private RoutePointWithAddressDTO(RoutePoint routePoint) throws IOException {
	    	
//    	super(routePoint);
	 	
    	this.uuid = routePoint.getUuid();
    	this.x = routePoint.getX();
    	this.y = routePoint.getY();
    	
    	TimeZone timezone = TimeZone.getTimeZone(routePoint.getRoute().getVehicle().getEnterprise().getTimezone());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date.setTimeZone(timezone);
		
		this.visitDate = date.format(routePoint.getVisitDate());
		
		this.setRouteId(routePoint.getUuid());
		
    	JsonNominatimClient nominatimClient = new JsonNominatimClient(HttpClients.createDefault(), "http://nominatim.openstreetmap.org/");

    	Address address = nominatimClient.getAddress(routePoint.getY(), routePoint.getX());

    	this.address = address.getDisplayName();
    	
    }
    
    public RoutePointWithAddressDTO() {
	}

	public static RoutePointWithAddressDTO fromRoutePoint(RoutePoint routePoint) {
    	try {
			return new RoutePointWithAddressDTO(routePoint);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return new RoutePointWithAddressDTO();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
