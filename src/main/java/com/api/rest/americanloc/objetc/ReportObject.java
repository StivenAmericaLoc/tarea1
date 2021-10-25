package com.api.rest.americanloc.objetc;

public class ReportObject {

	private String IMEI;
	private String address;
	private String consecutive;
	private String operation;
	private String senuelo;
	private String date;
	private String hour;
	private String latitude;
	private String longitude;
	private String city;
	private String state;
	private String pc;
	private LocationObject installationInfo;
	private LocationObject lastLocationInfo;
	private String daysNotReporting;
	private String failureType = "";

	public ReportObject(String consecutive, String operation, String IMEI, String senuelo,
			LocationObject installationInfo, LocationObject lastLocationInfo, String daysNotReporting) {
		this.consecutive = consecutive;
		this.operation = operation;
		this.IMEI = IMEI;
		this.senuelo = senuelo;
		this.installationInfo = installationInfo;
		this.lastLocationInfo = lastLocationInfo;
		this.daysNotReporting = daysNotReporting;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConsecutive() {
		return consecutive;
	}

	public void setConsecutive(String consecutive) {
		this.consecutive = consecutive;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSenuelo() {
		return senuelo;
	}

	public void setSenuelo(String senuelo) {
		this.senuelo = senuelo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public LocationObject getInstallationInfo() {
		return installationInfo;
	}

	public void setInstallationInfo(LocationObject installationInfo) {
		this.installationInfo = installationInfo;
	}

	public LocationObject getLastLocationInfo() {
		return lastLocationInfo;
	}

	public void setLastLocationInfo(LocationObject lastLocationInfo) {
		this.lastLocationInfo = lastLocationInfo;
	}

	public String getDaysNotReporting() {
		return daysNotReporting;
	}

	public void setDaysNotReporting(String daysNotReporting) {
		this.daysNotReporting = daysNotReporting;
	}

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

}
