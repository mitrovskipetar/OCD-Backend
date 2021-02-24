package com.ocd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class OnuSpecificData {
    public List<OnuSpecificParams> params = new ArrayList<>();
    public String portId;



    public String id;

    public OnuSpecificData () {


    }

    private String extractAttributeFromOnuData(String onuData, String attributeName) {
        onuData = onuData.substring(onuData.indexOf(attributeName) + attributeName.length());
        onuData = onuData.substring(onuData.indexOf("\"") + 1);
        String value = onuData.substring(0, onuData.indexOf("\""));
        return value;
    }

    public void addParam (OnuSpecificParams param) {
        params.add(param);
    }

    public List<OnuSpecificParams> getParams() {
        return params;
    }

    public void setAttributes(List<OnuSpecificParams> params) {
        this.params = params;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
