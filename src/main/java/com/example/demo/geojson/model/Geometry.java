package com.example.demo.geojson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Geometry {
	
	private String type;
	private	double[][] coordinates;
	public double[][] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(double[][] coordinates) {
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
