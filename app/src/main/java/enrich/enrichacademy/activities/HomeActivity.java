package enrich.enrichacademy.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.fragments.CoursesFragment;
import enrich.enrichacademy.fragments.HistoryFragment;
import enrich.enrichacademy.fragments.ProfileFragment;
import enrich.enrichacademy.fragments.ServicesFragment;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.StoreModel;
import enrich.enrichacademy.utils.EnrichUtils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class HomeActivity extends AppCompatActivity {

    private static final int SERVICES = 1;
    private static final int COURSES = 2;
    private static final int HISTORY = 3;
    private static final int PROFILE = 4;

    LinearLayout mServicesContainer, mCoursesContainer, mHistoryContainer, mProfileContainer, mCartContainer;
    TextView mHistoryLabel, mCoursesLabel, mServicesLabel, mProfileLabel, mCartLabel, itemsInCart;
    ImageView mServicesImage, mCoursesImage, mHistoryImage, mProfileImage, mCartImage;

    EnrichAcademyApplication application;
    ArrayList<ServicesModel> list;

    int storeId;
    String storeName;
    StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        storeModel = getIntent().getParcelableExtra("Store");

        application = (EnrichAcademyApplication) getApplicationContext();
        list = application.getCart();

        mServicesContainer = findViewById(R.id.services_container);
        mCoursesContainer = findViewById(R.id.courses_container);
        mHistoryContainer = findViewById(R.id.history_container);
        mProfileContainer = findViewById(R.id.profile_container);
        mCartContainer = findViewById(R.id.cart_container);
        mServicesLabel = findViewById(R.id.services_label);
        mCoursesLabel = findViewById(R.id.courses_label);
        mHistoryLabel = findViewById(R.id.history_label);
        mProfileLabel = findViewById(R.id.profile_label);
        mServicesImage = findViewById(R.id.services_image);
        mCoursesImage = findViewById(R.id.courses_image);
        mHistoryImage = findViewById(R.id.history_image);
        mProfileImage = findViewById(R.id.profile_image);
        itemsInCart = findViewById(R.id.items_in_cart);

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
                selectFragment(HISTORY);
            }
        });

        mProfileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFragment(PROFILE);
            }
        });

        mCartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (application.isCartEmpty()) {
                    EnrichUtils.showMessage(HomeActivity.this, "Cart is Empty!");
                } else {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });

        selectFragment(SERVICES);
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame_container, fragment);
        fragmentTransaction.commit();
    }

    public void selectFragment(int fragment) {
        switch (fragment) {
            case SERVICES:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_selected);
                mCoursesImage.setImageResource(R.drawable.courses_unselected);
                mHistoryImage.setImageResource(R.drawable.history_unselected);
                mProfileImage.setImageResource(R.drawable.profile_unselected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.colorAccent));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.text_color));
                mProfileLabel.setTextColor(getResources().getColor(R.color.text_color));

                //ADD FRAGMENT
                addFragment(ServicesFragment.getInstance(storeModel));
                break;
            case COURSES:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_unselected);
                mCoursesImage.setImageResource(R.drawable.courses_selected);
                mHistoryImage.setImageResource(R.drawable.history_unselected);
                mProfileImage.setImageResource(R.drawable.profile_unselected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.colorAccent));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.text_color));
                mProfileLabel.setTextColor(getResources().getColor(R.color.text_color));

                //ADD FRAGMENT
                addFragment(CoursesFragment.getInstance());
                break;
            case HISTORY:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_unselected);
                mCoursesImage.setImageResource(R.drawable.courses_unselected);
                mHistoryImage.setImageResource(R.drawable.history_selected);
                mProfileImage.setImageResource(R.drawable.profile_unselected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.colorAccent));
                mProfileLabel.setTextColor(getResources().getColor(R.color.text_color));

                //ADD FRAGMENT
                addFragment(HistoryFragment.getInstance(false));
                break;
            case PROFILE:
                //CHANGE IMAGE
                mServicesImage.setImageResource(R.drawable.services_unselected);
                mCoursesImage.setImageResource(R.drawable.courses_unselected);
                mHistoryImage.setImageResource(R.drawable.history_unselected);
                mProfileImage.setImageResource(R.drawable.profile_selected);

                //CHANGE LABEL
                mServicesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mCoursesLabel.setTextColor(getResources().getColor(R.color.text_color));
                mHistoryLabel.setTextColor(getResources().getColor(R.color.text_color));
                mProfileLabel.setTextColor(getResources().getColor(R.color.colorAccent));

                //ADD FRAGMENT
                addFragment(ProfileFragment.getInstance());
                break;
            default:
                break;
        }
    }

    public void updateCart() {
        if (list.size() == 0 || list == null) {
            itemsInCart.setVisibility(View.GONE);
        } else {
            itemsInCart.setVisibility(View.VISIBLE);
            itemsInCart.setText("" + list.size());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }
}
