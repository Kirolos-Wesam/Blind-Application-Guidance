package com.objdetector;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.speech.RecognizerIntent;

import java.util.Locale;


public class StartActivity extends AppCompatActivity {

    float x1 , x2 , y1 , y2 ;
    TextToSpeech tts;

    @Override
    protected void onStop() {
        tts.stop();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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

        tts = new TextToSpeech(this, this::Welcome);

    }

    public boolean onTouchEvent(MotionEvent touchEvent){

        switch(touchEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                //swap left for voice command
                if(x1 < x2){
                    Intent i = new Intent(StartActivity.this, TalkBack.class);
                    startActivity(i);
                    //swap right for talk back
                }else if(x1 > x2){
                    Intent i = new Intent(StartActivity.this, VoiceCommand.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    public void Welcome(int i)
    {

        tts.setSpeechRate(0.8f);
        tts.speak(" Welcome to Bag app , Swap left for voice command , Swap Right for TalkBack",
                TextToSpeech.QUEUE_ADD,
                null);

    }

}



