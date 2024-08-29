package com.example.moneymanage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends AppCompatActivity {

    private EditText edtPhone, edtPass;
    private static final String URL = "jdbc:mysql://10.0.2.2:3306/moneymanage?connectTimeout=10000&socketTimeout=10000&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "NhatCuong04@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeUI();

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> authenticateUser());

        TextView txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(v -> openRegisterActivity());
    }

    private void initializeUI() {
        edtPhone = findViewById(R.id.edtPhone);
        edtPass = findViewById(R.id.edtPassword);
    }

    private void authenticateUser() {
        String phone = edtPhone.getText().toString();
        String password = edtPass.getText().toString();

        if (!phone.isEmpty() && !password.isEmpty()) {
            new AuthenticateUserTask(phone, password).execute();
        } else {
            Toast.makeText(Login.this, "Vui lòng nhập số điện thoại và mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    private class AuthenticateUserTask extends AsyncTask<Void, Void, Boolean> {
        private String phone, password;

        AuthenticateUserTask(String phone, String password) {
            this.phone = phone;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return authenticate(phone, password);
        }

        @Override
        protected void onPostExecute(Boolean isAuthenticated) {
            if (isAuthenticated) {
                Intent intent = new Intent(Login.this, Home.class);
                intent.putExtra("phone",edtPhone.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Số điện thoại hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean authenticate(String phone, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isAuthenticated = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM users WHERE phone = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isAuthenticated;
    }
}
