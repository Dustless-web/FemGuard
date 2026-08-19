package com.example.guardian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_contacts) {
                selectedFragment = new ContactFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    public void checkAndSendSOS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendSOS();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.SEND_SMS
            }, 1);
        }
    }

    private void sendSOS() {
        CancellationTokenSource cts = new CancellationTokenSource();
        Toast.makeText(this, "Locating...", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = getSharedPreferences("MySafetyApp", MODE_PRIVATE);
        boolean playSiren = prefs.getBoolean("siren_enabled", false);
        boolean vibrate = prefs.getBoolean("vibrate_enabled", true);

        if (vibrate) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        }

        if (playSiren) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.police_siren);
            if(mediaPlayer != null) mediaPlayer.start();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        dispatchSMS(location);
                    } else {
                        Toast.makeText(this, "Location not found. Ensure GPS is on.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void dispatchSMS(Location location) {
        SharedPreferences prefs = getSharedPreferences("MySafetyApp", MODE_PRIVATE);
        String jsonString = prefs.getString("contacts_list", "[]");
        String customMsg = prefs.getString("sos_msg", "HELP! I need emergency assistance.");

        // ADDED USER PROFILE INFO
        String name = prefs.getString("user_name", "Unknown");
        String blood = prefs.getString("user_blood", "Unknown");
        String userInfo = "\nName: " + name + " | Blood: " + blood;

        String mapLink = " https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
        String fullMsg = customMsg + userInfo + mapLink;

        try {
            JSONArray contacts = new JSONArray(jsonString);

            if (contacts.length() == 0) {
                Toast.makeText(this, "No contacts saved!", Toast.LENGTH_SHORT).show();
                return;
            }

            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                String number = contact.getString("number");
                smsManager.sendTextMessage(number, null, fullMsg, null, null);
            }
            Toast.makeText(this, "SOS Sent!", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}