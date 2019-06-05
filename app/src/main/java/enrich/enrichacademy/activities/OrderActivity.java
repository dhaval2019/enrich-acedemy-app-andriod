package enrich.enrichacademy.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.OrderResponseModel;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    TextView orderId, bookingDate, paymentMode, status, amount;
    int orderIdVal;
    Button proceed;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderIdVal = getIntent().getIntExtra("OrderId", 0);

        orderId = findViewById(R.id.order_order_id);
        bookingDate = findViewById(R.id.order_booking_date);
        amount = findViewById(R.id.order_amount);
        status = findViewById(R.id.order_status);
        paymentMode = findViewById(R.id.order_payment_mode);
        proceed = findViewById(R.id.order_proceed_btn);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, ServiceHistoryActivity.class);
                intent.putExtra("FromOrderPage", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        getOrderById(orderIdVal);
    }

    public void getOrderById(int orderIdVal) {
        Call<SingleResponseModel<OrderResponseModel>> orderModelCall = EnrichUtils.getRetrofitForEnrich().getOrderById(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ORDER_BY_ID + "/" + orderIdVal);
        orderModelCall.enqueue(new Callback<SingleResponseModel<OrderResponseModel>>() {
            @Override
            public void onResponse(Call<SingleResponseModel<OrderResponseModel>> call, Response<SingleResponseModel<OrderResponseModel>> response) {
                if (response.body().status == 0) {
                    setData(response.body().data);
                } else {
                    EnrichUtils.showMessage(OrderActivity.this, "" + response.body().message);
                }
            }

            @Override
            public void onFailure(Call<SingleResponseModel<OrderResponseModel>> call, Throwable t) {
                EnrichUtils.log("" + t);
            }
        });
    }

    public void setData(OrderResponseModel orderModel) {
        orderId.setText("" + orderModel.Id);

        try {
            SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
            Date date = stringToDate.parse(orderModel.BookDate);

            SimpleDateFormat dateToString = new SimpleDateFormat("dd/MM/yyyy");

            bookingDate.setText(dateToString.format(date));
        } catch (ParseException e) {
            bookingDate.setText(orderModel.BookDate);
            e.printStackTrace();
        }

        amount.setText("Rs. " + orderModel.Price);
//        status.setText("" + orderModel.BookDate);
        paymentMode.setText("Cash");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
