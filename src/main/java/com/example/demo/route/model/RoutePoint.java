package com.example.demo.route.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.geo.Point;

@Entity
public class RoutePoint {
	
	@Id
	@GeneratedValue
	private UUID uuid;
	
	private double x;
	
	private double y;
	
	private long visitDate;
	
	@ManyToOne
    @JoinColumn(name = "route_id")
	private Route route;
	
	public RoutePoint () {
		
	}
	
	public RoutePoint(double x, double y) {
		this.x = x;
		this.y = y;
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

	public long getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(long visitDate) {
		this.visitDate = visitDate;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	
	
}
