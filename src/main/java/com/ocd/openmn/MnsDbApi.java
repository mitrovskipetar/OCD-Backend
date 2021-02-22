package com.ocd.openmn;

import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MnsDbApi {
	
	
	/**
	 * @param nodeId
	 * @return number of ONUs on the given node
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static int countOnusInANode(Integer nodeId) throws IOException, ParserConfigurationException, SAXException {
		String[] attributes = {"ProvisionedItGponOnu", "nodeId", nodeId.toString() };
		return SOAPHelper.countObjectsInDb(attributes);
	}

	public static String getAllOnus(){

		return "";
	}
	
	public static String getNodeFromDb(String nodeName) throws Exception {
		String table = "MnsNode";
		String attrName = "commonName";
		String value = nodeName;
		String[] attr = {table, attrName, value};
		return SOAPHelper.getObjectAsString(attr);
	}

	
	//Get container from Database
	public static String getContainerFromDb (String containerName) throws Exception {
		String table = "MnsContainer";
		String attrName = "containerName";
		String value = containerName;
		String[] attr = {table, attrName, value};
		return SOAPHelper.getObjectAsString(attr);
	}
	//Get shelf type, return Pizzabox for Pizzabox, return Shelf for any other shelf type
	public static Boolean isContainerShelfTypePizzabox (String containerName) throws Exception {
		String container = getContainerFromDb(containerName);
		String shelfType = getAttrFromStringObject("shelfType", container);
		if(shelfType.equals("MECVX"))
			return true;
		else return false;
	}
	
	//Check if there is a node attached to the container's slot
	//to do
	public static String getNodeId(String nodeName) throws Exception {
		String node = getNodeFromDb(nodeName);
		String nodeId = getAttrFromStringObject("nodeId", node);
		return nodeId;
	}
	
	public static String getNodeStatus(String nodeId) throws Exception{
		String table = "MnNodeStatus";
		String attribute = "nodeId";
		String value = nodeId;
		String[] attr = {table, attribute, value};
		return SOAPHelper.getObjectAsString(attr);
	}
	
	public static boolean getNodeSyncStatus(String nodeId) throws Exception{
		String nodeStatus = getNodeStatus(nodeId);
		String syncStatus = getAttrFromStringObject("nodeSyncStatus", nodeStatus);
		if(syncStatus.equalsIgnoreCase("0"))
			return false;
		else return true;
	}
//
//	public static String[] getAllNodes() {
//		List<String> nodes = new ArrayList<String>();
//		return [" asd", "asd"];
//	}
	
	public static String getAttrFromStringObject(String attrName, String object) {
		attrName = attrName.concat("=");
		String regex = "\\s\\w+=";
		Pattern pattern = Pattern.compile(regex);
		String str3 = object.substring((object.indexOf(attrName) + attrName.length()), object.length());
		Matcher m = pattern.matcher(str3);
		m.find();
		str3 = str3.substring(0, m.start());
		int slashes = StringUtils.countOccurrencesOf(str3, "/");
		if (slashes == 1) {
			if(str3.charAt(str3.length() - 1) == '/') {
			return str3.replace("/", "");
			}else {
			return str3.substring(0, str3.indexOf("/"));
			}
		} else if (slashes == 2) {
			return str3.substring(0, 3);
		} else if ((slashes == 3)) {
			if(str3.charAt(str3.length() - 1) == '/') {
			return str3.substring(0, 5);
			}else {
			return str3.substring(0, 3);
			}
		} else if (slashes == 5)

		{
			return str3.substring(0, 5);
		}
		return "Error: Attribute not found!";
	}
	
	public static boolean nodeExistsInMns(String nodeName) throws IOException, ParserConfigurationException, SAXException {
		String table = "MnsNode";
		String attrName = "commonName";
		String value = nodeName;
		String[] attr = {table, attrName, value};
		return SOAPHelper.objExistsInMns(attr);
	}

//	public static void getOnus(String attrName, String attrValue) {
//		SOAPHelper.getOnus(attrName, attrValue);
//	}
//
//	public static void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue) {
//		SOAPHelper.getOnus(attrName, attrValue, secAttrName, secAttrValue);
//	}
//
//	public static void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue, String thrAttrName, String thrAttrValue) {
//		SOAPHelper.getOnus(attrName, attrValue, secAttrName, secAttrValue, thrAttrName, thrAttrValue);
//	}
	public static boolean containerExistsInMns(String containerName) throws IOException, ParserConfigurationException, SAXException {
		String table = "MnsContainer";
		String attrName = "containerName";
		String value = containerName;
		String[] attr = {table, attrName, value};
		return SOAPHelper.objExistsInMns(attr);
	}
	
	public static String getPortFromDb(String nodeName, String portInterface) throws Exception {
		String node = getNodeFromDb(nodeName);
		String nodeId = getAttrFromStringObject("nodeId", node);
		String table = "PhysicalPort";
		String attrName = "portInterface";
		String attrValue = "0/1";
		String secAttrName = "nodeId";
		String secAttrValue = nodeId;
		String [] attr = {table, attrName, attrValue, secAttrName, secAttrValue};
		String request = XMLHelper.generateGetObjectAsStringRequestMul(attr);
		return SOAPHelper.sendSoapRequest(request);
	}
	
	/**
	 * @param nodeName 
	 * @return number of ONUs on the given node
	 * @throws Exception 
	 */
	public static int countOnusInANode(String nodeName) throws Exception {
		String[] attributes = {"MnsNode", "commonName", nodeName};
		String node = null;
		try {
			node = SOAPHelper.getObjectAsString(attributes);
		} catch (Exception e) {
			if(e.getMessage().equals("Object doesn't exist in Database!")) {
				throw new Exception("Node with given name doesn't exist in the database!");
			}
		}
		int nodeId = Integer.parseInt(getAttrFromStringObject("nodeId", node));
		return countOnusInANode(nodeId);
		
	}
}
