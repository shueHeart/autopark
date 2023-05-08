package com.example.demo.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.report.model.Period;
import com.example.demo.report.model.ReportDTO;
import com.example.demo.report.repository.ReportRepository;
import com.example.demo.report.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ReportController extends BaseController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@GetMapping("/vehicle/{vehicleUuid}/reportsss")
	public List<ReportDTO> reportsByVehicleAndStartAndEndDate(@PathVariable("vehicleUuid") UUID vehicleUuid, 
			@RequestParam("startDate") long startDate, @RequestParam("endDate") long endDate) {		
		
		
		return reportService.reportsByVehicleAndStartAndEndDate(vehicleUuid, startDate, endDate);
			
	}
	
	@GetMapping("/reports") 
	public ModelAndView getAllReportsInView() {
		
		ModelAndView reports = new ModelAndView("reports");
		
		reports.addObject("reports", reportRepository.findAll());
		
		TimeZone timeZone = TimeZone.getTimeZone(getCurrentUser().getTimezone());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		date.setTimeZone(timeZone);
		reports.addObject("date", date);
		
		reports.addObject("periods", Period.values());
		
		
		
		return reports;
		
	}
	
}
