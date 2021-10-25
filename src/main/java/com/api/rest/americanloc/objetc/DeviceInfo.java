package com.api.rest.americanloc.objetc;

import java.util.TimeZone;

public class DeviceInfo {

	private int idDevice;
	private int clientId;
	private String IMEI;
	private int model;
	private String nick;
	private String color;
	private String modelName;
	private long since;
	private Integer messageDistributor;
	private int authorized;
	private TimeZone timeZone;
	private float timeOffset_hours;
	private String units;

	private int serviceId;

	private int groupIdForThisUser;

	private String serialNumberType;
	private String serialNumber;

	private String subModel;

	private Integer realTimeLocation = null;

	/*
	 * public int circleGeofence; public int polygonGeofence; public int
	 * routeGeofence; public int lowBattEvent; public int offEvent; public int
	 * speedEvent; public int distanceTracking; public int timeTracking; public int
	 * timeDistanceTracking; public int idleTimeEvent; public int
	 * invisibleTimeEvent; public int parkingEventByGeofence; public int callNumber;
	 * public int trackingByDays; public int trackingByHours; public int
	 * zoneAlarmByDays; public int zoneAlarmByHours;
	 * 
	 */

	public DeviceInfo() {

	}

	// DeviceInfo(String idDevice,String userId,int model,String number,String
	// protocol,String nick,String modelName)
	public DeviceInfo(int idDevice, int serviceId, int userId, int model, int messageDistributor, String nick,
			String color, String modelName, String serialNumberType, String serialNumber, String subModel) {
		// used in admin app
		this.idDevice = idDevice;
		this.serviceId = serviceId;
		this.clientId = userId;
		this.model = model;
		this.nick = nick;
		this.modelName = modelName;
		this.color = color;
		this.messageDistributor = messageDistributor;

		this.serialNumberType = serialNumberType;
		this.serialNumber = serialNumber;
		this.subModel = subModel;

	}

//public DeviceInfo(String idDevice,String userId,String nick,String color,String modelName,String modelId)
	public DeviceInfo(int idDevice, int serviceId, int userId, String nick, String color, String modelName, int modelId,
			int messageDistributor, TimeZone timeZone, float timeOffset_hours, String serialNumberType,
			String serialNumber, String subModel) {
		this.idDevice = idDevice;
		this.serviceId = serviceId;
		this.clientId = userId;
		this.nick = nick;
		this.color = color;
		this.modelName = modelName;
		this.model = modelId;
		this.messageDistributor = messageDistributor;
		this.timeOffset_hours = timeOffset_hours;

		this.serialNumberType = serialNumberType;
		this.serialNumber = serialNumber;
		this.subModel = subModel;
	}

	// public DeviceInfo(String idDevice,int model,String phoneNumber,String ip,int
	// port,Integer messageDistributor,String protocol,String modelName,String
	// nick,float timeOffsetInHours,String IMEI)

	public void setDeviceGroupForUser(int groupId) {
		groupIdForThisUser = groupId;
	}

	public DeviceInfo(int idDevice, int serviceId, int userId, int model, String nick, String color, String modelName,
			String serialNumberType, String serialNumber, String subModel) {
		this.idDevice = idDevice;
		this.serviceId = serviceId;
		this.clientId = userId;
		this.model = model;
		this.nick = nick;
		this.modelName = modelName;
		this.color = color;

		this.serialNumberType = serialNumberType;
		this.serialNumber = serialNumber;
		this.subModel = subModel;
	}

	public int getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public long getSince() {
		return since;
	}

	public void setSince(long since) {
		this.since = since;
	}

	public Integer getMessageDistributor() {
		return messageDistributor;
	}

	public void setMessageDistributor(Integer messageDistributor) {
		this.messageDistributor = messageDistributor;
	}

	public int getAuthorized() {
		return authorized;
	}

	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public float getTimeOffset_hours() {
		return timeOffset_hours;
	}

	public void setTimeOffset_hours(float timeOffset_hours) {
		this.timeOffset_hours = timeOffset_hours;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getGroupIdForThisUser() {
		return groupIdForThisUser;
	}

	public void setGroupIdForThisUser(int groupIdForThisUser) {
		this.groupIdForThisUser = groupIdForThisUser;
	}

	public String getSerialNumberType() {
		return serialNumberType;
	}

	public void setSerialNumberType(String serialNumberType) {
		this.serialNumberType = serialNumberType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSubModel() {
		return subModel;
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

	public Integer getRealTimeLocation() {
		return realTimeLocation;
	}

	public void setRealTimeLocation(Integer realTimeLocation) {
		this.realTimeLocation = realTimeLocation;
	}

}
