package com.example.javanots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
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

public class MainActivity extends AppCompatActivity {

    private EditText mloginemail,mloginpassword;
    private RelativeLayout mlogin,mgotosignup;
    private TextView mgotoforgotpassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if (firebaseUser!=null)
        {
            startActivity(new Intent(MainActivity.this,NotesActivity.class));
        }

        mloginemail=findViewById(R.id.loginemail);
        mloginpassword=findViewById(R.id.loginpassword);
        mlogin=findViewById(R.id.login);
        mgotosignup=findViewById(R.id.gotosingup);
        mgotoforgotpassword=findViewById(R.id.gotoforgotpassword);



        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SingUp.class));
            }
        });
        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,Fogotpassword.class));
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mloginemail.getText().toString().trim();
                String password=mloginpassword.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Field Are Required",Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<7)
                {
                    Toast.makeText(getApplicationContext(), "Password Should Greater Than 7 Latter", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Login User...

                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                checkEnailVerification();
                               // Toast.makeText(getApplicationContext(),"Login is Successfull",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Account Does't Exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });
    }

    private void checkEnailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,NotesActivity.class));
        }
        else 
        {
            Toast.makeText(getApplicationContext(), "Verify Your Mail First", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}