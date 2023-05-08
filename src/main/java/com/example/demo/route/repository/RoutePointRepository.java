package com.example.demo.route.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.route.model.RoutePoint;
import com.example.demo.vehicle.model.Vehicle;

@Repository
public interface RoutePointRepository extends JpaRepository<RoutePoint, UUID> {
	
    List<RoutePoint> findByRouteVehicleAndVisitDateBetween(Vehicle vehicle, long fromVisitDate, long toVisitDate);

}
