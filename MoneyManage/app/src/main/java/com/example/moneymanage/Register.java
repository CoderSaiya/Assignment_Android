package com.example.moneymanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView edtFName = findViewById(R.id.edtFName);
        TextView edtLName = findViewById(R.id.edtLName);
        TextView edtPassword = findViewById(R.id.edtPassword);
        Button btnRegister = findViewById(R.id.btnRegis);
        final EditText edtPhone = findViewById(R.id.edtPhone);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPhone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Register.this,"Hãy nhập số điện thoại",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84" + edtPhone.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        Register.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnRegister.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btnRegister.setVisibility(View.VISIBLE);
                                Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btnRegister.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(Register.this,OTP.class);
                                intent.putExtra("phone",edtPhone.getText().toString());
                                intent.putExtra("verificationId",s);
                                intent.putExtra("fName",edtFName.getText().toString());
                                intent.putExtra("lName",edtLName.getText().toString());
                                intent.putExtra("password",edtPassword.getText().toString());
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }
}
