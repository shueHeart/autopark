package com.example.demo.geojson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GEO {
	
	private String type;
	private Geometry geometry;
	private Properties properties;
	
}
