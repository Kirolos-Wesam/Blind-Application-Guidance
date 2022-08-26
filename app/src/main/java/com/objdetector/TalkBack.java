package com.objdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.speech.tts.TextToSpeech;


import java.util.Locale;


public class TalkBack extends AppCompatActivity {
    int number_of_clicks = 0;
    boolean thread_started = false;
    final int DELAY_BETWEEN_CLICKS_IN_MILLISECONDS = 250;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_back);
        Button buttonCurr = findViewById(R.id.ButtonCurr);
        Button buttonObjD = findViewById(R.id.buttonObjD);
        Button buttonOCR = findViewById(R.id.buttonOCR);
        TextToSpeech.OnInitListener listener =
                new TextToSpeech.OnInitListener() {
                    @Override public void onInit(final int status){
                        if (status == TextToSpeech.SUCCESS) {
                            Log.d("TTS", "Text to speech engine started successfully.");
                            tts.setLanguage(Locale.US);
                        } else {
                            Log.d("TTS", "Error starting the text to speech engine.");
                        }
                    }
                };
        tts = new TextToSpeech(this.getApplicationContext(), listener);

        buttonObjD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++number_of_clicks;
                if(!thread_started){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            thread_started = true;
                            try {
                                Thread.sleep(DELAY_BETWEEN_CLICKS_IN_MILLISECONDS);
                                if(number_of_clicks == 1){
                                    tts.setSpeechRate(0.9f);
                                    tts.speak("Object Detection",
                                            TextToSpeech.QUEUE_FLUSH,
                                            null);


                                } else if(number_of_clicks == 2){
                                    Intent intent = new Intent(TalkBack.this, ModelInstaler.class);
                                    startActivity(intent);
                                }
                                number_of_clicks = 0;
                                thread_started = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        buttonOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++number_of_clicks;
                if(!thread_started){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            thread_started = true;
                            try {
                                Thread.sleep(DELAY_BETWEEN_CLICKS_IN_MILLISECONDS);
                                if(number_of_clicks == 1){
                                    tts.setSpeechRate(0.7f);
                                    tts.speak("OCR",
                                            TextToSpeech.QUEUE_FLUSH,
                                            null);

                                } else if(number_of_clicks == 2){
                                    Intent intent = new Intent(TalkBack.this, com.objdetector.MainActivity.class);
                                     startActivity(intent);
                                }
                                number_of_clicks = 0;
                                thread_started = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        buttonCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++number_of_clicks;
                if(!thread_started){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            thread_started = true;
                            try {
                                Thread.sleep(DELAY_BETWEEN_CLICKS_IN_MILLISECONDS);
                                if(number_of_clicks == 1){
                                    tts.setSpeechRate(0.9f);
                                    tts.speak("Currency Detector",
                                            TextToSpeech.QUEUE_FLUSH,
                                            null);
                                } else if(number_of_clicks == 2){
                                    Intent intent = new Intent(TalkBack.this, com.objdetector.ClassifierActivity.class);
                                     startActivity(intent);
                                }
                                number_of_clicks = 0;
                                thread_started = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });




    }

}