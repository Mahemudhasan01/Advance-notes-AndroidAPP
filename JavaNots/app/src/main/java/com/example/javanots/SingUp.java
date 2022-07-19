package com.example.javanots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUp extends AppCompatActivity {

    private EditText msignupemail,msignuppassword;
    private RelativeLayout msignup;
    private TextView mgotologin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        msignupemail=findViewById(R.id.signupemail);
        msignuppassword=findViewById(R.id.signuppassword);
        mgotologin=findViewById(R.id.gotologin);
        msignup=findViewById(R.id.signup);

        firebaseAuth=FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingUp.this,MainActivity.class);
                startActivity(intent);
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=msignupemail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Filed Are Required", Toast.LENGTH_SHORT).show();
                    //System.out.println("All Text Filed Are Required");

                }
                else if (password.length()<7)
                {
                   Toast.makeText(getApplicationContext(),"Password Should Greater than 7 Latter",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Register User To the Fire Base...


                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_SHORT).show();

                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Registration Successfull!", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
                                                                //Send Verification Email...
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is Sent, Verify And Login Again",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SingUp.this,MainActivity.class));
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Failed To Send Verification Email Try Again",Toast.LENGTH_SHORT).show();
        }
    }
}