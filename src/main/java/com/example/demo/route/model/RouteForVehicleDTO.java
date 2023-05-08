package com.example.demo.route.model;

import java.util.UUID;

public class RouteForVehicleDTO {
	private UUID uuid;
	
	private long startDate;
	
	private long endDate;

	private RoutePointWithAddressDTO startPoint;
	
	private RoutePointWithAddressDTO endPoint;
	
	private UUID vehicleId;
	
	private String requestToImg;
	
	private RouteForVehicleDTO (Route route) {
		
		this.uuid = route.getUuid();
		this.startDate = route.getStartDate();
		this.endDate = route.getEndDate();
		this.vehicleId = route.getVehicle().getUuid();
		this.setStartPoint(RoutePointWithAddressDTO.fromRoutePoint(route.getRoutePoints().get(0)));
		this.setEndPoint(RoutePointWithAddressDTO.fromRoutePoint(route.getRoutePoints().get(route.getRoutePoints().size() - 1)));
		
		this.requestToImg = "https://static-maps.yandex.ru/1.x/?";
		
		
		RoutePoint centralPoint = route.getRoutePoints().get(route.getRoutePoints().size() / 2);
		this.requestToImg += "ll=" + centralPoint.getY() + "," + centralPoint.getX() + "&l=map";
		this.requestToImg += "&size=650,450";
		this.requestToImg += "&pt=";
		this.requestToImg += "pm2gnm~" + startPoint.getY() + "," + startPoint.getX() + ",";
		this.requestToImg += "pm2rdm~" + endPoint.getY() + "," + endPoint.getX();
		this.requestToImg += "&pl=c:25D500,w:"; //+  this.startPoint.getY() + "," + this.startPoint.getX();
		this.requestToImg += route.getRoutePoints().size() + ",";
		for (int i = 1; i < route.getRoutePoints().size(); ++i) {
			this.requestToImg += route.getRoutePoints().get(i).getY() + "," + route.getRoutePoints().get(i).getX() + ",";//"round" + i + "~" + route.getRoutePoints().get(i).getY() + "," + route.getRoutePoints().get(i).getX();  
		}
			
		System.out.println(this.requestToImg);
	}
	
	public static RouteForVehicleDTO fromRoute (Route route) {
		return new RouteForVehicleDTO(route);
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

	public RoutePointWithAddressDTO getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(RoutePointWithAddressDTO endPoint) {
		this.endPoint = endPoint;
	}

	public RoutePointWithAddressDTO getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(RoutePointWithAddressDTO startPoint) {
		this.startPoint = startPoint;
	}

	public String getRequestToImg() {
		return requestToImg;
	}

	public void setRequestToImg(String requestToImg) {
		this.requestToImg = requestToImg;
	}
}
