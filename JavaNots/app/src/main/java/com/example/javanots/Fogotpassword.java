package com.example.javanots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Fogotpassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecoverbutton;
    private TextView mgobacktologin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogotpassword);

        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();
        mforgotpassword=findViewById(R.id.forgotpassword);
        mpasswordrecoverbutton=findViewById(R.id.passwordrecoverbutton);
        mgobacktologin=findViewById(R.id.gobacktologin);

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Fogotpassword.this,MainActivity.class));

            }
        });

        mpasswordrecoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mforgotpassword.getText().toString().trim();
                if(mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Your Email First",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //We Will Send Password Recover Email...
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful())
                          {
                              Toast.makeText(getApplicationContext(),"Mail Sent In Your Registered Email",Toast.LENGTH_SHORT).show();
                              finish();
                              startActivity(new Intent(Fogotpassword.this,MainActivity.class));
                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(), "Account Not Exist",Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
                }
            }
        });
    }
}