package com.example.demo.report.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.report.model.Report;
import com.example.demo.route.model.Route;

@Repository
public interface ReportRepository extends CrudRepository<Report, UUID> {

	 @Query("SELECT r FROM Report r WHERE r.startDate >= :start AND r.endDate <= :end AND r.vehicle.uuid = :vehicleUuid")
	 List<Report> findReportsByStartDateAndEndDateAndVehicleUuid(@Param("start") long start, @Param("end") long end, @Param("vehicleUuid") UUID vehicleUuid);
	
}
