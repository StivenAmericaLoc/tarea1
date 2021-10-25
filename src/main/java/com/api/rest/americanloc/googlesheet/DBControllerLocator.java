package com.api.rest.americanloc.googlesheet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.api.rest.americanloc.objetc.InfoDevices;

public class DBControllerLocator {

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
			e.printStackTrace();
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

	public List<InfoDevices> getDevicesNotReportingLastXDays(Long daysBack) {

		List<InfoDevices> info = new ArrayList<>();
		ResultSet r = null;
		PreparedStatement s = null;
		try {
			// select devices.nick,devices.model,devices_zoombak100_101.*

			String comando = "SELECT distinct(current_device_failures.device_id), current_device_failures.event_time_millis, devices.messageDistributor,admin_devices_distribution_groups.resultsdb_password,\n"
					+ "admin_devices_distribution_groups.resultsdb_url,admin_devices_distribution_groups.resultsdb_user,devices.model,current_device_failures.failure_type,devices.nick\n"
					+ "FROM current_device_failures \n"
					+ "left join devices on current_device_failures.device_id=devices.iddevice\n"
					+ "left join clients on devices.userid=clients.clientId\n"
					+ "left join admin_services on devices.service_id=admin_services.id\n"
					+ "left join admin_devices_distribution_groups on devices.messageDistributor=admin_devices_distribution_groups.id\n"
					+ "where current_device_failures.event_time_millis<? and clients.clientId=179 and admin_services.status_id=2\n"
					+ "order by admin_devices_distribution_groups.id;";
			s = conn.prepareStatement(comando);
			// System.out.println(nextWeek + " " + next2Weeks + "" + comando);
			Long now = System.currentTimeMillis();
			Long lookFor = now - daysBack;
			s.setLong(1, lookFor);
			r = s.executeQuery();

			while (r.next()) {
				try {
					int deviceId = r.getInt(1);
					Long dateMillis = r.getLong(2);
					int logger = r.getInt(3);
					String password = r.getString(4);
					String url = r.getString(5);
					String user = r.getString(6);
					int model = r.getInt(7);
					String failureType = r.getString(8);
					String nick = r.getString(9);
					InfoDevices infoDevices = new InfoDevices(deviceId, dateMillis, logger, user, password, url, model,
							failureType, nick);
					info.add(infoDevices);
				} catch (Exception e) {
					continue;
				}

			}
			r.close();
			s.close();

			return info;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				r.close();
				s.close();
			} catch (Exception x) {
			}
			return null;
			// throw new GPSLocatorException(1, "ControladorBaseDatos",
			// "getHistoryPointsWithinInterval()",e.toString(), null);
		}

	}

}
