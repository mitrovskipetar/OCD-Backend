package com.ocd.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.context.annotation.Bean;

@JsonInclude
public class ShortItGponOnu {


    private String portId;
    private String adminState;
    private String description;
    private String interfaceNumber;
    private String nodeName;
    private String oltPort;



    public ShortItGponOnu(String onuAsString) {
        this.portId = extractAttributeFromOnuString(onuAsString, "portId");
        this.adminState = extractAttributeFromOnuString(onuAsString, "adminState");
        this.description = extractAttributeFromOnuString(onuAsString, "description");
        this.interfaceNumber = extractAttributeFromOnuString(onuAsString, "interface");
        this.nodeName = extractAttributeFromOnuString(onuAsString, "nodeName");
        this.oltPort = extractAttributeFromOnuString(onuAsString, "oltPort");
    }

    private String extractAttributeFromOnuString(String onu, String attributeName) {
        onu = onu.substring(onu.indexOf(attributeName) + attributeName.length());
        onu = onu.substring(onu.indexOf("\"") + 1);
        String value = onu.substring(0, onu.indexOf("\""));
        return value;
    }

//  GETTERS & SETTERS:

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInterfaceNumber(String interfaceNumber) {
        this.interfaceNumber = interfaceNumber;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setOltPort(String oltPort) {
        this.oltPort = oltPort;
    }

    public String getPortId() { return portId; }

    public String getAdminState() {
        return adminState;
    }

    public String getDescription() {
        return description;
    }

    public String getInterfaceNumber() {
        return interfaceNumber;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getOltPort() {
        return oltPort;
    }
}
