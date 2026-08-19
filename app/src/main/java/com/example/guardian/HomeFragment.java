package com.example.guardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnFakeCall = view.findViewById(R.id.btnFakeCall);
        Button btnMap = view.findViewById(R.id.btnMap); // New Button
        Button btnSOS = view.findViewById(R.id.btnSOS);

        // Open Fake Call Screen
        btnFakeCall.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), FakeCallActivity.class));
        });

        // Open Live Map Screen
        btnMap.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MapActivity.class));
        });

        // Trigger SOS (Logic is in MainActivity)
        btnSOS.setOnLongClickListener(v -> {
            ((MainActivity)getActivity()).checkAndSendSOS();
            return true;
        });

        return view;
    }
}