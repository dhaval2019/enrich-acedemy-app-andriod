package enrich.enrichacademy.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProfileActivity extends AppCompatActivity {

    Button registerSignupBtn;
    EditText userName, userEmailAddress, userPassword;
    TextView userDOB;
    RadioButton userGenderMale, userGenderFemale;

    String userNameStr, userEmailStr, userDOBStr, userPasswordStr, userGenderStr, userMobileNumber;

    Date dateValue;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            userDOB.setText(dayOfMonth + "/" + monthOfYear + 1 + "/" + year);
            userDOBStr = userDOB.getText().toString();

            dateValue = myCalendar.getTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);

        userMobileNumber = getIntent().getStringExtra("MobileNumber");

        registerSignupBtn = (Button) findViewById(R.id.register_signup_btn);
        userName = (EditText) findViewById(R.id.user_name);
        userEmailAddress = (EditText) findViewById(R.id.user_email);
        userDOB = (TextView) findViewById(R.id.user_dob);
        userPassword = (EditText) findViewById(R.id.user_password);
        userGenderMale = (RadioButton) findViewById(R.id.user_gender_male);
        userGenderFemale = (RadioButton) findViewById(R.id.user_gender_female);

        userGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userGenderStr = "Male";
            }
        });

        userGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userGenderStr = "Female";
            }
        });

        userDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        registerSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userNameStr = userName.getText().toString();
                userEmailStr = userEmailAddress.getText().toString();
                userPasswordStr = userPassword.getText().toString();

                if (userNameStr.isEmpty() || userEmailStr.isEmpty() || userPasswordStr.isEmpty() || userDOBStr == null || userGenderStr == null) {
                    EnrichUtils.showMessage(RegisterProfileActivity.this, "Please fill all the fields");
                } else {
                    UserModel userModel = new UserModel();
                    userModel.setDateOfBirth(userDOBStr);
                    userModel.setName(userNameStr);
                    userModel.setEmailAddress(userEmailStr);
                    userModel.setPassword(userPasswordStr);
                    userModel.setGender(userGenderStr);
                    userModel.setRegistrationCompleted(true);
                    userModel.setMobile(userMobileNumber);
                    userModel.setCreatedOn(new Date());

                    saveUser(userModel);
                }
            }
        });
    }

    public void saveUser(final UserModel userModel) {
        String userJson = EnrichUtils.newGson().toJson(userModel);

        Call<UserModel> saveUser = EnrichUtils.getRetrofitForEnrich().saveUserData(userModel);
        saveUser.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                EnrichUtils.log("" + response.code());
                String userJson = EnrichUtils.newGson().toJson(userModel);
                SharedPreferenceStore.storeValue(RegisterProfileActivity.this, Constants.KEY_USER_MODEL, userJson);

                Intent intent = new Intent(RegisterProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
            }
        });
    }

}
