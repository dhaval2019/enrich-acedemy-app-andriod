package enrich.enrichacademy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CartAdapter;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.CartModel;
import enrich.enrichacademy.model.OrderModel;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingSummaryActivity extends AppCompatActivity {

    RecyclerView bookingSummaryRecyclerview;
    TextView totalItems, totalAmount, address, date, time;
    Button proceed;
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
        proceed = (Button) findViewById(R.id.booking_summary_proceed_btn);

        totalItems.setText("Total Services: " + application.getCartItemCount());
        totalAmount.setText("Total Amount: Rs. " + application.getTotalCartPrice());

        StringBuilder timingStr = new StringBuilder();
        for (int i = 0; i < application.getCart().size(); i++) {
            timingStr.append(application.getCart().get(i).TimingModel.Timings + ",");
        }

        time.setText(timingStr.toString().substring(0, timingStr.toString().length() - 1));
        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(application.getCart().get(0).TimingModel.FromDate));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new CartAdapter(BookingSummaryActivity.this, application.getCart());
        bookingSummaryRecyclerview.setAdapter(adapter);
        bookingSummaryRecyclerview.setLayoutManager(new LinearLayoutManager(BookingSummaryActivity.this));

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        });
    }

    public void createOrder() {
        final OrderModel orderModel = new OrderModel();
        orderModel.Amount = application.getTotalCartPrice();
        orderModel.ServiceId = application.getCart().get(0).Id;
        orderModel.BookingDate = new Date();
        orderModel.BookingStatus = "Booked";
        orderModel.IsCod = true;
        orderModel.IsCancelled = false;
        orderModel.PaymentMode = 1;

        Call<OrderModel> orderModelCall = EnrichUtils.getRetrofitForEnrich().createOrder(orderModel);
        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                EnrichUtils.log("" + response.code());
                Intent intent = new Intent(BookingSummaryActivity.this, OrderActivity.class);
                intent.putExtra("OrderModel", orderModel);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                EnrichUtils.log("" + t);
            }
        });
    }

}
