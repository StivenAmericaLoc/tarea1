package com.api.rest.americanloc.googlesheet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.api.rest.americanloc.objetc.AddressObject;

public class AddressRequest {

	String gisgraphyURL = "http://107.180.76.207:17200";

	public AddressRequest(String gisgraphyURL) {
		this.gisgraphyURL = gisgraphyURL;
	}

	public String readAddresesInfo(String lat, String longitude) {

		try {

			Double latDouble = Double.parseDouble(lat);
			Double longDouble = Double.parseDouble(longitude);
			if (latDouble < 12.2362 && longDouble > -80.401929 && -34.005679 > longDouble) { // Suramerica

				return (readAddresesInfoCol(lat, longitude));
			} else { // resto del mundo
				return "";
				// return readAddressesInfoRestOfTheWorld(lat, longitude);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public AddressObject readAddressesInfoRestOfTheWorld(String lat, String longitude) {

		AddressObject addressObject = null;
		String houseNumber = "";
		String zipCode = "";
		String street = "";
		String city = "";
		String state = "";
		try {

			URL url = new URL(gisgraphyURL + "/reversegeocoding/search?format=XML&lat=" + lat + "&lng=" + longitude
					+ "&from=1&to=10&indent=true");
			// new
			// URL(gisgraphyURL+"/street/search?format=XML&lat="+lat+"&lng="+longitude+"&radius=300&streettype=&from=1&to=2&distance=true");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			String entrada;
			String cadena = "";

			while ((entrada = br.readLine()) != null) {
				cadena = cadena + entrada;
			}
			// System.out.println(cadena);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource archivo = new InputSource();
			archivo.setCharacterStream(new StringReader(cadena));

			Document documento = db.parse(archivo);
			documento.getDocumentElement().normalize();
			NodeList nodes = documento.getElementsByTagName("result");
			//Vector<AddressObject> addressResults = new Vector<AddressObject>();
			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList lastNameList = element.getElementsByTagName("formatedFull");
				if (lastNameList.item(0) == null) {
					continue;
				}
				Element lastNameElement = (Element) lastNameList.item(0);
				NodeList nameList = lastNameElement.getChildNodes();
				String name = ((Node) nameList.item(0)).getNodeValue();
				// System.out.println("name: " + name);

				NodeList lastDistanceList = element.getElementsByTagName("distance");
				Element lastDistanceElement = (Element) lastDistanceList.item(0);
				NodeList distanceList = lastDistanceElement.getChildNodes();
				String distance = ((Node) distanceList.item(0)).getNodeValue();
				// System.out.println("distance: " + distance);

				try {
					NodeList streetList = element.getElementsByTagName("streetName");
					Element streetElement = (Element) streetList.item(0);
					NodeList streetNode = streetElement.getChildNodes();
					street = ((Node) streetNode.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				try {
					NodeList houseNumberList = element.getElementsByTagName("houseNumber");
					Element houseNumberElement = (Element) houseNumberList.item(0);
					NodeList houseNumberNode = houseNumberElement.getChildNodes();
					houseNumber = ((Node) houseNumberNode.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				try {
					NodeList zipCodeNode = element.getElementsByTagName("zipCode");
					Element zipCodeElement = (Element) zipCodeNode.item(0);
					NodeList zipCOdeLIst = zipCodeElement.getChildNodes();
					zipCode = ((Node) zipCOdeLIst.item(0)).getNodeValue();
				} catch (Exception e) {
				}
				try {
					NodeList cityCodeNode = element.getElementsByTagName("city");
					Element cityCodeElement = (Element) cityCodeNode.item(0);
					NodeList cityCOdeLIst = cityCodeElement.getChildNodes();
					city = ((Node) cityCOdeLIst.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				// String response = new String(city.getBytes("ISO-8859-1"), "UTF-8");
				// System.out.println(city+" "+response);
				try {
					NodeList stateCodeNode = element.getElementsByTagName("state");
					Element stateCodeElement = (Element) stateCodeNode.item(0);
					NodeList stateCOdeLIst = stateCodeElement.getChildNodes();
					state = ((Node) stateCOdeLIst.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				// System.out.println("isIn: " + isIn);
				addressObject = new AddressObject(name, Double.parseDouble(distance), street, houseNumber, zipCode,
						city, state);

				break;
			}
			return addressObject;
		} catch (Exception e) {
			return null;
		}
	}

	public String readAddresesInfoCol(String lat, String longitude) {
		try {
			URL url = new URL(gisgraphyURL + "/street/search?format=XML&lat=" + lat + "&lng=" + longitude
					+ "&radius=300&streettype=&from=1&to=20&distance=true");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			String entrada;
			String cadena = "";

			while ((entrada = br.readLine()) != null) {
				cadena = cadena + entrada;
			}
			// System.out.println(cadena);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource archivo = new InputSource();
			archivo.setCharacterStream(new StringReader(cadena));

			Document documento = db.parse(archivo);
			documento.getDocumentElement().normalize();
			NodeList nodes = documento.getElementsByTagName("result");
			Vector<AddressObject> addressResults = new Vector<AddressObject>();
			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList lastNameList = element.getElementsByTagName("name");
				if (lastNameList.item(0) == null) {
					continue;
				}
				Element lastNameElement = (Element) lastNameList.item(0);
				NodeList nameList = lastNameElement.getChildNodes();
				String name = ((Node) nameList.item(0)).getNodeValue();
				// System.out.println("name: " + name);

				NodeList lastDistanceList = element.getElementsByTagName("distance");
				Element lastDistanceElement = (Element) lastDistanceList.item(0);
				NodeList distanceList = lastDistanceElement.getChildNodes();
				String distance = ((Node) distanceList.item(0)).getNodeValue();
				// System.out.println("distance: " + distance);

				NodeList lastIsInList = element.getElementsByTagName("isIn");
				Element lastIsInElement = (Element) lastIsInList.item(0);
				NodeList isInList = lastIsInElement.getChildNodes();
				String isIn = ((Node) isInList.item(0)).getNodeValue();
				// System.out.println(name+"isIn: " + isIn);
				AddressObject addressObject = new AddressObject(name, Double.parseDouble(distance), isIn, "", "", "",
						"");
				addressResults.add(addressObject);

			}
			return analyzeValidAddress(addressResults);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public String analyzeValidAddress(Vector<AddressObject> addresses) {
		try {
			Vector<String> validAddress = new Vector<String>();
			String name = "";
			String validAddressString = "";
			for (int i = 0; i < addresses.size(); i++) {
				if (validAddress.size() == 4) {
					break;
				}
				AddressObject addressObject = (AddressObject) addresses.elementAt(i);
				if (i == 0) {
					validAddress.add(addressObject.getName());
					name = addressObject.getName();
					validAddressString += name + "\n";
				} else {
					// if(name.compareTo(addressObject.name)!=0 &&
					// ((AddressObject)validAddress.elementAt(0)).name.compareTo(addressObject.name)!=0)
					if (validAddress.contains(addressObject.getName()) == false) {
						validAddress.add(addressObject.getName());
						name = addressObject.getName();
						if (validAddress.size() != 4) {
							validAddressString += addressObject.getName() + "\n";
						} else {
							validAddressString += addressObject.getName();
						}
					}
				}

			}

			return validAddressString;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	public AddressObject readCoordinates(String address) {

		AddressObject addressObject = null;
		String houseNumber = "";
		String zipCode = "";
		String street = "";
		String city = "";
		String state = "";
		try {

			URL url = new URL(gisgraphyURL + "/geocoding/geocode?address=" + address + "&countrycode=MX&format=XML");
			// new
			// URL(gisgraphyURL+"/street/search?format=XML&lat="+lat+"&lng="+longitude+"&radius=300&streettype=&from=1&to=2&distance=true");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			String entrada;
			String cadena = "";

			while ((entrada = br.readLine()) != null) {
				cadena = cadena + entrada;
			}
			// System.out.println(cadena);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource archivo = new InputSource();
			archivo.setCharacterStream(new StringReader(cadena));

			Document documento = db.parse(archivo);
			documento.getDocumentElement().normalize();
			NodeList nodes = documento.getElementsByTagName("result");
			//Vector addressResults = new Vector();
			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList lastNameList = element.getElementsByTagName("lat");
				if (lastNameList.item(0) == null) {
					continue;
				}
				Element lastNameElement = (Element) lastNameList.item(0);
				NodeList nameList = lastNameElement.getChildNodes();
				String latitude = ((Node) nameList.item(0)).getNodeValue();
				// System.out.println("name: " + name);

				NodeList lngList = element.getElementsByTagName("lng");
				Element lngElement = (Element) lngList.item(0);
				NodeList lngeList = lngElement.getChildNodes();
				String longitude = ((Node) lngeList.item(0)).getNodeValue();
				// System.out.println("distance: " + distance);

				try {
					NodeList streetList = element.getElementsByTagName("streetName");
					Element streetElement = (Element) streetList.item(0);
					NodeList streetNode = streetElement.getChildNodes();
					street = ((Node) streetNode.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				try {
					NodeList houseNumberList = element.getElementsByTagName("houseNumber");
					Element houseNumberElement = (Element) houseNumberList.item(0);
					NodeList houseNumberNode = houseNumberElement.getChildNodes();
					houseNumber = ((Node) houseNumberNode.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				try {
					NodeList zipCodeNode = element.getElementsByTagName("zipCode");
					Element zipCodeElement = (Element) zipCodeNode.item(0);
					NodeList zipCOdeLIst = zipCodeElement.getChildNodes();
					zipCode = ((Node) zipCOdeLIst.item(0)).getNodeValue();
				} catch (Exception e) {
				}
				try {
					NodeList cityCodeNode = element.getElementsByTagName("city");
					Element cityCodeElement = (Element) cityCodeNode.item(0);
					NodeList cityCOdeLIst = cityCodeElement.getChildNodes();
					city = ((Node) cityCOdeLIst.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				// String response = new String(city.getBytes("ISO-8859-1"), "UTF-8");
				// System.out.println(city+" "+response);
				try {
					NodeList stateCodeNode = element.getElementsByTagName("state");
					Element stateCodeElement = (Element) stateCodeNode.item(0);
					NodeList stateCOdeLIst = stateCodeElement.getChildNodes();
					state = ((Node) stateCOdeLIst.item(0)).getNodeValue();
				} catch (Exception e) {

				}
				// System.out.println("isIn: " + isIn);
				addressObject = new AddressObject(latitude, longitude, street, houseNumber, zipCode, city, state);
				System.out.println("latitude: " + addressObject.getLatitude());
				System.out.println("longitude: " + addressObject.getLongitude());
				System.out.println("direccion: " + addressObject.getStreet() + " " + houseNumber);
				System.out.println("zipcode: " + addressObject.getZipCode());
				System.out.println("city: " + addressObject.getCity());
				System.out.println("state: " + addressObject.getState());
				break;
			}
			return addressObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
