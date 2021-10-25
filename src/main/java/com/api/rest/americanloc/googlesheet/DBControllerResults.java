package com.api.rest.americanloc.googlesheet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.api.rest.americanloc.objetc.LastPositionObject;

public class DBControllerResults {

	public Connection conn = null;

	public boolean validateIfIsConnectedToDB() {

		try {
			if (conn == null) {
				return false;
			}
			// return !conn.isClosed();
			boolean valid = conn.isValid(10);
			// logger.debug("CONEXION ES VALIDA MYSQL " + valid);
			return valid;
		} catch (Exception e) {
			// logger.error("Exception", e);
			return false;
		}
	}

	public void desconectar() {
		try {
			conn.close();
			// System.out.println(conn.isClosed());
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public boolean setAutoCommit(boolean autoCommit) {
		try {
			conn.setAutoCommit(autoCommit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean commit() {
		try {
			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean rollBack() {
		try {
			conn.rollback();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean conectar(String driverBaseDatos, String urlBaseDatos, String userName, String password) {

		try {
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			connectionProps.put("useSSL", "false");

			Class.forName(driverBaseDatos);
			conn = DriverManager.getConnection(urlBaseDatos, connectionProps);
			return true;
		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			desconectar();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			desconectar();
			return false;
		}
	}

	public LastPositionObject getLastPosition(String deviceId, String table) {
		ResultSet r;
		LastPositionObject resultPoint = null;

		try {

			String comando = "select " + table + ".latitude," + table + ".longitude," + table + ".devicetime," + table
					+ ".speed from " + table + " where (iddevice=" + deviceId
					+ " and locationType<=1 ) order by devicetime desc limit 1;";
			// System.out.println(comando);
			Statement s = conn.createStatement();
			r = s.executeQuery(comando);
			while (r.next()) {
				// idresult, idprueba, time
				try {
					Float latitude = r.getFloat(1);
					// System.out.println("latitude "+latitude);
					Float longitude = r.getFloat(2);
					Long time = r.getLong(3);
					String speed = r.getString(4);
					// System.out.println("deviceName "+deviceName);
					try {
						resultPoint = new LastPositionObject("", Integer.parseInt(deviceId), latitude, longitude,
								Double.parseDouble(speed), time);
					} catch (Exception x) {

						continue;
					}

				} catch (Exception exe) {

					continue;
				}

			}

			r.close();
			s.close();
		} catch (Exception e) {
			// logger.error("getTrackingInformationForEnfora", e);
			return null;
			// throw new GPSLocatorException(1, "ControladorBaseDatos",
			// "getHistoryPointsWithinInterval()",e.toString(), null);
		}
		return resultPoint;
	}

}
