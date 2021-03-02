package com.ocd.openmn;

import com.ocd.model.OnuSpecificData;

import java.util.ArrayList;
import java.util.List;

public class XMLHelper {

	public static String generateGetOnusRequest(String attrName, String attrValue) {
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:getOnusRequest>" + "<filter attr='" + attrName + "' value='" + attrValue.trim() + "' />"
				+ "</urn:getOnusRequest>";
		String xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}

	public static String generateGetOnuSpecificDataRequest(String portId) {
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:getOnuSpecificDataRequest>" + "<filter portId='" + portId + "'/>"
				+ "</urn:getOnuSpecificDataRequest>";
		String xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}

	public static String generateGetOnuConfigFilesRequest(String portId) {
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:osdGetOnuConfigFilesRequest>" + "<filter portId='" + portId + "'/>"
				+ "</urn:osdGetOnuConfigFilesRequest>";
		String xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}

	public static String generateInsertOnuSpecificDataRequest(OnuSpecificData onuSpecificData) {
		onuSpecificData.getParams().size();
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReqBeg = "<urn:insertOnuSpecificDataRequest>" + "<onuSpec id=\"" + onuSpecificData.getId()
				+ "\" portId=\"" + onuSpecificData.getPortId() + "\">";
		String soapReqEnd = "</onuSpec> </urn:insertOnuSpecificDataRequest>";
		String soapReqBody = "";
		String xmlDoc = "";
		xmlDoc = xmlDoc.concat(beforeSoapReq + soapReqBeg);
		for(int i = 0; i < onuSpecificData.getParams().size(); i++) {
			xmlDoc = xmlDoc.concat("<onuSpecParams portId=\" " + onuSpecificData.getPortId() + "\" attributeName=\"" +
					onuSpecificData.getParams().get(i).getParam() +
					"\" attributeValue=\"" + onuSpecificData.getParams().get(i).getValue() + "\" />");
		}
		xmlDoc = xmlDoc.concat(soapReqEnd + afterSoapReq);
		return xmlDoc;
	}

//	public static String generateGetOnusRequest(String attrName, String attrValue, String secAttrName, String secAttrValue) {
//		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
//				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
//		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
//		String soapReq = "<urn:testRunnerGetQueryRequest>" + "         <filter attr='" + attrName + "' value='" + attrValue + "'/>"
//				+ "<filter secAttrName='" + attrName + "' value='" + attrValue + "'/>" +
//				+ "      </urn:testRunnerGetQueryRequest>";
//		String xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
//		return xmlDoc;
//	}

	public static String extractValueFromStringObject(String object, String attrName) {
		if(object.contains(attrName)) {
			object = object.substring(object.indexOf(attrName) + attrName.length());
			object = object.substring(object.indexOf("\"") + 1);
			String value = object.substring(0, object.indexOf("\""));
			return value;
		} else {
			return null;
		}
		}


	public static List<String> extractObjectsFromStringList(String response, String objectName) {
		List<String> objList = new ArrayList<>();
		response = response.substring(response.indexOf(objectName) + objectName.length());
		while(response.contains(objectName)) {
			String strOnu = response.substring(0, response.indexOf(objectName));
			response = response.substring(response.indexOf(objectName) + objectName.length());
			if(strOnu != null) {
				objList.add(strOnu);
			}
		}
		String lastOnu = response;
		objList.add(lastOnu);
		return objList;
	}

	public static String extractAttributeFromStringObject(String obj, String attributeName) {
		obj = obj.substring(obj.indexOf(attributeName) + attributeName.length());
		obj = obj.substring(obj.indexOf("\"") + 1);
		String value = obj.substring(0, obj.indexOf("\""));
		return value;
	}

