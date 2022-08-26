package com.objdetector;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;


public class VoiceCommand extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private static final int MY_DATA_CHECK_CODE = 1234;
    private ImageButton btnSpeak;
    TextToSpeech  tts;
    int time=0;

    final int RequestCameraPermission = 1001;
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_command);
        ActivityCompat.requestPermissions(VoiceCommand.this,new String[]{Manifest.permission.CAMERA},
                RequestCameraPermission);

        btnSpeak = findViewById(R.id.btnSpeak);

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        btnSpeak.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                tts.speak(" ", TextToSpeech.QUEUE_FLUSH,null);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");


                try {
                    startActivityForResult(intent, RESULT_SPEECH);


                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }


        });

    }


    @Override
    protected void onStop() {
        tts.stop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onInit(int i)
    {
        if(i==1){
            return;
        }

        tts.setSpeechRate(0.9f);
        tts.speak(" Say one for Object detection   ,   two for OCR   ,   three for Currency Detector.",
                TextToSpeech.QUEUE_ADD,  // Drop all pending entries in the playback queue.
                null);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if(text.get(0).equals("two")||text.get(0).equals("2")||text.get(0).equals("do")||text.get(0).equals("to")||text.get(0).equals("too"))
                    {
                        // Intent intent = new Intent(this,  MainActivity.class);
                        Intent intent = new Intent(this, com.objdetector.MainActivity.class);
                        startActivity(intent);


                    }
                    else  if(text.get(0).equals("three")||text.get(0).equals("3") ||text.get(0).equals("tre")|| text.get(0).equals("tree"))
                    {
                        // Intent intent = new Intent(this,  MainActivity.class);
                        Intent intent = new Intent(this, com.objdetector.ClassifierActivity.class);
                        startActivity(intent);


                    }
                    else if(text.get(0).equals("one")||text.get(0).equals("on")||text.get(0).equals("1")||text.get(0).equals("what")||text.get(0).equals("wan")){
                        Intent intent = new Intent(this,  ModelInstaler.class);
                        startActivity(intent);
                    }
                    else
                    {
                        tts.setSpeechRate(0.9f);
                        tts.speak(" invalid input try again .",
                                TextToSpeech.QUEUE_ADD,  // Drop all pending entries in the playback queue.
                                null);


                    }
                }
                break;


            case MY_DATA_CHECK_CODE:

                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
                {

                    tts = new TextToSpeech(this, this::onInit);

                }
                else
                {
                    // missing data, install it
                    Intent installIntent = new Intent();
                    installIntent.setAction(
                            TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installIntent);
                    finish();
                }
                break;
        }
    }
}