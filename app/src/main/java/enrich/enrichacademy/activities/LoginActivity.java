package enrich.enrichacademy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.ResponseDS;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.PostParams;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView signUpButton;
    Button mOTPProceedBtn;
    EditText mMobileNumberEdit;
    LinearLayout mobileNumberContainer;

    String mobileNumberStr, otpStr, userIdStr;

    boolean isMobileNumberEntered = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mOTPProceedBtn = (Button) findViewById(R.id.otp_proceed_btn);
        mMobileNumberEdit = (EditText) findViewById(R.id.mobile_number_edit);
        mobileNumberContainer = (LinearLayout) findViewById(R.id.mobile_number_container);
        signUpButton = (TextView) findViewById(R.id.sign_up_button);

//        switchMobileNumberOTPLayout(false);

        mOTPProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
//                if (!isMobileNumberEntered) {
                mobileNumberStr = mMobileNumberEdit.getText().toString();
                if (mobileNumberStr.length() < 10) {
                    EnrichUtils.showMessage(LoginActivity.this, "Please enter a valid mobile number");
                } else {
                    verifyNow(mobileNumberStr);

//                        switchMobileNumberOTPLayout(true);
//                        isMobileNumberEntered = true;
                }
//                } else {
//                    otpStr = mOTPEdit.getText().toString();
//                    if (otpStr.length() < 4) {
//                        EnrichUtils.showMessage(LoginActivity.this, "Please enter a valid OTP");
//                    } else {
//                        verifyOtp(mobileNumberStr, otpStr);
//                    }
//                }
            }
        });

//        mResendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//                if (userIdStr != null)
//                    resendOtpNow();
//            }
//        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterProfileActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void switchMobileNumberOTPLayout (boolean isMobileNumberEntered) {
//        if (isMobileNumberEntered) {
//            mobileNumberContainer.setVisibility(View.GONE);
//            otpContainer.setVisibility(View.VISIBLE);
//        } else {
//            mobileNumberContainer.setVisibility(View.VISIBLE);
//            otpContainer.setVisibility(View.GONE);
//        }
//    }

    public void verifyNow (final String mobNumber) {
        EnrichUtils.showProgressDialog(LoginActivity.this);

        EnrichUtils.log(EnrichURLs.ENRICH_HOST + EnrichURLs.LOGIN + "/" + mobNumber);

        Call<SingleResponseModel<String>> otpModelCall = EnrichUtils.getRetrofitForEnrich().sendOtp(EnrichURLs.ENRICH_HOST + EnrichURLs.LOGIN + "/" + mobNumber);
        //PostParams.init().add("Mobile", mobNumber).addPlatform()
        otpModelCall.enqueue(new Callback<SingleResponseModel<String>>() {
            @Override
            public void onResponse (Call<SingleResponseModel<String>> call, Response<SingleResponseModel<String>> response) {
                if (response.code() == 200) {
                    if (response.body().status == 0) {
                        Intent intent = new Intent(LoginActivity.this, OTPVerificationActivity.class);
                        intent.putExtra("LOGIN", true);
                        intent.putExtra("MobileNumber", mobNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        EnrichUtils.showMessage(LoginActivity.this, "" + response.body().data);
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }
                EnrichUtils.cancelCurrentDialog(LoginActivity.this);
            }

            @Override
            public void onFailure (Call<SingleResponseModel<String>> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("TAG", "Error : " + "No Internet Connection");
                    EnrichUtils.showMessage(LoginActivity.this, "Please check Internet Connection");
                    EnrichUtils.cancelCurrentDialog(LoginActivity.this);
                }
            }
        });
    }

//    public void verifyOtp (final String mobileNumberStr, String otpNumber) {
//        EnrichUtils.showProgressDialog(LoginActivity.this);
//        Call<ResponseDS<String>> userModelCall = EnrichUtils.getRetrofit().verifyOtp(PostParams.init().add("RegistrationId", userIdStr + "").add("OTP", otpNumber).addPlatform());
//        userModelCall.enqueue(new Callback<ResponseDS<String>>() {
//            @Override
//            public void onResponse (Call<ResponseDS<String>> call, Response<ResponseDS<String>> response) {
//                if (response.body().s == Constants.API_SUCCESS) {
//                    Intent intent = new Intent(LoginActivity.this, RegisterProfileActivity.class);
//                    intent.putExtra("MobileNumber", mobileNumberStr);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    if (response.body().e != null) {
//                        EnrichUtils.showMessage(LoginActivity.this, response.body().e);
//                    }
//                }
//                EnrichUtils.cancelCurrentDialog(LoginActivity.this);
//            }
//
//            @Override
//            public void onFailure (Call<ResponseDS<String>> call, Throwable t) {
//                Intent intent = new Intent(LoginActivity.this, RegisterProfileActivity.class);
//                intent.putExtra("MobileNumber", mobileNumberStr);
//                startActivity(intent);
//                if (t instanceof IOException) {
//                    Log.e("TAG", "Error : " + "No Internet Connection");
//                    EnrichUtils.showMessage(LoginActivity.this, "Please check Internet Connection");
//                }
//                EnrichUtils.cancelCurrentDialog(LoginActivity.this);
//            }
//        });
//    }

//    class ResendOptReq {
//        int RegistrationId;
//
//        public ResendOptReq (int registrationId) {
//            RegistrationId = registrationId;
//        }
//    }
//
//    public void resendOtpNow () {
//        EnrichUtils.showProgressDialog(LoginActivity.this);
//        Call<ResponseDS<Boolean>> otpModelCall = EnrichUtils.getRetrofit().resendOtp(EnrichUtils.newGson().toJsonTree(new ResendOptReq(Integer.parseInt(userIdStr))));
//        otpModelCall.enqueue(new Callback<ResponseDS<Boolean>>() {
//            @Override
//            public void onResponse (Call<ResponseDS<Boolean>> call, Response<ResponseDS<Boolean>> response) {
//                if (response.body() != null) {
//                    if (response.body().s == Constants.API_SUCCESS) {
//                        EnrichUtils.showMessage(LoginActivity.this, "OTP requested Successfully.");
//                    } else {
//                        EnrichUtils.showMessage(LoginActivity.this, "Error !! " + response.body().e);
//                    }
//                }
//                EnrichUtils.cancelCurrentDialog(LoginActivity.this);
//            }
//
//            @Override
//            public void onFailure (Call<ResponseDS<Boolean>> call, Throwable t) {
//                if (t instanceof IOException) {
//                    Log.e("TAG", "Error : " + "No Internet Connection");
//                    EnrichUtils.showMessage(LoginActivity.this, "Please check Internet Connection");
//                }
//                EnrichUtils.cancelCurrentDialog(LoginActivity.this);
//            }
//        });
//    }
}
