package com.ocd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OnuSpecificParams {
    String param = "";
    String value = "";
    @JsonIgnore
    String id = "";
    @JsonIgnore
    String portId = "";


    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }
}
