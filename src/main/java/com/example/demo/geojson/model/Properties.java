package com.example.demo.geojson.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Properties {

	private UUID vehicleId;
	
	private String visitDate;
	
}
