package com.example.demo.route.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.demo.vehicle.model.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Route {

	@Id
	@GeneratedValue
	private UUID uuid;
	
	private long startDate;
	
	private long endDate;
	
	@OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<RoutePoint> routePoints = new ArrayList<RoutePoint>();
	
	@ManyToOne
	@JoinColumn(name="vehicle_id")
	@JsonIgnore
	private Vehicle vehicle;
	
	public Route () {
		
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

	public List<RoutePoint> getRoutePoints() {
		return routePoints;
	}

	public void setRoutePoints(List<RoutePoint> routePoints) {
		this.routePoints = routePoints;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public long getLastRoutePointVisitDate() {
		
		if (routePoints.size() == 0) {
			return new Date().getTime();
		}
		
		return routePoints.get(routePoints.size() - 1).getVisitDate();
	}
	
	
	public void addPoint(RoutePoint point) {
		
		if (point != null && point.getUuid() != null && !routePoints.contains(point)) {
			routePoints.add(point);
		}
		
	}
}
