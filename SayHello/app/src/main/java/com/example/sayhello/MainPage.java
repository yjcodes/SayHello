package com.example.sayhello;

import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainPage extends Activity implements TextToSpeech.OnInitListener {
    private int result=0;
    private TextToSpeech tts;
    private Button btnSpeak, btnclear, btnexit;
    private EditText txtText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tts = new TextToSpeech(this, this);
        btnSpeak = (Button)findViewById(R.id.btnSpeak);
        btnclear = (Button)findViewById(R.id.btnclr);
        btnexit = (Button)findViewById(R.id.btnext);
        txtText = (EditText)findViewById(R.id.txtText);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                speakOut();
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtText.setText("");
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Missing data", Toast.LENGTH_LONG).show();
                btnSpeak.setEnabled(false);
            } else {
                btnSpeak.setEnabled(true);
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut() {
        String text = txtText.getText().toString();
        if(result!=tts.setLanguage(Locale.US))
        {
            Toast.makeText(getApplicationContext(), "Enter right Words! ", Toast.LENGTH_LONG).show();
        }else
        {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
