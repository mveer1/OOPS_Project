package com.example.oops_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class verificationpage extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    EditText phoneNumber, codeEnter;
    Button nextBtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationpage);
        fAuth = FirebaseAuth.getInstance();
        phoneNumber = findViewById(R.id.phone);
        codeEnter = findViewById(R.id.codeEnter);
        progressBar = findViewById(R.id.progressBar);
        nextBtn = findViewById(R.id.nextBtn);
        state = findViewById(R.id.state);
        codePicker = findViewById(R.id.ccp);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(!verificationInProgress){
                    if(!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length() == 10){
                        String phoneNum = "+" + codePicker.getSelectedCountryCode()+phoneNumber.getText().toString();
                        Log.d(TAG, "onClick: Phone No -> " + phoneNum);
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP... ");
                        state.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);
                    }
                    else {
                        phoneNumber.setError("Phone Number is Not Valid");
                    }
                }else {
                    String userOTP = codeEnter.getText().toString();
                    if(/*!userOTP.isEmpty() &&*/ userOTP.length() == 6){
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, userOTP);
                        verifyAuth(credential);
                    }
                }

            }
        });
}

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(verificationpage.this, "Authentication is Successful", Toast.LENGTH_SHORT).show();   // this shall take user to the next activity
                }else{
                    Toast.makeText(verificationpage.this, "Authentication is Failed", Toast.LENGTH_SHORT).show();       // this shall take user back
                }
            }
        });
    }

    private void requestOTP(String phoneNum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId = s;
                token = forceResendingToken;
                nextBtn.setText("Verify");
                verificationInProgress = true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(verificationpage.this, "Cannot Create Account "+ e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}