package com.example.guardian;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FakeCallActivity extends AppCompatActivity {

    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);

        // Play default ringtone
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        if (ringtone != null) {
            ringtone.play();
        }

        Button btnAnswer = findViewById(R.id.btnAnswer);
        Button btnDecline = findViewById(R.id.btnDecline);

        // Clicking either button stops the "call"
        btnAnswer.setOnClickListener(v -> finishCall());
        btnDecline.setOnClickListener(v -> finishCall());
    }

    private void finishCall() {
        if(ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        if(ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        super.onDestroy();
    }
}