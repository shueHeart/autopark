package com.example.demo.route.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.LoginController;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.repository.EnterpriseRepository;
import com.example.demo.geojson.model.GEO;
import com.example.demo.geojson.model.Geometry;
import com.example.demo.geojson.model.Properties;
import com.example.demo.manager.model.Manager;
import com.example.demo.report.model.Period;
import com.example.demo.report.model.Report;
import com.example.demo.report.model.ReportType;
import com.example.demo.report.repository.ReportRepository;
import com.example.demo.route.model.Route;
import com.example.demo.route.model.RouteDTO;
import com.example.demo.route.model.RouteForVehicleDTO;
import com.example.demo.route.model.RoutePoint;
import com.example.demo.route.model.RoutePointDTO;
import com.example.demo.route.model.RoutePointWithAddressDTO;
import com.example.demo.route.repository.RoutePointRepository;
import com.example.demo.route.repository.RouteRepository;
import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.repository.VehicleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RouteService {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private RoutePointRepository routePointRepository;

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;

	@Value("${vehicle.uuid}")
	private UUID genRouteVehicleUuid;

	@Value("${start.point.x}")
	private double startPointX;
	@Value("${start.point.y}")
	private double startPointY;

	@Value("${end.point.x}")
	private double endPointX;
	@Value("${end.point.y}")
	private double endPointY;

	private String firstRouteCoords = "{\"coordinates\":[[8.681495,49.41461],[8.687872,49.420318]]}";
	private String secondRouteCoords = "{\"coordinates\":[[8.689102, 49.402488],[8.691004, 49.391625]]}";
	private String thirdRouteCoords = "{\"coordinates\":[[8.510939, 49.477229],[8.467165, 49.554721]]}";

	private Geometry geometriesForFirstRoute = new Geometry();
	private Geometry geometriesForSecondRoute = new Geometry();
	private Geometry geometriesForThirdRoute = new Geometry();

	
	private UUID reportUuid = UUID.fromString("1a093271-1492-467d-b9a2-82d0f165ecac");

	private int count_point = 40;

	private Point[] points = new Point[0];

	public List<RouteDTO> findAllRoutesByStartDateAndEndDate(long startDate, long endDate) {
		return routeRepository.findRoutesByStartDateAndEndDate(startDate, endDate).stream()
				.map(route -> RouteDTO.fromRoute(route)).collect(Collectors.toList());
	}

	public List<RouteForVehicleDTO> findAllRoutesForVehicle(UUID vehicleUuid) {
		return routeRepository.findAllByVehicleUuid(vehicleUuid).stream()
				.map(route -> RouteForVehicleDTO.fromRoute(route)).collect(Collectors.toList());
	}

	public List<RouteForVehicleDTO> findAllRoutesByVehicleIdAndStartDateAndEndDate(UUID vehicleId, long startDate,
			long endDate) {

		
		return routeRepository.findRoutesByVehicleUuidAndStartDateAndEndDate(vehicleId, startDate, endDate).stream()
				.map(route -> RouteForVehicleDTO.fromRoute(route)).collect(Collectors.toList());

//		List<Route> routes = routeRepository.findRoutesByStartDateAndEndDate(startDate, endDate);
//		
//		routes.stream().filter(route -> route.getVehicle().getUuid().equals(vehicleId)).collect(Collectors.toList());
//		
//		return routes.stream().map(route -> RouteForVehicleDTO.fromRoute(route)).collect(Collectors.toList());

	}

	public ResponseEntity<?> findAllRoutePointsByVehicleAndDateInterval(UUID vehicleId, long startDate, long endDate,
			boolean geo) {

		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new RuntimeException("Vehicle not found"));

		List<RoutePoint> routePoints = routePointRepository.findByRouteVehicleAndVisitDateBetween(vehicle, startDate,
				endDate);

		List<RoutePointDTO> routePointDTOs = routePoints.stream()
				.map(routePoint -> RoutePointDTO.fromRoutePoint(routePoint)).collect(Collectors.toList());

		if (geo) {
			return new ResponseEntity<>(geoBuilder(routePointDTOs, vehicleId), HttpStatus.OK);
		}

		log.info(routePoints.size() + "");

		return new ResponseEntity<>(routePoints.stream().map(routePoint -> RoutePointDTO.fromRoutePoint(routePoint))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	public ModelAndView findAllRoutesByVehicle(UUID vehicleUuid, int pageNumber, int pageSize) {

		ModelAndView routes = new ModelAndView("routes");

		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Route> routePage = routeRepository.findAllByVehicleUuid(vehicleUuid, pageable);
		
		routes.addObject("routes", routePage.getContent().stream().map(route -> RouteForVehicleDTO.fromRoute(route)).collect(Collectors.toList()));

		Vehicle vehicle = vehicleRepository.findById(vehicleUuid)
				.orElseThrow(() -> new RuntimeException("ТС не найдено"));

		TimeZone timeZone = TimeZone.getTimeZone(vehicle.getEnterprise().getTimezone());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		date.setTimeZone(timeZone);

		routes.addObject("date", date);

		return routes;
	}

//	@Scheduled(fixedRate = 1000)
	public void updateTrack() {

		UUID vehicleUuid = UUID.fromString("08b49b4a-154d-443e-84bd-01837625dfb6");

		if (count_point == 0) {
			Report report = new Report();

			report.setStartDate(new Date().getTime());
			report.setType(ReportType.VEHICLE_MILEAGE);
			report.setVehicle(vehicleRepository.findById(vehicleUuid).get());
			report.setPeriod(Period.DAY);
			report.setResult("0.0");

			this.reportUuid = reportRepository.save(report).getUuid();
			log.info(this.reportUuid + "");
		}

		List<Route> routes = routeRepository.findAllByVehicleUuid(vehicleUuid);

		Geometry geom = getRoute("");

		RoutePoint routePoint = new RoutePoint(geom.getCoordinates()[count_point][1],
				geom.getCoordinates()[count_point][0]);

		Report report = reportRepository.findById(this.reportUuid).get();
		
		
		double x1 = geom.getCoordinates()[count_point][1];
		double y1 = geom.getCoordinates()[count_point][0];
		
		double x2 = geom.getCoordinates()[count_point][1];
		double y2 = geom.getCoordinates()[count_point][0];
		
		if (count_point > 0) {
			x2 = geom.getCoordinates()[count_point - 1][1];
			y2 = geom.getCoordinates()[count_point - 1][0];
		}
			
		report.setResult((Double.parseDouble(report.getResult()) + distance(x1, y1, x2, y2, 'K')) + "");
		report = reportRepository.save(report);
		
		
		routePoint.setVisitDate(routes.get(0).getLastRoutePointVisitDate() + 1000L);
		routePoint.setRoute(routes.get(0));

		

		count_point += +1;
		
		if (count_point == geom.getCoordinates().length - 1) {
			
			Route route = routes.get(0);
			
			route.setEndDate(new Date().getTime());
			
			report.setEndDate(new Date().getTime());
			
			
			
			reportRepository.save(report);
		}

		log.info(count_point + "");

		routePointRepository.save(routePoint);

	}
	
	public void trackcreator(Manager manager) {
		
		List<Manager> managers = new ArrayList<Manager>();
		managers.add(manager);
		
		geometriesForFirstRoute = getRoute(firstRouteCoords);
		geometriesForSecondRoute = getRoute(secondRouteCoords);
		geometriesForThirdRoute = getRoute(thirdRouteCoords);
		
		List<Enterprise> enterprises = enterpriseRepository.findAllByManagersIn(managers);
		
		for (Enterprise enterprise : enterprises) {
			createRoutesForVehiclesByEnterprise(enterprise.getUuid());
		}
		
	}
	
	private void createRoutesForVehiclesByEnterprise(UUID enterpriseId) {
		
		Enterprise enterprise = enterpriseRepository.findById(enterpriseId).orElse(null);
		
		if (enterprise == null) {
			return;
		}
		
		List<Vehicle> vehicles = enterprise.getVehicles();
		
		long time = new Date().getTime();

		for (int i = 0; i < 10; ++i) {
			for (Vehicle vehicle : vehicles) {
			
				vehicle.addRoute(createRouteForVehicle(vehicle, geometriesForFirstRoute, time));
				time += 604800000;
				vehicle.addRoute(createRouteForVehicle(vehicle, geometriesForSecondRoute, time));
				time += 604800000;
				vehicle.addRoute(createRouteForVehicle(vehicle, geometriesForThirdRoute, time));
				time += 604800000;
				vehicleRepository.save(vehicle);

			}
		}
		
	}
	
	private Route createRouteForVehicle(Vehicle vehicle, Geometry geometry, long startTime) {
		
		Report report = createReport(vehicle.getUuid());
		
		Route route = new Route();
		route.setStartDate(startTime);
		route.setVehicle(vehicle);
		route = routeRepository.save(route);
		
		double[][] coordinates = geometry.getCoordinates(); 
		
		for (int i = 0; i < coordinates.length; ++i) {
			
			double[] prevPoint = null;
			if (i > 0) prevPoint = coordinates[i - 1];
			
			startTime += 60000;
			
			route.addPoint(createPoint(route, report, coordinates[i], prevPoint, startTime));
			
		}
		
		report.setEndDate(route.getLastRoutePointVisitDate());
		report = reportRepository.save(report);
		route.setEndDate(route.getLastRoutePointVisitDate());
		route = routeRepository.save(route);
		
		return route;
	}
	
	private RoutePoint createPoint(Route route, Report report, double[] point, double[] lastPoint, long visitTime) {
		
		RoutePoint routePoint = new RoutePoint(point[1],
				point[0]);
		
		double x1 = point[1];
		double y1 = point[0];
		
		double x2 = point[1];
		double y2 = point[0];
		
		if (lastPoint != null) {
			x2 = lastPoint[1];
			y2 = lastPoint[0];
		}
			
		report.setResult((Double.parseDouble(report.getResult()) + distance(x1, y1, x2, y2, 'K')) + "");
		report = reportRepository.save(report);
		
		
		routePoint.setVisitDate(visitTime);
		routePoint.setRoute(route);
		
		return routePointRepository.save(routePoint);
		
	}
	
	private Report createReport(UUID vehicleUuid) {

		Report report = new Report();

		report.setStartDate(new Date().getTime());
		report.setType(ReportType.VEHICLE_MILEAGE);
		report.setVehicle(vehicleRepository.findById(vehicleUuid).get());
		report.setPeriod(Period.DAY);
		report.setResult("0.0");

		return reportRepository.save(report);
		
//		this.reportUuid = reportRepository.save(report).getUuid();
//		log.info(this.reportUuid + "");
		
	}

	private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}


	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public Geometry getRoute(String coordinates) {

		Geometry geom = new Geometry();

		try {
			HttpClient client = HttpClientBuilder.create().build();

			HttpPost request = new HttpPost("https://api.openrouteservice.org/v2/directions/driving-car/geojson");

			StringEntity stringentity = new StringEntity(coordinates);

			request.setEntity(stringentity);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Authorization", "5b3ce3597851110001cf6248c6274ee1fd85413489639fc0ee33b899");

			HttpResponse response = client.execute(request);

			String responseBody = EntityUtils.toString(response.getEntity());
			log.info(responseBody);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode track = mapper.readTree(responseBody);
			log.info(track.get("features").get(0).get("geometry") + "");

			geom = mapper.treeToValue(track.get("features").get(0).get("geometry"), Geometry.class);

			log.info(track.get("features").get(0).get("geometry") + "");

			return geom;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return geom;
	}

	private List<GEO> geoBuilder(List<RoutePointDTO> routePointsDTOs, UUID vehicleId) {

		List<GEO> geos = new ArrayList<GEO>();

//		for (RoutePointDTO routePointDTO : routePointsDTOs) {
//			
//			Geometry geometry = new Geometry("RoutePoint", new double[] {routePointDTO.getX(), routePointDTO.getY()});
//			Properties properties = new Properties(vehicleId, routePointDTO.getVisitDate());
//			
//			geos.add(new GEO("RoutePoint", geometry, properties));
//			
//		}

		return geos;

	}

}
