package com.ocd.service;

import com.ocd.model.OnuSpecificData;
import com.ocd.model.OnuSpecificParams;
import com.ocd.model.ShortItGponOnu;
import com.ocd.openmn.SOAPHelper;
import com.ocd.openmn.XMLHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OcdService {
    public List<ShortItGponOnu> getOnus(String attrName, String attrValue) throws IOException {
        String request = XMLHelper.generateGetOnusRequest(attrName, attrValue);
        String response = SOAPHelper.sendSoapRequest(request);
        List<String> onusAsString = XMLHelper.extractObjectsFromStringList(response, "shortItGponOnu");
        List<ShortItGponOnu> resultOnus = new ArrayList<>();
        for(String strOnu : onusAsString) {
            ShortItGponOnu onu = new ShortItGponOnu(strOnu);
            resultOnus.add(onu);
        }
        return resultOnus;
    }

    public List<String> getOnuConfigFiles(String portId) throws IOException {
        String request = XMLHelper.generateGetOnuConfigFilesRequest(portId);
        String response = SOAPHelper.sendSoapRequest(request);
        List<String> objects = XMLHelper.extractObjectsFromStringList(response, "configFile");
        List<String> result = new ArrayList<>();
        result.add("");
        for(String obj : objects) {
            result.add(XMLHelper.extractValueFromStringObject(obj, "fileName"));
        }
        return result;
    }

    public OnuSpecificData getOnuSpecificData(String portId) throws IOException {
        String request = XMLHelper.generateGetOnuSpecificDataRequest(portId);
        String response = SOAPHelper.sendSoapRequest(request);
        if(!response.contains("onuSpecificData")){
            return null;
        }

        List<String> onuSpecData = XMLHelper.extractObjectsFromStringList(response, "onuSpecificData");
        OnuSpecificData respData = new OnuSpecificData();
        respData.setId(XMLHelper.extractAttributeFromStringObject(onuSpecData.get(0), "id"));
        respData.setPortId(XMLHelper.extractAttributeFromStringObject(onuSpecData.get(0), "portId"));
        for(String str : onuSpecData) {
            OnuSpecificParams params = new OnuSpecificParams();
            params.setParam(XMLHelper.extractValueFromStringObject(str, "attributeName"));
            params.setValue(XMLHelper.extractValueFromStringObject(str, "attributeValue"));
            params.setPortId(XMLHelper.extractAttributeFromStringObject(onuSpecData.get(0), "portId"));
            respData.addParam(params);
        }
        return respData;
    }

    public boolean insertOnuSpecificData(OnuSpecificData onuSpecificData) throws IOException {
        String request = XMLHelper.generateInsertOnuSpecificDataRequest(onuSpecificData);
        String response = SOAPHelper.sendSoapRequest(request);


        return true;
    }

//    public void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue) {
//
//    }
//
//    public void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue, String thrAttrName, String thrAttrValue) {
//
//    }
}
