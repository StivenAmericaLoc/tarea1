package com.api.rest.americanloc.back.reporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.americanloc.back.reporte.service.IReporteService;

@RestController
@RequestMapping("/report")
public class ReporteController {
	
	@Autowired
	private IReporteService reporteService;
	
	@GetMapping("/cr")
	public ResponseEntity<String> getReporteCr(@RequestHeader("Authorization") String token) {
		return reporteService.getReporteCr(token);
	}

}
