package com.dinesh.speech.processor;

import org.apache.camel.Exchange;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dinesh on 10/27/17.
 */
public class ApiProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String voiceMessage= (String) exchange.getProperty("strMessage");
        String api=null;
        if(voiceMessage.toLowerCase().contains("transfer")){
            api ="P2C";
        }
        if(voiceMessage.toLowerCase().contains("ATM") || voiceMessage.toLowerCase().contains("location")){
            api="LOCATIONS";
        }
        if(voiceMessage.toLowerCase().contains("balance")){
            api = "AVS";
        }
        System.out.println("Calling API: " +  api);
        exchange.setProperty("API", api);
    }
}
