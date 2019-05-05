package com.dinesh.speech.processor;

import com.dinesh.speech.Constants;

import java.io.*;
import java.util.Arrays;

/**
 * Created by dinesh on 10/31/17.
 */
public class AudioRespBuilder {

    public void createAudio(String text) throws IOException{

        ProcessBuilder pb = new ProcessBuilder(
                "curl", this.createUrl(text) ,
                "-H", "Referer: http://translate.google.com/", "-H", "User-Agent: stagefright/1.2 (Linux;Android 5.0) > google_tts.mp3" );

        Runtime runtime = Runtime.getRuntime();

        Process p = pb.start();
        InputStream is = p.getInputStream();

        FileOutputStream outputStream = new FileOutputStream(Constants.basePath + Constants.outputFile);

        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] bytes = new byte[100];
        int numberByteReaded;
        while ((numberByteReaded = bis.read(bytes, 0, 100)) != -1) {

            outputStream.write(bytes, 0, numberByteReaded);
            Arrays.fill(bytes, (byte) 0);

        }

        outputStream.flush();
        outputStream.close();
    }

    private String createUrl(String text) {

        String convertedText = text.replace(" ","%20");
        StringBuilder stringBuilder = new StringBuilder("https://translate.google.com/translate_tts?ie=UTF-8&q=");
        stringBuilder.append(convertedText);
        stringBuilder.append("&tl=en&client=tw-ob");
        return stringBuilder.toString();
    }
    public static void main(String arg[]) throws IOException {
    AudioRespBuilder a = new AudioRespBuilder();
    a.createAudio("hello");

}


}
