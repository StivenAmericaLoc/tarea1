package com.api.rest.americanloc.objetc;

public class LastPositionObject {

	private String deviceName;
	private Integer idDevice;
	private Float latitude;
	private Float longitude;
	private Double speed;
	private Long time;
	private DeviceInfo deviceInfo;
	private Integer type;

	public LastPositionObject(String deviceName, Integer idDevice, Float latitude, Float longitude, Double speed,
			Long time) {
		this.deviceName = deviceName;
		this.idDevice = idDevice;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.time = time;
	}

	public LastPositionObject(String deviceName, Integer idDevice, Float latitude, Float longitude, Double speed,
			Long time, Integer type) {
		this.deviceName = deviceName;
		this.idDevice = idDevice;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.time = time;
		this.type = type;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(Integer idDevice) {
		this.idDevice = idDevice;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
