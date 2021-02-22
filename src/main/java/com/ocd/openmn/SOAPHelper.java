package com.ocd.openmn;

import com.ocd.model.ShortItGponOnu;
import jdk.nashorn.internal.parser.JSONParser;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

public class SOAPHelper {

	private final static String USERNAME = "sysadmin";
	private final static String PASSWORD = "sysadmin";
	private final static String SRV_ADDRESS = "172.17.100.137";

	/**
	 * @param attr - Array of string attributes in this order: , TABLE, ATTR,
	 *                   VALUE</strong>
	 *
	 *                   checks if an <strong>ATTR</strong> with the given
	 *                   <strong>VALUE</strong> exists in the given
	 *                   <strong>TABLE</strong> <br>
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static boolean objExistsInMns(String[] attr) throws IOException, ParserConfigurationException, SAXException {
		int count = countObjectsInDb(attr);
		if(count > 0) {
			return true;
		}else {
			return false;
		}
	}



//	public static List<String> getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue) {
//		XMLHelper.generateGetOnusRequest(attrName, attrValue, secAttrName, secAttrValue);
//	}
//
//	public static List<String> getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue, String thrAttrName, String thrAttrValue) {
//		XMLHelper.generateGetOnusRequest(attrName, attrValue, secAttrName, secAttrValue, thrAttrName, thrAttrValue);
//	}


	/**
	 * Used to get an object from DB.
	 * 
	 * @param attr - Array of string attributes in this order: ,
	 *                   <strong>TABLE, ATTR_NAME, ATTR_VALUE</strong>
	 * 
	 *                   <strong>TABLE</strong> - DB Table <strong>NAME</strong> -
	 *                   Name of the attribute in MNS <strong>OIDATTRNAME</strong> -
	 *                   The value of the attribute
	 * @throws Exception 
	 */
	public static String getObjectAsString(String[] attr) throws Exception {
		String xmlReq = XMLHelper.generateGetObjectAsStringRequest(attr);
		String response;
		try {
			response = sendSoapRequest(xmlReq);
		} catch (IOException e) {
			return e.getMessage();
		}
		return SOAPHelper.extractStringObjectFromResponse(response);
	}
	
	public static String getObjectAsStringMult(String[] attr) throws Exception {
		String xmlReq = XMLHelper.generateGetObjectAsStringRequestMul(attr);
		String response;
		try {
			response = sendSoapRequest(xmlReq);
		} catch (IOException e) {
			return e.getMessage();
		}
		return SOAPHelper.extractStringObjectFromResponse(response);
	}
	
	public static int countObjectsInDb(String[] attr) throws IOException, ParserConfigurationException, SAXException {
		String xml = XMLHelper.generateCountObjectsInDbRequest(attr);
		String xmlResponse = sendSoapRequest(xml);
		int count = extractIntValueFromXml("count", xmlResponse);
		return count;
	}


	
	public static String extractStringObjectFromResponse(String xmlResponse) throws Exception {
			String response = xmlResponse;
			response = response.substring(response.indexOf("object=") + 9, response.indexOf("]\"/>"));
			if(response == "") {
				throw new Exception("Object doesn't exist in Database!");
			}
		return response;
	}

	public static Object turnStringIntoObject(String objAsString) {
		return null;
	}

	public static int extractIntValueFromXml(String attrName, String xmlResponse)
			throws ParserConfigurationException, SAXException, IOException {
		String response = xmlResponse;
		response = response.substring(response.indexOf(attrName + "="), response.indexOf(attrName + "=") + 15);
		response = response.replaceAll("\\D", "");
		return Integer.parseInt(response);
	}

	public static String extractValueFromObjectString(String attrName) {

		return "";
	}

//	public static void getOnus(String attrName, String attrValue) {
//
//	}
//
//	public static void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue) {
//
//	}
//
//	public static void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue, String thrAttrName, String thrAttrValue) {
//
//	}

	public static String sendSoapRequest(String xml) throws IOException {
		String url = "http://" + SRV_ADDRESS + "/mns/openmns/OpenMnsWebService";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		String norm = USERNAME + ":" + PASSWORD;
		Encoder enc = Base64.getEncoder();
		con.setRequestProperty("Authorization", "Basic " + enc.encodeToString(norm.getBytes()));
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(xml);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}