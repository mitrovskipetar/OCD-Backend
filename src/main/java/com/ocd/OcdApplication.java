package com.ocd;

import com.ocd.openmn.SOAPHelper;
import com.ocd.openmn.XMLHelper;
import com.ocd.service.OcdService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
public class OcdApplication {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        SpringApplication.run(OcdApplication.class, args);
    }

}
