package com.example.guardian;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactFragment extends Fragment {

    EditText nameInput, phoneInput;
    LinearLayout contactListContainer;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        nameInput = view.findViewById(R.id.nameInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        contactListContainer = view.findViewById(R.id.contactListContainer);
        Button saveButton = view.findViewById(R.id.saveButton);

        sharedPreferences = getActivity().getSharedPreferences("MySafetyApp", Context.MODE_PRIVATE);

        loadContacts();

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String number = phoneInput.getText().toString();

            if(!name.isEmpty() && !number.isEmpty()){
                saveContact(name, number);
                nameInput.setText("");
                phoneInput.setText("");
            } else {
                Toast.makeText(getContext(), "Please enter both Name and Number", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void saveContact(String name, String number) {
        String jsonString = sharedPreferences.getString("contacts_list", "[]");
        try {
            JSONArray contacts = new JSONArray(jsonString);
            JSONObject newContact = new JSONObject();
            newContact.put("name", name);
            newContact.put("number", number);
            contacts.put(newContact);

            sharedPreferences.edit().putString("contacts_list", contacts.toString()).apply();
            loadContacts(); // Refresh UI
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteContact(int index) {
        String jsonString = sharedPreferences.getString("contacts_list", "[]");
        try {
            JSONArray contacts = new JSONArray(jsonString);
            JSONArray newContacts = new JSONArray();
            for (int i = 0; i < contacts.length(); i++) {
                if (i != index) {
                    newContacts.put(contacts.get(i));
                }
            }
            sharedPreferences.edit().putString("contacts_list", newContacts.toString()).apply();
            loadContacts();
            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        contactListContainer.removeAllViews();
        String jsonString = sharedPreferences.getString("contacts_list", "[]");

        try {
            JSONArray contacts = new JSONArray(jsonString);

            if (contacts.length() == 0) {
                TextView empty = new TextView(getContext());
                empty.setText("No contacts saved.");
                empty.setTextColor(Color.parseColor("#636E72")); // Visible Grey
                empty.setPadding(20, 20, 20, 20);
                contactListContainer.addView(empty);
                return;
            }

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                String name = contact.getString("name");
                String number = contact.getString("number");
                final int index = i;

                // 1. Create Card
                CardView card = new CardView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 24); // Add spacing between cards
                card.setLayoutParams(params);
                card.setRadius(16f);
                card.setCardElevation(6f);
                card.setContentPadding(40, 40, 40, 40);

                // --- CRITICAL FIX: Force White Background ---
                card.setCardBackgroundColor(Color.WHITE);
                // --------------------------------------------

                LinearLayout row = new LinearLayout(getContext());
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setGravity(Gravity.CENTER_VERTICAL);

                // 2. Info Section
                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);
                info.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                // Name Text (Indigo Color)
                TextView nameView = new TextView(getContext());
                nameView.setText(name);
                nameView.setTextSize(20f);
                nameView.setTextColor(Color.parseColor("#3949AB")); // Indigo Blue for visibility
                nameView.setTypeface(null, Typeface.BOLD);

                // Number Text (Dark Grey)
                TextView numView = new TextView(getContext());
                numView.setText(number);
                numView.setTextSize(14f);
                numView.setTextColor(Color.parseColor("#2D3436")); // Dark Grey

                info.addView(nameView);
                info.addView(numView);

                // 3. Delete Button (Red)
                Button deleteBtn = new Button(getContext());
                deleteBtn.setText("DELETE");
                deleteBtn.setTextSize(12f);
                deleteBtn.setTextColor(Color.RED);
                deleteBtn.setBackgroundColor(Color.TRANSPARENT);
                deleteBtn.setOnClickListener(v -> deleteContact(index));

                row.addView(info);
                row.addView(deleteBtn);
                card.addView(row);

                contactListContainer.addView(card);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}