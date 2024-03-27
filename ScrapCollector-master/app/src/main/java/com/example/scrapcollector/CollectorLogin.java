package com.example.scrapcollector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

public class CollectorLogin extends AppCompatActivity {

    private FirebaseAuth auth;
    String username="";
    String password="";
    String message="";
    String J_STRING="";
    JSONObject jsonObject;
    TextView usernamee,passwordd,btnSignup;
    ImageButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectorlog);
        auth = FirebaseAuth.getInstance();
        usernamee=(TextView) findViewById(R.id.login_input_email);
        passwordd=(TextView) findViewById(R.id.login_input_password);
        btnLogin = (ImageButton) findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.link_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollectorLogin.this, CollectorRegister.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernamee.getText().toString();
                final String password = passwordd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate email format
                if (!isValidBusinessEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Invalid email format! Please enter a business email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CollectorLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Toast.makeText(CollectorLogin.this, "Password should be more than 6 characters",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CollectorLogin.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(CollectorLogin.this, SelectArea.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    // Validate email format for business emails
    private boolean isValidBusinessEmail(String email) {
        // Add your email validation logic here
        // For example, you can check if the email domain is a valid business domain
        // You can customize this logic based on your specific requirements
        String businessDomain = "example.com"; // Replace with your desired business domain
        return email.endsWith("@" + businessDomain);
    }
}
