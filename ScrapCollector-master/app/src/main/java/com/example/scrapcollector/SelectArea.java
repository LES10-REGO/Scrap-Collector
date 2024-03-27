package com.example.scrapcollector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class SelectArea extends AppCompatActivity {

    HashSet<String> locationSet = new HashSet<>();
    ArrayList<String> locationList = new ArrayList<>();

    ProgressDialog progressDialog;
    String Location = "";
    String json_string = "";
    String JSON_STRING = "";
    JSONObject jsonObject;
    JSONArray jsonArray;
    Spinner mySpinner;
    DatabaseReference databaseGarbage;
    ImageButton go;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areaselect);

        databaseGarbage = FirebaseDatabase.getInstance().getReference("garbage");
        mySpinner = findViewById(R.id.select_area);
        go = findViewById(R.id.find_garbage);
        button =findViewById(R.id.button2);

        locationList.add("Select Area");

        Query query = databaseGarbage.orderByChild("locality");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locationSet.clear(); // Clear the set to avoid duplicates

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PostGarbage postGarbage = childSnapshot.getValue(PostGarbage.class);
                    String locality = postGarbage.getLocality();
                    if (!postGarbage.getLocked().equals("1")) {
                        // Add the location to the set only if it is not serviced
                        locationSet.add(locality);
                    }
                }

                locationList.clear();
                locationList.add("Select Area");
                locationList.addAll(locationSet);

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(SelectArea.this,
                        android.R.layout.simple_spinner_item, locationList);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedArea = mySpinner.getSelectedItem().toString();
                Log.d("selectedArea", selectedArea);

                Intent intent = new Intent(SelectArea.this, GarbageDetails.class);
                intent.putExtra("area", selectedArea);
                startActivity(intent);
            }
        });

        // Logout button

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions here (e.g., clear session, sign out, etc.)
                // ...

                // Show a toast message for logout success
                Toast.makeText(SelectArea.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Start the main activity and clear the activity stack
                Intent intent = new Intent(SelectArea.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                 // Finish the current activity
            }
        });
    }
}
