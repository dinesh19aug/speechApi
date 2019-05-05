package com.dinesh.speech;

import com.dinesh.speech.Beans.GoogleSspeechApi;

import com.dinesh.speech.processor.*;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;

import javax.ws.rs.core.MediaType;

public class MyRouteBuilder extends SpringRouteBuilder{

	@Override
	public void configure() throws Exception {
		restConfiguration().component("restlet").port(8081).bindingMode(RestBindingMode.off);
	    rest("/smartApi").consumes(MediaType.MULTIPART_FORM_DATA).produces(MediaType.APPLICATION_JSON)
				.post("/bye").to("direct:bye");


		from("direct:bye")
				.process(new MessageProcessor())
				.setProperty("strMessage", method(GoogleSspeechApi.class, "processSpeech"))
				.process(new ApiProcessor())
				.choice()
					.when(exchangeProperty("API").isEqualTo("P2C")).to("direct:P2C")
					.when(exchangeProperty("API").isEqualTo("LOCATIONS")).to("direct:LOCATIONS")
					.when(exchangeProperty("API").isEqualTo("AVS")).to("direct:AVS")
				;
		from("direct:P2C")
				.process(new P2CMessageProcessor())
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.marshal().json(JsonLibrary.Jackson);
		from("direct:LOCATIONS")
				.process(new LocMessageProcessor())
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.marshal().json(JsonLibrary.Jackson);
		from("direct:AVS")
				.process(new AvsMessageProcessor())
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.marshal().json(JsonLibrary.Jackson);

	}
}

