package com.example.scrapcollector;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class areaselect extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areaselect);

        Spinner selectAreaSpinner = findViewById(R.id.select_area);

        // Example data source with potential duplicate entries
        List<String> areaList = Arrays.asList("Area 1", "Area 2", "Area 3", "Area 1", "Area 2");

        // Remove duplicates from the list
        List<String> uniqueAreaList = new ArrayList<>(new HashSet<>(areaList));

        // Create an ArrayAdapter with the unique list of areas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, uniqueAreaList);

        // Set the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        selectAreaSpinner.setAdapter(adapter);
    }
}
