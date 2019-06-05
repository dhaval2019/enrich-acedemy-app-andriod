package enrich.enrichacademy.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 11;

    String allRequiredPermissions[] = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECEIVE_SMS};

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.app_logo);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

//        EnrichUtils.log(encoded);

        invokePermission();

    }

    private void invokePermission () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> requestList = getNotGrantedPermissions();
            if (requestList.size() > 0) {
                requestPermission(requestList);
            } else {
                checkForUserUpdate();
            }
        } else {
            checkForUserUpdate();
        }
    }

    private ArrayList<String> getNotGrantedPermissions () {
        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        checkForPermission(notGrantedPermissions, allRequiredPermissions);
        return notGrantedPermissions;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkForPermission (ArrayList<String> permissionList, String... permissions) {
        for (String permission : permissions)
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                permissionList.add(permission);
    }

    private void requestPermission (ArrayList<String> allPermissions) {
        ActivityCompat.requestPermissions(this, allPermissions.toArray(new String[allPermissions.size()]), PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                boolean permissionDenied = false;
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionDenied = true;
                        checkForUserUpdate();
                        break;
                    }
                }
                if (permissionDenied) {
                    invokePermission();
                } else {
                    checkForUserUpdate();
                }
                break;
        }
    }

    private void checkForUserUpdate () {
        try{
            String userId = EnrichUtils.getUser(SplashActivity.this).Id;
            if (userId.isEmpty()) {
                switchToNextScreen();
            } else {
                getUserInfo(userId);
            }
        }catch (Exception e){
            switchToNextScreen();
        }
    }

    private void getUserInfo (final String userId) {
        Call<SingleResponseModel<UserModel>> call = EnrichUtils.getRetrofitForEnrich().getUserById(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_USER_BY_ID + "/" + userId);
        EnrichUtils.log(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_USER_BY_ID + "/" + userId);

        call.enqueue(new Callback<SingleResponseModel<UserModel>>() {
            @Override
            public void onResponse (Call<SingleResponseModel<UserModel>> call, Response<SingleResponseModel<UserModel>> response) {
                if (response.body().status == 0) {
                    String userInfo = EnrichUtils.newGson().toJson(response.body().data);
                    EnrichUtils.setUser(SplashActivity.this, userInfo);
                    switchToNextScreen();
                } else {
                    EnrichUtils.log(response.body().message);
                    switchToNextScreen();
                }
            }

            @Override
            public void onFailure (Call<SingleResponseModel<UserModel>> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
                switchToNextScreen();
            }
        });
    }

    private void switchToNextScreen () {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run () {
                try {
                    UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(SplashActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

                    if (userModel != null) {
                        //OneSignal.sendTag("Phone", EnrichUtils.getUser(SplashActivity.this).Mobile);
//                    if(userModel.getIsVerified()==1){
                        Intent intent = new Intent(SplashActivity.this, StoreSelectorActivity.class);
                        startActivity(intent);
                        finish();
//                    }else{
//                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
