package com.ocd.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ocd.model.OnuSpecificData;
import com.ocd.model.ShortItGponOnu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import com.ocd.service.OcdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ComponentScan(basePackages= {"com.ocd.com.ocd.controller"})
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OcdController {

    @Autowired
    private OcdService ocdService;

    @GetMapping("/api/getOnus")
    public List<ShortItGponOnu> getFoos(@RequestParam String attrName,
                                        @RequestParam String attrValue,
                                        @RequestParam(required = false) String secAttrName,
                                        @RequestParam(required = false) String secAttrValue,
                                        @RequestParam(required = false) String thrAttrName,
                                        @RequestParam(required = false) String thrAttrValue) throws IOException {
        List<ShortItGponOnu> response = new ArrayList<>();
        if (thrAttrName != null) {
//            response = ocdService.getOnus(attrName, attrValue, secAttrName, secAttrValue, thrAttrName, thrAttrValue);
        } else if (secAttrName != null) {
//            ocdService.getOnus(attrName, attrValue, secAttrName, secAttrValue);
        } else if (attrName != null) {
            response = ocdService.getOnus(attrName, attrValue);
        }
        return response;
    }

    @GetMapping("/api/getOnuSpecificData")
    public OnuSpecificData getOnuSpecificData(@RequestParam String portId) throws IOException {
        return ocdService.getOnuSpecificData(portId);
    }

    @GetMapping("/api/getOnuConfigFiles")
    public List<String> getOnuConfigFiles(@RequestParam String portId) throws IOException {
        return ocdService.getOnuConfigFiles(portId);
    }

    @PostMapping("/api/insertOnuSpecificData")
    public boolean insertOnuSpecificData(@RequestBody OnuSpecificData onuSpecificData) throws IOException {
        return ocdService.insertOnuSpecificData(onuSpecificData);
    }

//    public OnuSpecificData getOnuSpecificData(@RequestParam String )

}
