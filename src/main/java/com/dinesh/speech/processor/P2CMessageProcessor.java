package com.dinesh.speech.processor;

import com.dinesh.speech.response.JsonResponse;
import org.apache.camel.Exchange;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * Created by dinesh on 10/29/17.
 */
public class P2CMessageProcessor implements org.apache.camel.Processor {

    Random random;
    char[] symbols;
    AudioRespBuilder audioRespBuilder = new AudioRespBuilder();
    char[] buf;
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("P2C message processor called");
        JsonResponse response = new JsonResponse();
        String txnId = getTxnId(18);
        response.setMessage("The money has been transferred. Your txnId is " + txnId);
        audioRespBuilder.createAudio("The money has been transferred. Your txnId is " + txnId);
        exchange.getIn().setBody(response, JsonResponse.class);
    }



    public String getTxnId(int length) {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,length);

    }
}
