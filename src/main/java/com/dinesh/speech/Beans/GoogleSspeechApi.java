package com.dinesh.speech.Beans;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by dinesh on 10/27/17.
 */
public class GoogleSspeechApi {

    public String processSpeech(String filePath) throws Exception {
        System.out.println("Processing Speech now....");
       // System.out.println("File Path: " + filePath);
        return syncRecognizeFile("/home/dinesh/Desktop/MyFile.wav");
    }

    

    public static String syncRecognizeFile(String fileName) throws Exception{
        String transcript = null;
        try (SpeechClient speechClient = SpeechClient.create()){
            // Reads the audio file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            // Builds the sync recognize request
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("en-US")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
                transcript = alternative.getTranscript();
            }

        }

        return transcript;
    }
}
