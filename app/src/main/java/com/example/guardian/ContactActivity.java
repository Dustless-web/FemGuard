package com.example.guardian;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class ContactActivity extends AppCompatActivity {
    EditText phoneInput;
    Button saveButton;
    TextView savedNumbersView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        phoneInput = findViewById(R.id.phoneInput);
        saveButton = findViewById(R.id.saveButton);
        savedNumbersView = findViewById(R.id.savedNumbersView);

        sharedPreferences = getSharedPreferences("MySafetyApp", MODE_PRIVATE);

        refreshContactList();

        saveButton.setOnClickListener(v -> {
            String number = phoneInput.getText().toString();
            if(!number.isEmpty()){
                saveNumber(number);
                phoneInput.setText("");
            }
        });
    }

    private void saveNumber(String number) {
        Set<String> numbers = sharedPreferences.getStringSet("trusted_numbers", new HashSet<>());
        Set<String> newNumbers = new HashSet<>(numbers);
        newNumbers.add(number);

        sharedPreferences.edit().putStringSet("trusted_numbers", newNumbers).apply();
        Toast.makeText(this, "Contact Saved!", Toast.LENGTH_SHORT).show();
        refreshContactList();
    }

    private void refreshContactList() {
        Set<String> numbers = sharedPreferences.getStringSet("trusted_numbers", new HashSet<>());
        if (numbers.isEmpty()) {
            savedNumbersView.setText("No contacts saved yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for(String s : numbers) {
                sb.append("• ").append(s).append("\n");
            }
            savedNumbersView.setText(sb.toString());
        }
    }
}