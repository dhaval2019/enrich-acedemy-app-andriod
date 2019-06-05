package enrich.enrichacademy.activities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import enrich.enrichacademy.R;
import enrich.enrichacademy.fragments.HistoryFragment;

public class ServiceHistoryActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history);

        boolean fromOrderPage = getIntent().getBooleanExtra("FromOrderPage", false);

        addFragment(HistoryFragment.getInstance(fromOrderPage));
    }

    public void addFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.service_history_container, fragment);
        fragmentTransaction.commit();
    }
}