	/**
	 * @param attributes - Array of string attributes in this order: ,
	 *                   TABLE, ATTR, VALUE</strong>
	 * 
	 *                   checks if an <strong>ATTR</strong> with the
	 *                   given <strong>VALUE</strong> exists in the given
	 *                   <strong>TABLE</strong> <br>
	 */
	public static String generateExistsRequest(String[] attributes) {
		String table = attributes[0];
		String attr = attributes[1];
		String value = attributes[2];
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerGetQueryRequest>" + "         <filter table='" + table + "' attr='" + attr
				+ "' value='" + value + "'" + "/>" + "      </urn:testRunnerGetQueryRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}
	
	public static String generateGetObjectAsStringRequest(String[] attributes) {
		String table = attributes[0];
		String attrName = attributes[1];
		String attrValue = attributes[2];
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerGetObjectAsStringRequest>" + "         <filter table='" + table + "' attr='" + attrName
				+ "' value='" + attrValue + "'" + "/>" + "      </urn:testRunnerGetObjectAsStringRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}
	
	public static String generateGetObjectAsStringRequestMul(String[] attributes) {
		String table = attributes[0];
		String attrName = attributes[1];
		String attrValue = attributes[2];
		String secAttrName = attributes[3];
		String secAttrValue = attributes[4];
		
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerGetObjectAsStringMulRequest>" + "         <filter table='" + table + "' attr='" + attrName
				+ "' value='" + attrValue + "' secAttr='" + secAttrName	+ "' secValue='" + secAttrValue + "'" + "/>" + "      </urn:testRunnerGetObjectAsStringMulRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}
	
	public static String generateCountObjectsInDbRequest(String[] attributes) {
		String table = attributes[0];
		String attrName = attributes[1];
		String attrValue = attributes[2];
		
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerCountObjectsInDbRequest>" + "         <filter table='" + table + "' attr='" + attrName
				+ "' value='" + attrValue + "'" + "/>" + "      </urn:testRunnerCountObjectsInDbRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}
	
	public static String generateCountObjectsInDbRequestMul(String[] attributes) {
		String table = attributes[0];
		String attrName = attributes[1];
		String attrValue = attributes[2];
		String secAttrName = attributes[3];
		String secAttrValue = attributes[4];
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerCountObjectsInDbRequest>" + "         <filter table='" + table + "' attr='" + attrName
				+ "' value='" + attrValue + "' secAttr='" + secAttrName	+ "' secValue='" + secAttrValue + "'" + "/>" + "      </urn:testRunnerCountObjectsInDbRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}
	//TR
	public static String getAttributeFromObject(String attr) {
		String str2 = "nodeId=/ nodeTypeId=ITLUMEV1/ nodeTypeIdVirt=/ snmpProfileId=SNMP_V2C/ description=1223/ version=/ versionPrint=/ commonName=qwe/ mnNodeId=mnsskopje137/ connectivityProfileId=/ ntpProfileId=/ syslogGroupId=/ hostname=qwe/ hostname1=/ hostname2=/ productCategory=/ nodeMode=/ snmpEntityId=/ mnUrl=/ nodeIp=/ mnRelease=/ dbRelease=/ dataRelease=/ voipHostname=/ snmpProxyAgentNodeId=/ snmpAgentMibType=/ productId=/ hiddenNode=/ dbSyncMode=/ uploadMode=/ autoConfigDisabled=/ duplicated=/ productName=/ docRelease=/ snSwUpdate=/ sqlRecording=/ dialupAccess=/ neRelease2=/ manualUpgrade=/ acsConfigurationMode=/ geoHostname=/ tags=/ internal=/ geoZoneId=/ timeZoneId=/ imsNodeType=/ imsSipUri=/ mnReleasePrint=/ dbReleasePrint=/ dataReleasePrint=/ geoRedundant=/ dbSyncModeVirtual=/ state=/ alarmSeverity=/ alarms=/ oldTags=/ isEditor=/ forcedDelete=/ diagModuleId=/ entityIndex=/ ossMacAddress=/ ossSwVersion=/ autoConfigEnabled=/ subtypes=/ subtypesVirtual=/ ";
		String strWithEq = "/\\b\\w+=/";
		String[] nodeId = str2.split("/");
		return "";
	}

	public static String generateDeleteNodeRequest(String oid) {
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerDeleteNodeRequest>" + "         <filter oid='" + oid + "'/>"
				+ "      </urn:testRunnerDeleteNodeRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}

	public static String generateDeleteContainerRequest(String oid) {
		String xmlDoc = "";
		String beforeSoapReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:iskratel-si:itopenmns-1-0\">\r\n"
				+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>";
		String afterSoapReq = "</soapenv:Body>\r\n" + "</soapenv:Envelope>";
		String soapReq = "<urn:testRunnerDeleteContainerRequest>" + "         <filter oid='" + oid + "'/>"
				+ "      </urn:testRunnerDeleteContainerRequest>";
		xmlDoc = beforeSoapReq + soapReq + afterSoapReq;
		return xmlDoc;
	}

	public static boolean getExistsFromXml(String xmlResponse) {
		if (xmlResponse.contains("exists")) {
			String ex = xmlResponse.substring(xmlResponse.indexOf("exists") + 8, xmlResponse.indexOf("exists") + 12);
			if (ex.contains("true")) {
				return true;
			} else if (ex.contains("false")) {
				return false;
			} else {
				throw new IllegalArgumentException("Value 'true' or 'false' not found in attribute 'exists'");
			}
		} else {
			throw new IllegalArgumentException("Attribute 'exists' not found in the response");
		}
	}
	
	public static int getOidFromXml(String xmlResponse) {
		String src = "";
		if (xmlResponse.contains("oid")) {
			src = xmlResponse.substring(xmlResponse.indexOf("oid") + 3, xmlResponse.indexOf("success") + 10);
			src = src.replaceAll("\\D", "");
		}
		return Integer.parseInt(src);
	}
	
	public static boolean getSuccessFromXml(String xmlResponse) {
		if (xmlResponse.contains("success")) {
			String ex = xmlResponse.substring(xmlResponse.indexOf("success") + 8, xmlResponse.indexOf("success") + 12);
			if (ex.contains("true")) {
				return true;
			} else if (ex.contains("false")) {
				return false;
			} else {
				throw new IllegalArgumentException("Value 'true' or 'false' not found in attribute 'success'");
			}
		} else {
			throw new IllegalArgumentException("Attribute 'success' not found in the response");
		}
	}
}