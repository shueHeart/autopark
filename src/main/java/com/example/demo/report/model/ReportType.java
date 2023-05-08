package com.example.demo.report.model;

public enum ReportType {

	VEHICLE_MILEAGE("Пробег автомобиля за период", 0);
	
	private final String title;
	
	private final int value;
	

	private ReportType(String title, int value) {
		
		this.title = title;
		this.value = value;
		
	}
	

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return value;
	}
	
	
}
