package com.api.rest.americanloc.back.reporte.service;

import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.rest.americanloc.back.config.dao.IConfigDAO;
import com.api.rest.americanloc.back.config.entity.Config;
import com.api.rest.americanloc.back.user.service.IUserTokenService;
import com.api.rest.americanloc.googlesheet.ReportNotConnectedDevices;

@Service
public class ReporteService implements IReporteService {
	
	private static final int ID_MAIN_DB_URL = 1;
	private static final int ID_MAIN_DB_USER = 2;
	private static final int ID_MAIN_DB_PSW = 3;
	private static final int ID_SHEET_ID = 8;
	private static final int ID_SHEET_NAME = 9;
	
	@Autowired
	private IUserTokenService userTokenService;
	
	@Autowired
	private IConfigDAO configDao;

	@Override
	public ResponseEntity<String> getReporteCr(String token) {
		boolean validarToken = userTokenService.isAvalibleToken(token);
		if (validarToken) {
			ReportNotConnectedDevices reportConection = new ReportNotConnectedDevices();
			Optional<Config> config = configDao.findById(ID_MAIN_DB_URL);
			reportConection.mainDBURL = config.get().getData();
			config = configDao.findById(ID_MAIN_DB_USER);
			reportConection.mainDBUser = config.get().getData();
			config = configDao.findById(ID_MAIN_DB_PSW);
			reportConection.mainDBPassword = config.get().getData();
			config = configDao.findById(ID_SHEET_ID);
			reportConection.sheetId = config.get().getData();
			config = configDao.findById(ID_SHEET_NAME);
			reportConection.sheetName = config.get().getData();
			JSONArray respues = reportConection.getInfoForAPIResponseNotConnectedDevices();
			if (respues != null) {
				return new ResponseEntity<String>(respues.toString(), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Hubo un fallo al obtener el reporte", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} else {
			return new ResponseEntity<String>("Token invalido", HttpStatus.FORBIDDEN);
		}
	}
	
	

}
