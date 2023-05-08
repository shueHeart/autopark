package com.example.demo.route.model;

import java.util.UUID;

public class RouteDTO {
	
	private UUID uuid;
	
	private long startDate;
	
	private long endDate;

	private UUID vehicleId;
	
	protected RouteDTO (Route route) {
		this.uuid = route.getUuid();
		this.startDate = route.getStartDate();
		this.endDate = route.getEndDate();
		this.vehicleId = route.getVehicle().getUuid();
	}
	
	public static RouteDTO fromRoute (Route route) {
		return new RouteDTO(route);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public UUID getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(UUID vehicleId) {
		this.vehicleId = vehicleId;
	}
	

}
