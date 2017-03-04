package enrich.enrichacademy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CartAdapter;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.CartModel;

public class BookingSummaryActivity extends AppCompatActivity {

    RecyclerView bookingSummaryRecyclerview;
    TextView totalItems, totalAmount, address, date, time;
    ImageView back;
    List<CartModel> list;
    CartAdapter adapter;

    EnrichAcademyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        application = (EnrichAcademyApplication) getApplicationContext();

        bookingSummaryRecyclerview = (RecyclerView) findViewById(R.id.booking_summary_rv);
        totalItems = (TextView) findViewById(R.id.booking_summary_total_items);
        totalAmount = (TextView) findViewById(R.id.booking_summary_total_amount);
        address = (TextView) findViewById(R.id.booking_summary_address);
        date = (TextView) findViewById(R.id.booking_summary_date);
        time = (TextView) findViewById(R.id.booking_summary_time);

        back = (ImageView) findViewById(R.id.back_button);

        totalItems.setText("Total Services: " + application.getCartItemCount());
        totalAmount.setText("Total Amount: Rs. " + application.getTotalCartPrice());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new CartAdapter(BookingSummaryActivity.this, application.getCart());
        bookingSummaryRecyclerview.setAdapter(adapter);
        bookingSummaryRecyclerview.setLayoutManager(new LinearLayoutManager(BookingSummaryActivity.this));
    }
}
