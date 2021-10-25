package com.api.rest.americanloc.objetc;

public class InfoDevices {

	private int deviceId;
	private Long dateMillis;
	private int logger;
	private String user;
	private String password;
	private String url;
	private int model;
	private String failureType;
	private String nick;

	public InfoDevices(int deviceId, Long dateMillis, int logger, String user, String password, String url, int model,
			String failureType, String nick) {
		this.deviceId = deviceId;
		this.dateMillis = dateMillis;
		this.logger = logger;
		this.user = user;
		this.password = password;
		this.url = url;
		this.model = model;
		this.failureType = failureType;
		this.nick = nick;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Long getDateMillis() {
		return dateMillis;
	}

	public void setDateMillis(Long dateMillis) {
		this.dateMillis = dateMillis;
	}

	public int getLogger() {
		return logger;
	}

	public void setLogger(int logger) {
		this.logger = logger;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

}
