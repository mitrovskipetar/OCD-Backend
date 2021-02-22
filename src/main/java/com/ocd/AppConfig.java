package com.ocd;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig implements InitializingBean {

    public static String logDirectory;
    public static String testDirectory;
    public static String driverLocation;

//    @Value("${log.directory}")
//    public void setLogDirectory (String dir) {
//        logDirectory = dir;
//    }
//
//    @Value("${test.directory}")
//    public void setTestDirectory (String dir) {
//        testDirectory = dir;
//    }
//
//    @Value("${driver.location}")
//    public void setDriverLocation (String dir) {
//        driverLocation = dir;
//    }

    public void showVariables() {
        System.out.println("Reading external property files:");
        System.out.println("Log Directory: " + logDirectory);
        System.out.println("Test Directory: " + testDirectory);
        System.out.println("Driver Location: " + driverLocation);
    }

    public void afterPropertiesSet() throws Exception {
        showVariables();
    }
}
