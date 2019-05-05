package com.dinesh.speech.processor;

import com.dinesh.speech.response.JsonResponse;
import org.apache.camel.Exchange;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by dinesh on 10/29/17.
 */
public class LocMessageProcessor implements org.apache.camel.Processor {
    AudioRespBuilder audioRespBuilder = new AudioRespBuilder();
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("LOC message processor called");
        JsonResponse response = new JsonResponse();
        Integer distance = getDistnace();
        DecimalFormat df = new DecimalFormat("#");
        StringBuilder builder = new StringBuilder("There are " + String.valueOf(df.format(distance)) + " ATM locations" +
                " " +
                "nearby");
        builder.append(". The closest one is " + (new DecimalFormat("#.00")).format(distance+ 0.25) + " miles away");
        response.setMessage(builder.toString());
        audioRespBuilder.createAudio(builder.toString());
        exchange.getIn().setBody(response, JsonResponse.class);
    }

    private Integer getDistnace() {
        Random distance = new Random();
        Integer dblAmt = distance.nextInt(6) + 0;

        return dblAmt;

    }
}
