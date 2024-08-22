package com.example.moneymanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTP extends AppCompatActivity {

    private TextView edtCode1, edtCode2, edtCode3, edtCode4, edtCode5, edtCode6;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        TextView txtMobile = findViewById(R.id.txtMobile);
        txtMobile.setText(String.format(
                "+84-%s", getIntent().getStringExtra("phone")
        ));

        edtCode1 = findViewById(R.id.edtCode1);
        edtCode2 = findViewById(R.id.edtCode2);
        edtCode3 = findViewById(R.id.edtCode3);
        edtCode4 = findViewById(R.id.edtCode4);
        edtCode5 = findViewById(R.id.edtCode5);
        edtCode6 = findViewById(R.id.edtCode6);

        setUpOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button btnSend = findViewById(R.id.btnSend);

        verificationId = getIntent().getStringExtra("verificationId");
        String lName = getIntent().getStringExtra("lName");
        String fName = getIntent().getStringExtra("fName");
        String password = getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCode1.getText().toString().trim().isEmpty()
                || edtCode2.getText().toString().trim().isEmpty()
                || edtCode3.getText().toString().trim().isEmpty()
                || edtCode4.getText().toString().trim().isEmpty()
                || edtCode5.getText().toString().trim().isEmpty()
                || edtCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTP.this,"Không được để trống mã",Toast.LENGTH_LONG).show();
                    return;
                }
                String code = edtCode1.getText().toString() +
                        edtCode2.getText().toString() +
                        edtCode3.getText().toString() +
                        edtCode4.getText().toString() +
                        edtCode5.getText().toString() +
                        edtCode6.getText().toString();

                if(verificationId != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    btnSend.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(OTP.this,Login.class);
                                        intent.putExtra("fName",fName);
                                        intent.putExtra("lName",lName);
                                        intent.putExtra("password",password);
                                        intent.putExtra("phone",phone);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(OTP.this,"Mã nhập không hợp lệ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setUpOTPInputs() {
        edtCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    edtCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    edtCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    edtCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    edtCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    edtCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}