package com.example.albunsifinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;
    private Button back;

    private FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("MAKE A NEW EVH ACCOUNT");

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        back=findViewById(R.id.back);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();

                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(RegisterActivity.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length()<6)
                {
                    Toast.makeText(RegisterActivity.this,"Password too short",Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_email,txt_password);
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void registerUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registering user successful!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MapsActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Registration failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
