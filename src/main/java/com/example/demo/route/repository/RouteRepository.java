package com.example.demo.route.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.route.model.Route;
import com.example.demo.route.model.RoutePoint;
import com.example.demo.vehicle.model.Vehicle;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID>, PagingAndSortingRepository<Route, UUID>{

	 @Query("SELECT r FROM Route r WHERE r.startDate >= :start AND r.endDate <= :end")
	 List<Route> findRoutesByStartDateAndEndDate(@Param("start") long start, @Param("end") long end);
	 
	 List<Route> findAllByVehicleUuid(UUID vehicleUuid);
	
	 
	 @Query("SELECT r FROM Route r WHERE r.vehicle.uuid = :vehicleId AND r.startDate >= :start AND r.endDate <= :end")
	 List<Route> findRoutesByVehicleUuidAndStartDateAndEndDate(@Param("vehicleId") UUID vehicleId, @Param("start") long start, @Param("end") long end);
	 
	 Page<Route> findAllByVehicleUuid(UUID vehicleUuid, Pageable pageable);
//	 List<Route> findByVehicleAndVisitDateBetween(Vehicle vehicle, long fromVisitDate, long toVisitDate);

	 
}
