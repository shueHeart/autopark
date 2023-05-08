package com.example.demo.report.model;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.report.service.ReportService;
import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.model.VehicleDTO;

public class ReportDTO {


	private UUID uuid;

	private ReportType type;

	private Period period;

	private String startDate;

	private String endDate;

	private String result;

	private UUID vehicleUuid;

	private ReportDTO(Report report) {
    			
    	this.uuid = report.getUuid();
    	
    	this.type = report.getType();
    	this.period = report.getPeriod();
    	this.result = report.getResult();
    	this.vehicleUuid = report.getVehicle().getUuid();
    	
    	TimeZone timezone = TimeZone.getTimeZone(report.getVehicle().getEnterprise().getTimezone());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		date.setTimeZone(timezone);
		
		this.startDate = date.format(report.getStartDate());
		this.endDate = date.format(report.getEndDate());
    	
    }

	public static ReportDTO fromReport(Report report) {
		return new ReportDTO(report);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UUID getVehicleUuid() {
		return vehicleUuid;
	}

	public void setVehicleUuid(UUID vehicleUuid) {
		this.vehicleUuid = vehicleUuid;
	}

}
