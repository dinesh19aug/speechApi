package com.dinesh.speech.processor;


import com.fasterxml.jackson.databind.ObjectMapper;
import flexjson.JSONSerializer;
import org.apache.camel.Exchange;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.InputRepresentation;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.Map;

/**
 * Created by dinesh on 10/25/17.
 */
public class MessageProcessor implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        System.out.println( exchange.getIn().getAttachments());
        MediaType mediaType =exchange.getIn().getHeader(Exchange.CONTENT_TYPE, MediaType.class);
        InputRepresentation representation =new InputRepresentation(exchange.getIn().getBody(InputStream.class), mediaType);

        try {
            List<FileItem> items =
                    new RestletFileUpload(
                            new DiskFileItemFactory()).parseRepresentation(representation);

            for (FileItem item : items) {

                if (!item.isFormField()) {
                    InputStream inputStream = item.getInputStream();
                    Path destination = Paths.get("/home/dinesh/Desktop/MyFile.wav");
                    Files.copy(inputStream, destination,
                            StandardCopyOption.REPLACE_EXISTING);
                }else{
                    InputStream inputStream = item.getInputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);
                    exchange.setProperty("jsonReuest", jsonMap);

                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }


    }
}
