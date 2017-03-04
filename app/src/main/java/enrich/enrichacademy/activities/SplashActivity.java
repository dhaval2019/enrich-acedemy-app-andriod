package enrich.enrichacademy.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(SplashActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

                if (userModel != null) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, RouteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
