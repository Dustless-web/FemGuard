package com.example.guardian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Views
        EditText msgInput = view.findViewById(R.id.msgInput);
        EditText nameInput = view.findViewById(R.id.nameInput);
        EditText bloodInput = view.findViewById(R.id.bloodInput);
        Button btnSaveMsg = view.findViewById(R.id.btnSaveMsg);
        Button btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        Button btnBattery = view.findViewById(R.id.btnBattery);
        Switch switchSiren = view.findViewById(R.id.switchSiren);
        Switch switchVibrate = view.findViewById(R.id.switchVibrate);

        sharedPreferences = getActivity().getSharedPreferences("MySafetyApp", Context.MODE_PRIVATE);

        // Load saved values
        msgInput.setText(sharedPreferences.getString("sos_msg", "HELP! I need emergency assistance."));
        nameInput.setText(sharedPreferences.getString("user_name", ""));
        bloodInput.setText(sharedPreferences.getString("user_blood", ""));
        switchSiren.setChecked(sharedPreferences.getBoolean("siren_enabled", false));
        switchVibrate.setChecked(sharedPreferences.getBoolean("vibrate_enabled", true));

        // Save Message
        btnSaveMsg.setOnClickListener(v -> {
            sharedPreferences.edit().putString("sos_msg", msgInput.getText().toString()).apply();
            Toast.makeText(getContext(), "Message Updated", Toast.LENGTH_SHORT).show();
        });

        // Save Profile
        btnSaveProfile.setOnClickListener(v -> {
            sharedPreferences.edit()
                    .putString("user_name", nameInput.getText().toString())
                    .putString("user_blood", bloodInput.getText().toString())
                    .apply();
            Toast.makeText(getContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
        });

        // Battery Optimization
        btnBattery.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent();
                String packageName = getContext().getPackageName();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Not needed for this Android version", Toast.LENGTH_SHORT).show();
            }
        });

        // Toggles
        switchSiren.setOnCheckedChangeListener((bv, isChecked) ->
                sharedPreferences.edit().putBoolean("siren_enabled", isChecked).apply());

        switchVibrate.setOnCheckedChangeListener((bv, isChecked) ->
                sharedPreferences.edit().putBoolean("vibrate_enabled", isChecked).apply());

        return view;
    }
}