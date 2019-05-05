package com.dinesh.speech.processor;

import com.dinesh.speech.response.JsonResponse;
import org.apache.camel.Exchange;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by dinesh on 10/29/17.
 */
public class AvsMessageProcessor implements org.apache.camel.Processor {
    AudioRespBuilder audioRespBuilder = new AudioRespBuilder();
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("AVS message processor called");
        JsonResponse response = new JsonResponse();
        Double amount = getBalance();
        DecimalFormat df = new DecimalFormat("#.00");
        response.setMessage("Your account balance is  " + String.valueOf(df.format(amount)));
        audioRespBuilder.createAudio("Your account balance is  " + String.valueOf(df.format(amount)));
        exchange.getIn().setBody(response, JsonResponse.class);
    }

    private Double getBalance() {
        Random amount = new Random();
        Double dblAmt = amount.nextDouble();

        return 100 * dblAmt;

    }
}
