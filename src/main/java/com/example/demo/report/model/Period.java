package com.example.demo.report.model;

public enum Period {

	DAY("День", 0), MONTH("Месяц", 0), YEAR("Год", 0);
	
	private final String title;
	
	private final int value;
	

	private Period(String title, int value) {
		
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
