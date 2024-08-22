package com.example.moneymanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        String fName = getIntent().getStringExtra("fName");
        String lName = getIntent().getStringExtra("lName");
        String password = getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtPass = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPhone.getText().toString().equals(phone) && edtPass.getText().toString().equals(password)) {
                    Intent intent = new Intent(Login.this,Home.class);
                    startActivity(intent);
                }
            }
        });

        TextView txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }


}
