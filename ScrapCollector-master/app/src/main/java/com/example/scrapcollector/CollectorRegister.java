package com.example.scrapcollector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CollectorRegister extends AppCompatActivity {
    DatabaseReference databaseUsers;
    private EditText et_fullname, et_username, et_password, et_email, et_contact;
    private RadioButton rb_male;
    private String reg_fullname, reg_user, reg_password, reg_email, reg_contact, reg_gender;
    private FirebaseAuth auth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectorregister);
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("collector");


        et_fullname = findViewById(R.id.et_fullname);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        et_contact = findViewById(R.id.et_contact);
        rb_male = findViewById(R.id.rb_male);
        RadioButton rb_female = findViewById(R.id.rb_female);
        ImageButton button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            if ((et_fullname.getText().toString().trim().equals("")) || (et_username.getText().toString().trim().equals("")) || (et_password.getText().toString().trim().equals("")) || (et_email.getText().toString().trim().equals("")) || (et_contact.getText().toString().trim().equals(""))) {

                Toast.makeText(CollectorRegister.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
            } else {
                reg_fullname = et_fullname.getText().toString();
                reg_user = et_username.getText().toString();
                reg_password = et_password.getText().toString();
                reg_email = et_email.getText().toString();
                reg_contact = et_contact.getText().toString();
                reg_gender = null;

                if (rb_male.isChecked()) {
                    reg_gender = "male";
                } else {
                    reg_gender = "female";
                }

                // Validate email format
                if (!isValidBusinessEmail(reg_email)) {
                    Toast.makeText(getApplicationContext(), "Invalid email format! Please enter a business email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(reg_email, reg_password)
                        .addOnCompleteListener(CollectorRegister.this, task -> {
                            Toast.makeText(CollectorRegister.this, "User Successfully Created", Toast.LENGTH_LONG).show();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(CollectorRegister.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                String id1 = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                addUser(id1, reg_fullname, reg_user, reg_password, reg_email, reg_contact, reg_gender);
                            }
                        });
            }
        });
    }

    private boolean isValidBusinessEmail(String email) {
        String businessDomain = "example.com"; // Replace with your desired business domain

        String[] parts = email.split("@");
        if (parts.length != 2)
            return false;

        String domain = parts[1];
        return domain.equalsIgnoreCase(businessDomain);
    }

    public void addUser(String id, String name, String username, String password, String email, String contact, String gender) {
        User user = new User(id, name, username, password, email, contact, gender);
        databaseUsers.child(id).setValue(user);
        // Display a success toast
        Toast.makeText(this, "Collector added with Key: " + id, Toast.LENGTH_LONG).show();
        startActivity(new Intent(CollectorRegister.this, CollectorLogin.class));
        finish();
    }
}
