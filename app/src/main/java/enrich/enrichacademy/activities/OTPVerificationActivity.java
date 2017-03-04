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

import java.io.IOException;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.ResponseDS;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.PostParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OTPVerificationActivity extends AppCompatActivity {

    TextView mResendBtn;
    Button mOTPProceedBtn;
    EditText mMobileNumberEdit, mOTPEdit;
    LinearLayout mobileNumberContainer, otpContainer;

    String mobileNumberStr, otpStr, userIdStr;

    boolean isMobileNumberEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        mResendBtn = (TextView) findViewById(R.id.resend_btn);
        mOTPProceedBtn = (Button) findViewById(R.id.otp_proceed_btn);
        mMobileNumberEdit = (EditText) findViewById(R.id.mobile_number_edit);
        mOTPEdit = (EditText) findViewById(R.id.otp_edit);
        mobileNumberContainer = (LinearLayout) findViewById(R.id.mobile_number_container);
        otpContainer = (LinearLayout) findViewById(R.id.otp_container);

        switchMobileNumberOTPLayout(false);

        mOTPProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMobileNumberEntered) {
                    mobileNumberStr = mMobileNumberEdit.getText().toString();
                    if (mobileNumberStr.length() < 10) {
                        EnrichUtils.showMessage(OTPVerificationActivity.this, "Please enter a valid mobile number");
                    } else {
                        verifyNow(mobileNumberStr);

//                        switchMobileNumberOTPLayout(true);
//                        isMobileNumberEntered = true;
                    }
                } else {
                    otpStr = mOTPEdit.getText().toString();
                    if (otpStr.length() < 4) {
                        EnrichUtils.showMessage(OTPVerificationActivity.this, "Please enter a valid OTP");
                    } else {
                        verifyOtp(mobileNumberStr, otpStr);
                    }
                }
            }
        });

        mResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userIdStr != null)
                    resendOtpNow();
            }
        });
    }

    private void switchMobileNumberOTPLayout(boolean isMobileNumberEntered) {
        if (isMobileNumberEntered) {
            mobileNumberContainer.setVisibility(View.GONE);
            otpContainer.setVisibility(View.VISIBLE);
        } else {
            mobileNumberContainer.setVisibility(View.VISIBLE);
            otpContainer.setVisibility(View.GONE);
        }
    }

    public void verifyNow(String mobNumber) {
        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);
        Call<ResponseDS<String>> otpModelCall = EnrichUtils.getRetrofit().sendOtp(PostParams.init().add("Mobile", mobNumber).addPlatform());
        otpModelCall.enqueue(new Callback<ResponseDS<String>>() {
            @Override
            public void onResponse(Call<ResponseDS<String>> call, Response<ResponseDS<String>> response) {
                if (response.body().s == Constants.API_SUCCESS) {
                    userIdStr = response.body().d;
                    switchMobileNumberOTPLayout(true);
                    isMobileNumberEntered = true;
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }

            @Override
            public void onFailure(Call<ResponseDS<String>> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("TAG", "Error : " + "No Internet Connection");
                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
                    EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
                }
            }
        });
    }

    public void verifyOtp(final String mobileNumberStr, String otpNumber) {
        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);
        Call<ResponseDS<String>> userModelCall = EnrichUtils.getRetrofit().verifyOtp(PostParams.init().add("RegistrationId", userIdStr + "").add("OTP", otpNumber).addPlatform());
        userModelCall.enqueue(new Callback<ResponseDS<String>>() {
            @Override
            public void onResponse(Call<ResponseDS<String>> call, Response<ResponseDS<String>> response) {
                if (response.body().s == Constants.API_SUCCESS) {
                    Intent intent = new Intent(OTPVerificationActivity.this, RegisterProfileActivity.class);
                    intent.putExtra("MobileNumber", mobileNumberStr);
                    startActivity(intent);
                    finish();
                } else {
                    if (response.body().e != null) {
                        EnrichUtils.showMessage(OTPVerificationActivity.this, response.body().e);
                    }
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }

            @Override
            public void onFailure(Call<ResponseDS<String>> call, Throwable t) {
                Intent intent = new Intent(OTPVerificationActivity.this, RegisterProfileActivity.class);
                intent.putExtra("MobileNumber", mobileNumberStr);
                startActivity(intent);
                if (t instanceof IOException) {
                    Log.e("TAG", "Error : " + "No Internet Connection");
                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }
        });
    }

    class ResendOptReq {
        int RegistrationId;

        public ResendOptReq(int registrationId) {
            RegistrationId = registrationId;
        }
    }

    public void resendOtpNow() {
        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);
        Call<ResponseDS<Boolean>> otpModelCall = EnrichUtils.getRetrofit().resendOtp(EnrichUtils.newGson().toJsonTree(new ResendOptReq(Integer.parseInt(userIdStr))));
        otpModelCall.enqueue(new Callback<ResponseDS<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseDS<Boolean>> call, Response<ResponseDS<Boolean>> response) {
                if (response.body() != null) {
                    if (response.body().s == Constants.API_SUCCESS) {
                        EnrichUtils.showMessage(OTPVerificationActivity.this, "OTP requested Successfully.");
                    } else {
                        EnrichUtils.showMessage(OTPVerificationActivity.this, "Error !! " + response.body().e);
                    }
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }

            @Override
            public void onFailure(Call<ResponseDS<Boolean>> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("TAG", "Error : " + "No Internet Connection");
                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }
        });
    }
}
