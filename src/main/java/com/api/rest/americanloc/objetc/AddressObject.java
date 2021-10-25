package com.api.rest.americanloc.objetc;

public class AddressObject {

	private String name;
	private double distance;
	private String street;
	private String houseNumber;
	private String zipCode;
	private String city;
	private String state;
	private String latitude;
	private String longitude;

	public AddressObject(String name, double distance, String street, String houseNumber, String zipCode, String city,
			String state) {
		this.name = name;
		this.distance = distance;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
	}

	public AddressObject(String latitude, String longitude, String street, String houseNumber, String zipCode,
			String city, String state) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

}
