package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.route.model.RouteDTO;
import com.example.demo.route.model.RouteForVehicleDTO;
import com.example.demo.route.service.RouteService;

@RestController
public class RouteController extends BaseController{

	@Autowired
	private RouteService routeService;

	@GetMapping("/vehicle/{vehicleId}/route_points")
	public ResponseEntity<?> findAllRoutePointsByVehicleAndDateInterval(@PathVariable("vehicleId") UUID vehicleId,
			@RequestParam("startDate") long startDate, @RequestParam("endDate") long endDate, @RequestParam("geo") boolean geo) {
		return routeService.findAllRoutePointsByVehicleAndDateInterval(vehicleId, startDate, endDate, geo);
	}
	
	@GetMapping("/routes")
	public List<RouteDTO> findAllRoutesByStartAndEndDate(@RequestParam("startDate") long startDate, @RequestParam("endDate") long endDate) {
		return routeService.findAllRoutesByStartDateAndEndDate(startDate, endDate);
	}
	
	@GetMapping("/vehicle/{vehicleId}/routes") 
	public List<RouteForVehicleDTO> findAllRoutesByVehicleAndDateInterval(
			@PathVariable("vehicleId") UUID vehicleId, @RequestParam("startDate") long startDate, @RequestParam("endDate") long endDate) {
		return routeService.findAllRoutesByVehicleIdAndStartDateAndEndDate(vehicleId, startDate, endDate);
	}
	
	@GetMapping("/view/vehicle/{vehicleId}/routes") 
	public ModelAndView viewRoutesForVehicle(@PathVariable("vehicleId") UUID vehicleId, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
		return routeService.findAllRoutesByVehicle(vehicleId, pageNumber, pageSize);
	}
	
	@PostMapping("/creator/routes") 
	public void creator() {
		routeService.trackcreator(getCurrentUser());
	}

}
