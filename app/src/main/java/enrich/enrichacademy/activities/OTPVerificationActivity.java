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
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.PostParams;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationActivity extends AppCompatActivity {

    TextView mResendBtn;
    Button mOTPProceedBtn;
    EditText mOTPEdit;
    LinearLayout otpContainer;

    String mobileNumberStr, otpStr, userIdStr;
    boolean isForLogin = false;

//    boolean isMobileNumberEntered = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        isForLogin = getIntent().getBooleanExtra("LOGIN",false);
        mobileNumberStr = getIntent().getStringExtra("MobileNumber");

        mResendBtn = (TextView) findViewById(R.id.resend_btn);
        mOTPProceedBtn = (Button) findViewById(R.id.otp_proceed_btn);
        mOTPEdit = (EditText) findViewById(R.id.otp_edit);
        otpContainer = (LinearLayout) findViewById(R.id.otp_container);

//        switchMobileNumberOTPLayout(false);

        mOTPProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
//                if (!isMobileNumberEntered) {
//                    mobileNumberStr = mMobileNumberEdit.getText().toString();
//                    if (mobileNumberStr.length() < 10) {
//                        EnrichUtils.showMessage(OTPVerificationActivity.this, "Please enter a valid mobile number");
//                    } else {
//                        verifyNow(mobileNumberStr);
//
////                        switchMobileNumberOTPLayout(true);
////                        isMobileNumberEntered = true;
//                    }
//                } else {
                otpStr = mOTPEdit.getText().toString();
                if (otpStr.length() < 4) {
                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please enter a valid OTP");
                } else {
                    if(isForLogin){
                        verifyOtpForLogin(mobileNumberStr, otpStr);
                    }else{
                        verifyOtp(otpStr);
                    }
                }
//                }
            }
        });

        mResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (userIdStr != null)
                    resendOtpNow();
            }
        });
    }

//    public void verifyNow (String mobNumber) {
//        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);
//        Call<ResponseDS<String>> otpModelCall = EnrichUtils.getRetrofitForEnrich().sendOtp(PostParams.init().add("Mobile", mobNumber).addPlatform());
//        otpModelCall.enqueue(new Callback<ResponseDS<String>>() {
//            @Override
//            public void onResponse (Call<ResponseDS<String>> call, Response<ResponseDS<String>> response) {
//                if (response.body().s == Constants.API_SUCCESS) {
//                    userIdStr = response.body().d;
//                    switchMobileNumberOTPLayout(true);
//                    isMobileNumberEntered = true;
//                }
//                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
//            }
//
//            @Override
//            public void onFailure (Call<ResponseDS<String>> call, Throwable t) {
//                if (t instanceof IOException) {
//                    Log.e("TAG", "Error : " + "No Internet Connection");
//                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
//                    EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
//                }
//            }
//        });
//    }

    public void verifyOtpForLogin (String mobileNumberStr, String otpNumber) {
        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);

