package enrich.enrichacademy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import enrich.enrichacademy.R;

@RequiresApi(api = Build.VERSION_CODES.M)
public class RouteActivity extends AppCompatActivity {

    TextView mBusinessBtn, mModelBtn;
    ImageView mBusinessModelBgImage;
    Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        mBusinessBtn = (TextView) findViewById(R.id.business_btn);
        mModelBtn = (TextView) findViewById(R.id.model_btn);
        mBusinessModelBgImage = (ImageView) findViewById(R.id.business_model_bg_image);
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);

        switchButtons(true);

        mBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchButtons(true);
            }
        });

        mModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchButtons(false);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RouteActivity.this, RegisterProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void switchButtons(boolean isBusinessSelected) {
        if (isBusinessSelected) {
            mBusinessModelBgImage.setImageResource(R.drawable.business_selected_business_model_bg);
            mBusinessBtn.setTextColor(Color.parseColor("#ffffff"));
            mModelBtn.setTextColor(Color.parseColor("#37393c"));
        } else {
            mBusinessModelBgImage.setImageResource(R.drawable.model_selected_business_model_bg);
            mModelBtn.setTextColor(Color.parseColor("#ffffff"));
            mBusinessBtn.setTextColor(Color.parseColor("#37393c"));
        }
    }
}
