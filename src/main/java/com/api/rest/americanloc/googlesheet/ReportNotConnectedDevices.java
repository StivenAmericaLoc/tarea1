package com.api.rest.americanloc.googlesheet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import com.api.rest.americanloc.objetc.AddressObject;
import com.api.rest.americanloc.objetc.InfoDevices;
import com.api.rest.americanloc.objetc.LastPositionObject;
import com.api.rest.americanloc.objetc.LocationObject;
import com.api.rest.americanloc.objetc.ReportObject;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class ReportNotConnectedDevices {

	DBControllerLocator dbConnection = new DBControllerLocator();
	AddressRequest addressRequest = new AddressRequest("http://107.180.76.207:17200");
	DBControllerResults dbConnectionResults = new DBControllerResults();
	public String mainDBURL;
	public String mainDBUser;
	public String mainDBPassword;
	public String sheetId;
	public String sheetName;

	public void startDBConnectionResults(String url, String user, String pwd) {
		try {

			dbConnectionResults.conectar("com.mysql.jdbc.Driver", url, user, pwd);
		} catch (Exception e) {
		}
	}

	public void stopDBConnectionResults() {
		try {
			dbConnectionResults.desconectar();
		} catch (Exception e) {
		}
	}

	public void startDBConnection(String url, String user, String pwd) {
		try {

			dbConnection.conectar("com.mysql.jdbc.Driver", url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopDBConnection() {
		try {
			dbConnection.desconectar();
		} catch (Exception e) {
		}
	}

	/*
	 * public static void main(String... args) { try { ReportNotConnectedDevices
	 * report = new ReportNotConnectedDevices();
	 * report.getInfoForAPIResponseNotConnectedDevices(); } catch (Exception e) {
	 * 
	 * } }
	 */

	/**
	 * Retorna los equipos que no reportan hace mas de 5 días para el cliente
	 * credito real
	 * 
	 * @author Alexandra Bernal
	 * @return JSONArray
	 */
	public JSONArray getInfoForAPIResponseNotConnectedDevices() {
		try {
			ReportObject infoForReport = null;
			LocationObject lastLocationInfo = null;
			SheetConnection sheetConnection = new SheetConnection();
			//readAndSetMainParameters("config.xml");
			BatchGetValuesResponse response = sheetConnection.getInfoSheet(sheetId, sheetName);
			startDBConnection(mainDBURL, mainDBUser, mainDBPassword);

			List<InfoDevices> devicesInfo = dbConnection.getDevicesNotReportingLastXDays(432000000L);// 5 días para
																										// credito real
			int currentLogger = 0;
			JSONArray completeInfo = new JSONArray();
			for (int i = 0; i < devicesInfo.size(); i++) {
				InfoDevices infoDevices = (InfoDevices) devicesInfo.get(i);
				if (infoDevices.getLogger() != currentLogger) {
					try {
						stopDBConnectionResults();
					} catch (NullPointerException exce) {
					}
					currentLogger = infoDevices.getLogger();
					startDBConnectionResults(infoDevices.getUrl(), infoDevices.getUser(), infoDevices.getPassword());
				}
				LastPositionObject lastPositionObject = obtainLastPosition(infoDevices.getDeviceId(),
						infoDevices.getModel());
				infoForReport = getInfoForDeviceInInfoSheet(response, infoDevices.getNick());

				if (lastPositionObject == null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(infoDevices.getDateMillis());
					calendar.setTimeZone(TimeZone.getTimeZone("America/Mexico"));
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
					String dateIssued = f.format(calendar.getTimeInMillis());
					lastLocationInfo = new LocationObject(dateIssued, "0", "0", "", "", "", "");
					infoForReport.setLastLocationInfo(lastLocationInfo);
					Long daysNotReporting = (System.currentTimeMillis() - infoDevices.getDateMillis()) / (86400000);
					Integer days = daysNotReporting.intValue();
					infoForReport.setDaysNotReporting(days.toString());
					infoForReport.setFailureType(
							"No estamos recibiendo datos de este equipo. Puede estar en una zona sin cobertura o apagado.");
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(lastPositionObject.getTime());
					calendar.setTimeZone(TimeZone.getTimeZone("America/Mexico"));
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
					String dateIssued = f.format(calendar.getTimeInMillis());
					lastLocationInfo = new LocationObject(dateIssued, lastPositionObject.getLatitude().toString(),
							lastPositionObject.getLongitude().toString(), "", "", "", "");
					// getJSONByOpenStreetMap(lastPositionObject.latitude,lastPositionObject.longitude,lastLocationInfo);
					AddressObject address = addressRequest.readAddressesInfoRestOfTheWorld(
							lastPositionObject.getLatitude().toString(), lastPositionObject.getLongitude().toString());
					lastLocationInfo.setAddress(address.getStreet() + " " + address.getHouseNumber());
					;
					lastLocationInfo.setPc(address.getZipCode());
					;
					lastLocationInfo.setCity(address.getCity());
					;
					lastLocationInfo.setState(address.getState());
					;
					infoForReport.setLastLocationInfo(lastLocationInfo);
					;
					Long daysNotReporting = (System.currentTimeMillis() - lastPositionObject.getTime()) / (86400000);
					Integer days = daysNotReporting.intValue();
					infoForReport.setDaysNotReporting(days.toString());
					if (infoDevices.getFailureType().compareTo("keepalive") == 0) {
						infoForReport.setFailureType(
								"No estamos recibiendo datos de este equipo. Puede estar en una zona sin cobertura o apagado.");
					} else {
						infoForReport.setFailureType(
								"El equipo no puede obtener posicion. Algun elemento puede estar obstruyendo la señal GPS del equipo.");
					}

				}

				JSONObject deviceInfoForAPI = new JSONObject();
				deviceInfoForAPI.append("consecutivo", infoForReport.getConsecutive());
				deviceInfoForAPI.append("operacion", infoForReport.getOperation());
				deviceInfoForAPI.append("imei", infoForReport.getIMEI());
				deviceInfoForAPI.append("senuelo", infoForReport.getSenuelo());
				deviceInfoForAPI.append("diassinreportar", Integer.parseInt(infoForReport.getDaysNotReporting()));
				JSONObject infoInstalacionForAPI = new JSONObject();
				infoInstalacionForAPI.append("fechayhorains", infoForReport.getInstallationInfo().getDate());
				infoInstalacionForAPI.append("latitudins", infoForReport.getInstallationInfo().getLatitude());
				infoInstalacionForAPI.append("longitudins", infoForReport.getInstallationInfo().getLongitude());
				infoInstalacionForAPI.append("ciudadins", infoForReport.getInstallationInfo().getCity());
				infoInstalacionForAPI.append("estadoins", infoForReport.getInstallationInfo().getState());
				infoInstalacionForAPI.append("cpins", infoForReport.getInstallationInfo().getPc());
				infoInstalacionForAPI.append("direccionins", infoForReport.getInstallationInfo().getAddress());
				infoInstalacionForAPI.append("linkins",
						"https://www.google.com.mx/maps/place/" + infoForReport.getInstallationInfo().getAddress());
				deviceInfoForAPI.append("infoinstalacion", infoInstalacionForAPI);
				JSONObject infoUltimaPosicionForAPI = new JSONObject();
				infoUltimaPosicionForAPI.append("fechayhoraubicacion", infoForReport.getLastLocationInfo().getDate());
				infoUltimaPosicionForAPI.append("latitudubicacion", infoForReport.getLastLocationInfo().getLatitude());
				infoUltimaPosicionForAPI.append("longitudubicacion",
						infoForReport.getLastLocationInfo().getLongitude());
				infoUltimaPosicionForAPI.append("ciudadubicacion", infoForReport.getLastLocationInfo().getCity());
				infoUltimaPosicionForAPI.append("estadoubicacion", infoForReport.getLastLocationInfo().getState());
				infoUltimaPosicionForAPI.append("cpubicacion", infoForReport.getLastLocationInfo().getPc());
				infoUltimaPosicionForAPI.append("direccionubicacion", infoForReport.getLastLocationInfo().getAddress());
				infoUltimaPosicionForAPI.append("tipofalla", infoForReport.getFailureType());
				infoUltimaPosicionForAPI.append("linkubicacion",
						"https://www.google.com/maps/search/?api=1&query="
								+ infoForReport.getLastLocationInfo().getLatitude() + ","
								+ infoForReport.getLastLocationInfo().getLongitude());

				deviceInfoForAPI.append("infoultimaubicacion", infoUltimaPosicionForAPI);
				completeInfo.put(deviceInfoForAPI);
				/*
				 * System.out.println(infoDevices.deviceId+"\t"+infoForReport.consecutive+"\t"+
				 * infoForReport.operation+"\t"+infoForReport.IMEI+"\t"+
				 * infoForReport.senuelo+"\t"+infoForReport.installationInfo.date+"\t"+
				 * infoForReport.installationInfo.latitude+"\t"+
				 * infoForReport.installationInfo.longitude+"\t"+infoForReport.installationInfo.
				 * city+"\t"+infoForReport.installationInfo.state+"\t"+
				 * infoForReport.installationInfo.pc+"\t"+infoForReport.installationInfo.address
				 * +"\t"+"https://www.google.com.mx/maps/place/"+infoForReport.installationInfo.
				 * address+"\t"+infoForReport.lastLocationInfo.date+"\t"+
				 * infoForReport.lastLocationInfo.latitude+"\t"+infoForReport.lastLocationInfo.
				 * longitude+"\t"+infoForReport.failureType+"\t"+infoForReport.daysNotReporting+
				 * "\t"+infoForReport.lastLocationInfo.city+"\t"+
				 * infoForReport.lastLocationInfo.state+"\t"+infoForReport.lastLocationInfo.pc+
				 * "\t"+infoForReport.lastLocationInfo.address+"\t"+
				 * "https://www.google.com/maps/search/?api=1&query="+infoForReport.
				 * lastLocationInfo.latitude+","+infoForReport.lastLocationInfo.longitude);
				 */

			}
			// System.out.println(completeInfo);
			stopDBConnectionResults();
			stopDBConnection();
			return completeInfo;
		} catch (Exception e) {
			System.out.println(e);
			stopDBConnectionResults();
			stopDBConnection();
			return null;
		} finally {
			stopDBConnectionResults();
			stopDBConnection();
		}
	}

	/*public boolean readAndSetMainParameters(String nombreArchivoConfiguracion) {

		Document doc = null;
		// logger.info("EL ARCHIVO ES "+pathToConfig);
		try {
			InputStream stream = this.getClass().getResourceAsStream("/static/config/" + nombreArchivoConfiguracion);
			// File docFile = new
			// File("resources/static/config/"+nombreArchivoConfiguracion);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(stream);
			Element root = doc.getDocumentElement();

			Element MainDBURL = (Element) (root.getElementsByTagName("MainDBURL").item(0));
			mainDBURL = MainDBURL.getFirstChild().getNodeValue();

			Element MainDBUser = (Element) (root.getElementsByTagName("MainDBUser").item(0));
			mainDBUser = MainDBUser.getFirstChild().getNodeValue();

			Element MainDBPSW = (Element) (root.getElementsByTagName("MainDBPSW").item(0));
			mainDBPassword = MainDBPSW.getFirstChild().getNodeValue();

			Element SheetID = (Element) (root.getElementsByTagName("SheetId").item(0));
			sheetId = SheetID.getFirstChild().getNodeValue();

			Element SheetName = (Element) (root.getElementsByTagName("SheetId").item(0));
			sheetName = SheetName.getFirstChild().getNodeValue();

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			// logger.info("EXCEPTION LEYENDO CONFIG "+e);
			return false;
			// AQUI DEBERIA RETORNAR Y ESCRIBIR EN LOG QUE NO PUDO INICIAR System.exit(1);
		}

	}*/

	public LastPositionObject obtainLastPosition(Integer iddevice, Integer modelId) {
		LastPositionObject lastPosition = null;
		if (modelId == 3) {// enfora
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_enfora_mtg_micro_results");

		} else if (modelId == 4) {// portatil
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_skypatrol_tt8850_results");

		} else if (modelId == 5) {// 8750plus
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_skypatrol_tt8750plus_results");

		} else if (modelId == 6) {// 9500
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_skypatrol_tt9500_results");

		} else if (modelId == 7) {// Sanav
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_sanav_mu201_results");

		} else if (modelId == 8) {// GV55
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_queclink_gv55_results");

		} else if (modelId == 9) {// GV300
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_queclink_gv300_results");

		} else if (modelId == 12) {// GV500
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_queclink_gv500_results");

		} else if (modelId == 14) {// GL300
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_queclink_gl300_results");

		} else if (modelId == 15) {// GL300W
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");

		} else if (modelId == 16) {// MG2G
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "sp_oigo_mg2g_results");

		} else if (modelId == 17) {// ST300
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");

		} else if (modelId == 19) {// AR2G
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");

		} else if (modelId == 20) {// FM1120
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");

		} else if (modelId == 22) {// GV75w
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");

		} else if (modelId == 23) {// GL300M
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");
		} else if (modelId == 24) {// ST4340
			lastPosition = dbConnectionResults.getLastPosition(iddevice.toString(), "last_result");

		}

		return lastPosition;

	}

	public ReportObject getInfoForDeviceInInfoSheet(BatchGetValuesResponse response, String deviceName) {
		try {
			// Range:DEVICEID
			ValueRange devicesId = response.getValueRanges().get(0);
			List<List<Object>> valuesDevicesId = devicesId.getValues();

			// Range1 :DIRECCION, COLONIA,CP,ESTADO, CIUDAD
			ValueRange range1 = response.getValueRanges().get(1);
			List<List<Object>> valuesRange1 = range1.getValues();

			// Range2 :IMEI, CONSECUTIVO,OPERACION, LATITUD Y LONGITUD
			ValueRange range2 = response.getValueRanges().get(2);
			List<List<Object>> valuesRange2 = range2.getValues();

			// Range3 :FECHA Y HORA
			ValueRange range3 = response.getValueRanges().get(3);
			List<List<Object>> valuesRange3 = range3.getValues();

			// Range4 :SENUELO
			ValueRange range4 = response.getValueRanges().get(4);
			List<List<Object>> valuesRange4 = range4.getValues();

			int deviceRow = 0;

			if (valuesDevicesId == null || valuesDevicesId.isEmpty()) {
				System.out.println("No data found.");
			} else {

				for (List<Object> row : valuesRange2) {
					// Print columns A and E, which correspond to indices 0 and 4.
					if (row.size() > 0) {
						// System.out.printf("%s\n", row.get(0));
						if (row.contains(deviceName)) {
							// System.out.println(valuesDevicesId.indexOf(row));
							deviceRow = valuesRange2.indexOf(row);
							break;
						} else {
						}

					}
				}
			}

			String address = "";
			String consecutive = "";
			String operation = "";
			String IMEI = "";
			String senuelo = "";
			String date = "";
			String hour = "";
			String latitude = "";
			String longitude = "";
			String city = "";
			String state = "";
			String pc = "";
			List<Object> rowRange1 = valuesRange1.get(deviceRow);
			try {
				address = (String) rowRange1.get(0);
			} catch (NullPointerException ex) {
				address = "";
			}
			try {
				pc = (String) rowRange1.get(2);
			} catch (NullPointerException ex) {
				pc = "";
			}
			try {
				state = (String) rowRange1.get(3);
			} catch (NullPointerException ex) {
				state = "";
			}
			try {
				city = (String) rowRange1.get(4);
			} catch (NullPointerException ex) {
				city = "";
			}
			List<Object> rowRange2 = valuesRange2.get(deviceRow);
			try {
				IMEI = (String) rowRange2.get(0);
			} catch (NullPointerException ex) {
				IMEI = "";
			}
			try {
				consecutive = (String) rowRange2.get(1);
			} catch (NullPointerException ex) {
				consecutive = "";
			}
			boolean smallSize = false;
			if (rowRange2.size() == 2) {
				operation = "";
				latitude = "";
				longitude = "";
				smallSize = true;
			} else {
				try {
					operation = (String) rowRange2.get(2);
				} catch (NullPointerException ex) {
					operation = "";
				}
			}
			if (rowRange2.size() == 3) {
				latitude = "";
				longitude = "";
			} else if (!smallSize) {
				try {
					latitude = ((String) rowRange2.get(3)).replaceAll(",", ".");

				} catch (NullPointerException ex) {
					latitude = "";
				}
				try {

					longitude = ((String) rowRange2.get(4)).replaceAll(",", ".");
				} catch (NullPointerException ex) {
					longitude = "";
				}
			}
			List<Object> rowRange3 = valuesRange3.get(deviceRow);
			try {
				date = (String) rowRange3.get(0);
			} catch (NullPointerException ex) {
				date = "";
			}
			if (rowRange3.size() == 1) {
				hour = "";
			} else {
				try {
					hour = (String) rowRange3.get(1);
				} catch (NullPointerException ex) {
					hour = "";
				}
			}
			List<Object> rowRange4 = valuesRange4.get(deviceRow);
			if (rowRange4.size() == 0) {
				senuelo = "NO";
			} else {
				try {
					senuelo = (String) rowRange4.get(0);
					if (senuelo.compareTo("0") == 0 || senuelo.trim().compareTo("") == 0) {
						senuelo = "NO";
					} else {
						senuelo = "SI";
					}
				} catch (NullPointerException ex) {
					senuelo = "";
				}
			}
			LocationObject installationInfo = new LocationObject(date + " " + hour, latitude, longitude, city, state,
					pc, address);
			ReportObject reportObject = new ReportObject(consecutive, operation, IMEI, senuelo, installationInfo, null,
					"");
			// System.out.println(consecutive + " " + operation + " " + IMEI + " " + senuelo
			// + " " + date + " " + hour + " " + latitude + " " + longitude + " " + city + "
			// " + state + " " + pc + " " + address);
			return reportObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Here the fullAddress String is in format like "address,city,state,zipcode".
	 * Here address means "street number + route" .
	 *
	 */
	public String getJSONByOpenStreetMap(Float latitude, Float longitude, LocationObject locationObject) {

		/*
		 * Create an java.net.URL object by passing the request URL in constructor. Here
		 * you can see I am converting the fullAddress String in UTF-8 format. You will
		 * get Exception if you don't convert your address in UTF-8 format. Perhaps
		 * google loves UTF-8 format. :) In parameter we also need to pass "sensor"
		 * parameter. sensor (required parameter) — Indicates whether or not the
		 * geocoding request comes from a device with a location sensor. This value must
		 * be either true or false.
		 */
		try {
			URL oracle = new URL("https://nominatim.openstreetmap.org/reverse.php?lat=" + latitude.toString() + "&lon="
					+ longitude.toString() + "&format=jsonv2");
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String inputLine;
			StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				stringBuilder.append(inputLine);
			}

			String response = stringBuilder.toString();
			String stringEncoded = URLDecoder.decode(response, "ISO-8859-1");
			in.close();
			JSONObject jsonObj = new JSONObject(stringEncoded);
			JSONObject jsonAddressObj = (JSONObject) jsonObj.get("address");
			if (stringEncoded.contains("postcode")) {
				locationObject.setPc((String) jsonAddressObj.get("postcode"));
				;
			} else {
				locationObject.setPc("");
				;
			}
			if (stringEncoded.contains("state")) {
				locationObject.setState((String) jsonAddressObj.get("state"));
				;
			} else {
				locationObject.setState("");
				;
			}
			if (stringEncoded.contains("city")) {
				locationObject.setCity((String) jsonAddressObj.get("city"));
				;
			} else {
				locationObject.setCity("");
				;
			}
			if (stringEncoded.contains("road")) {
				locationObject.setAddress((String) jsonAddressObj.get("road"));
				;
			} else {
				locationObject.setAddress("");
				;
			}
			return stringEncoded; // This returned String is JSON string from which you can retrieve all key value
									// pair and can save it in POJO.
		} catch (Exception e) {
			return null;
		}
	}

}
