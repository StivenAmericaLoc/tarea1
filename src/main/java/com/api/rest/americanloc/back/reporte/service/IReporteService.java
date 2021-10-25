package com.api.rest.americanloc.back.reporte.service;

import org.springframework.http.ResponseEntity;

public interface IReporteService {
	
	public ResponseEntity<String> getReporteCr(String token);

}
