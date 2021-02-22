package com.ocd.model;

import java.util.HashMap;
import java.util.Map;

public class OnuSpecificData {
    public Map<String, String> attributes = new HashMap<>();
    public String portId;

    public OnuSpecificData () {


    }

    private String extractAttributeFromOnuData(String onuData, String attributeName) {
        onuData = onuData.substring(onuData.indexOf(attributeName) + attributeName.length());
        onuData = onuData.substring(onuData.indexOf("\"") + 1);
        String value = onuData.substring(0, onuData.indexOf("\""));
        return value;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }
}