//        EnrichUtils.log(EnrichURLs.ENRICH_HOST + EnrichURLs.VERIFY_NUMBER + "/" + userModel.getId() + "/" + otpNumber);

        Call<SingleResponseModel<UserModel>> userModelCall = EnrichUtils.getRetrofitForEnrich().verifyOtpForLogin(EnrichURLs.ENRICH_HOST + EnrichURLs.VERIFY_OTP_LOGIN + "/" + mobileNumberStr + "/" + otpNumber);
        //PostParams.init().add("RegistrationId", userIdStr + "").add("OTP", otpNumber).addPlatform()
        userModelCall.enqueue(new Callback<SingleResponseModel<UserModel>>() {
            @Override
            public void onResponse (Call<SingleResponseModel<UserModel>> call, Response<SingleResponseModel<UserModel>> response) {
                if (response.code() == 200) {
                    if (response.body().status == 0) {
//                        EnrichUtils.showMessage(OTPVerificationActivity.this, response.body().message);

                        String userJson = EnrichUtils.newGson().toJson(response.body().data);
                        EnrichUtils.log(userJson);
                        SharedPreferenceStore.storeValue(OTPVerificationActivity.this, Constants.KEY_USER_MODEL, userJson);

                        Intent intent = new Intent(OTPVerificationActivity.this, StoreSelectorActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (response.body().message != null) {
                            EnrichUtils.showMessage(OTPVerificationActivity.this, response.body().message);
                        }
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(OTPVerificationActivity.this, "Invalid Headers.", Toast.LENGTH_SHORT).show();
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }

            @Override
            public void onFailure (Call<SingleResponseModel<UserModel>> call, Throwable t) {
//                Intent intent = new Intent(OTPVerificationActivity.this, RegisterProfileActivity.class);
//                intent.putExtra("MobileNumber", mobileNumberStr);
//                startActivity(intent);
//                if (t instanceof IOException) {
//                    Log.e("TAG", "Error : " + "No Internet Connection");
//                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
//                }
                EnrichUtils.log(t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }
        });
    }

    public void verifyOtp (String otpNumber) {
        final UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(OTPVerificationActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);

//        EnrichUtils.log(EnrichURLs.ENRICH_HOST + EnrichURLs.VERIFY_NUMBER + "/" + userModel.getId() + "/" + otpNumber);

        Call<SingleResponseModel<Boolean>> userModelCall = EnrichUtils.getRetrofitForEnrich().verifyOtp(EnrichURLs.ENRICH_HOST + EnrichURLs.VERIFY_NUMBER + "/" + userModel.getId() + "/" + otpNumber);
        //PostParams.init().add("RegistrationId", userIdStr + "").add("OTP", otpNumber).addPlatform()
        userModelCall.enqueue(new Callback<SingleResponseModel<Boolean>>() {
            @Override
            public void onResponse (Call<SingleResponseModel<Boolean>> call, Response<SingleResponseModel<Boolean>> response) {
                if (response.code() == 200) {
                    if (response.body().status == 0) {
//                        EnrichUtils.showMessage(OTPVerificationActivity.this, response.body().message);

                        Intent intent = new Intent(OTPVerificationActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (response.body().message != null) {
                            EnrichUtils.showMessage(OTPVerificationActivity.this, response.body().message);
                        }
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(OTPVerificationActivity.this, "Invalid Headers.", Toast.LENGTH_SHORT).show();
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }

            @Override
            public void onFailure (Call<SingleResponseModel<Boolean>> call, Throwable t) {
//                Intent intent = new Intent(OTPVerificationActivity.this, RegisterProfileActivity.class);
//                intent.putExtra("MobileNumber", mobileNumberStr);
//                startActivity(intent);
//                if (t instanceof IOException) {
//                    Log.e("TAG", "Error : " + "No Internet Connection");
//                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
//                }
                EnrichUtils.log(t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }
        });
    }

    class ResendOptReq {
        int RegistrationId;

        public ResendOptReq (int registrationId) {
            RegistrationId = registrationId;
        }
    }

    public void resendOtpNow () {
        final UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(OTPVerificationActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

        EnrichUtils.showProgressDialog(OTPVerificationActivity.this);

        Call<ResponseDS<Boolean>> otpModelCall = EnrichUtils.getRetrofit().resendOtp(EnrichUtils.newGson().toJsonTree(new ResendOptReq(Integer.parseInt(userIdStr))));
        otpModelCall.enqueue(new Callback<ResponseDS<Boolean>>() {
            @Override
            public void onResponse (Call<ResponseDS<Boolean>> call, Response<ResponseDS<Boolean>> response) {
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
            public void onFailure (Call<ResponseDS<Boolean>> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("TAG", "Error : " + "No Internet Connection");
                    EnrichUtils.showMessage(OTPVerificationActivity.this, "Please check Internet Connection");
                }
                EnrichUtils.cancelCurrentDialog(OTPVerificationActivity.this);
            }
        });
    }
}
