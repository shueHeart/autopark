package com.example.demo.report.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controller.LoginController;
import com.example.demo.report.model.ReportDTO;
import com.example.demo.report.repository.ReportRepository;

@Service
public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

	
	@Autowired
	private ReportRepository reportRepository;
	
	public List<ReportDTO> reportsByVehicleAndStartAndEndDate(@PathVariable("vehicleUuid") UUID vehicleUuid, 
			long startDate, long endDate) {
		log.info(startDate + "");
		log.info(endDate + "");
		
		return reportRepository.findReportsByStartDateAndEndDateAndVehicleUuid(startDate, endDate, vehicleUuid)
				.stream().map(report -> ReportDTO.fromReport(report)).collect(Collectors.toList());
			
		
	}
	
	
}
