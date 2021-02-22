package com.ocd.service;

import ch.qos.logback.core.db.DBHelper;
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

//    public void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue) {
//
//    }
//
//    public void getOnus(String attrName, String attrValue, String secAttrName, String secAttrValue, String thrAttrName, String thrAttrValue) {
//
//    }
}