package com.example.moneymanage;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OTP extends AppCompatActivity {

    private TextView edtCode1, edtCode2, edtCode3, edtCode4, edtCode5, edtCode6;
    private String verificationId;

    private static final String URL = "jdbc:mysql://10.0.2.2:3306/moneymanage?connectTimeout=10000&socketTimeout=10000&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "NhatCuong04@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        initializeUI();

        verificationId = getIntent().getStringExtra("verificationId");
        String lName = getIntent().getStringExtra("lName");
        String fName = getIntent().getStringExtra("fName");
        String password = getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> {
            if (isOTPEntered()) {
                String code = getEnteredOTP();
                if (verificationId != null) {
                    verifyOTP(code, fName, lName, password, phone);
                }
            } else {
                Toast.makeText(OTP.this, "Không được để trống mã", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeUI() {
        TextView txtMobile = findViewById(R.id.txtMobile);
        txtMobile.setText(String.format("+84-%s", getIntent().getStringExtra("phone")));

        edtCode1 = findViewById(R.id.edtCode1);
        edtCode2 = findViewById(R.id.edtCode2);
        edtCode3 = findViewById(R.id.edtCode3);
        edtCode4 = findViewById(R.id.edtCode4);
        edtCode5 = findViewById(R.id.edtCode5);
        edtCode6 = findViewById(R.id.edtCode6);

        setUpOTPInputs();
    }

    private boolean isOTPEntered() {
        return !edtCode1.getText().toString().trim().isEmpty() &&
                !edtCode2.getText().toString().trim().isEmpty() &&
                !edtCode3.getText().toString().trim().isEmpty() &&
                !edtCode4.getText().toString().trim().isEmpty() &&
                !edtCode5.getText().toString().trim().isEmpty() &&
                !edtCode6.getText().toString().trim().isEmpty();
    }

    private String getEnteredOTP() {
        return edtCode1.getText().toString() +
                edtCode2.getText().toString() +
                edtCode3.getText().toString() +
                edtCode4.getText().toString() +
                edtCode5.getText().toString() +
                edtCode6.getText().toString();
    }

    private void verifyOTP(String code, String fName, String lName, String password, String phone) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button btnSend = findViewById(R.id.btnSend);

        progressBar.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.INVISIBLE);

        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        btnSend.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            new SaveUserDataTask(fName, lName, password, phone).execute();
                        } else {
                            Toast.makeText(OTP.this, "Mã nhập không hợp lệ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private class SaveUserDataTask extends AsyncTask<Void, Void, Boolean> {
        private String fName, lName, password, phone;

        SaveUserDataTask(String fName, String lName, String password, String phone) {
            this.fName = fName;
            this.lName = lName;
            this.password = password;
            this.phone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return saveUserData(fName, lName, password, phone);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent(OTP.this, Login.class);
                intent.putExtra("fName", fName);
                intent.putExtra("lName", lName);
                intent.putExtra("password", password);
                intent.putExtra("phone", phone);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(OTP.this, "Không thể lưu dữ liệu người dùng", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean saveUserData(String fName, String lName, String password, String phone) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isSuccess = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO users (fName, lName, phone, password) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, password);

            preparedStatement.executeUpdate();
            isSuccess = true;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    private void setUpOTPInputs() {
        edtCode1.addTextChangedListener(new OTPTextWatcher(edtCode1, edtCode2));
        edtCode2.addTextChangedListener(new OTPTextWatcher(edtCode2, edtCode3));
        edtCode3.addTextChangedListener(new OTPTextWatcher(edtCode3, edtCode4));
        edtCode4.addTextChangedListener(new OTPTextWatcher(edtCode4, edtCode5));
        edtCode5.addTextChangedListener(new OTPTextWatcher(edtCode5, edtCode6));
    }

    private class OTPTextWatcher implements TextWatcher {
        private TextView currentView, nextView;

        OTPTextWatcher(TextView currentView, TextView nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().trim().isEmpty()) {
                nextView.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }
}