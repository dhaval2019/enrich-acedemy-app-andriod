package enrich.enrichacademy.activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.fragments.CoursesFragment;
import enrich.enrichacademy.fragments.HistoryFragment;
import enrich.enrichacademy.fragments.ServicesFragment;

public class HomeActivity extends AppCompatActivity {

    private static final int SERVICES = 1;
    private static final int COURSES = 2;
    private static final int HISOTRY = 3;

    LinearLayout mServicesContainer, mCoursesContainer, mHistoryContainer;
    TextView mHistoryLabel, mCoursesLabel, mServicesLabel;
    ImageView mServicesImage, mCoursesImage, mHistoryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mServicesContainer = (LinearLayout) findViewById(R.id.services_container);
        mCoursesContainer = (LinearLayout) findViewById(R.id.courses_container);
        mHistoryContainer = (LinearLayout) findViewById(R.id.history_container);
        mServicesLabel = (TextView) findViewById(R.id.services_label);
        mCoursesLabel = (TextView) findViewById(R.id.courses_label);
        mHistoryLabel = (TextView) findViewById(R.id.history_label);
        mServicesImage = (ImageView) findViewById(R.id.services_image);
        mCoursesImage = (ImageView) findViewById(R.id.courses_image);
        mHistoryImage = (ImageView) findViewById(R.id.history_image);

        mServicesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFragment(SERVICES);
            }
        });

        mCoursesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFragment(COURSES);
            }
        });

        mHistoryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFragment(HISOTRY);
            }
        });

        selectFragment(SERVICES);
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame_container, fragment);
        fragmentTransaction.commit();
    }

    private void selectFragment(int fragment) {
        switch (fragment) {
            case SERVICES:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_selected);
                mCoursesImage.setImageResource(R.drawable.courses_unselected);
                mHistoryImage.setImageResource(R.drawable.history_unselected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.colorAccent));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.text_color));

                //ADD FRAGMENT
                addFragment(ServicesFragment.getInstance());
                break;
            case COURSES:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_unselected);
                mCoursesImage.setImageResource(R.drawable.courses_selected);
                mHistoryImage.setImageResource(R.drawable.history_unselected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.colorAccent));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.text_color));

                //ADD FRAGMENT
                addFragment(CoursesFragment.getInstance());
                break;
            case HISOTRY:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_unselected);
                mCoursesImage.setImageResource(R.drawable.courses_unselected);
                mHistoryImage.setImageResource(R.drawable.history_selected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.colorAccent));

                //ADD FRAGMENT
                addFragment(HistoryFragment.getInstance());
                break;
            default:
                break;
        }
    }

}
