package com.example.scrapcollector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Contact extends AppCompatActivity {
    Button button;
    String code = "";
    String contact = "";
    String j_url = "";
    String message = "";
    String j_string = "";
    String JSON_STRING = "";
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        code = getIntent().getExtras().getString("code");
        contact = getIntent().getExtras().getString("number");
        TextView codee = (TextView) findViewById(R.id.unique_code);
        TextView contactt = (TextView) findViewById(R.id.contact);
        codee.setText(code);
        contactt.setText(contact);
        Toast.makeText(Contact.this, "Successfully Accepted, You can Collect Scrap Later!!", Toast.LENGTH_LONG).show();

        button = findViewById(R.id.button); // Assuming the button's ID is "button"
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact.this, SelectArea.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
