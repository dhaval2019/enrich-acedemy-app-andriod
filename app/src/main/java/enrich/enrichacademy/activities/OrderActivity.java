package enrich.enrichacademy.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.OrderModel;

public class OrderActivity extends AppCompatActivity {

    TextView orderId, bookingDate, paymentMode, status, amount;
    OrderModel orderModel;
    Button proceed;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderModel = getIntent().getParcelableExtra("OrderModel");

        orderId = (TextView) findViewById(R.id.order_order_id);
        bookingDate = (TextView) findViewById(R.id.order_booking_date);
        amount = (TextView) findViewById(R.id.order_amount);
        status = (TextView) findViewById(R.id.order_status);
        paymentMode = (TextView) findViewById(R.id.order_payment_mode);
        proceed = (Button) findViewById(R.id.order_proceed_btn);

        orderId.setText("" + orderModel.Id);
        bookingDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(orderModel.BookingDate));
        amount.setText("" + orderModel.Amount);
        status.setText("" + orderModel.BookingStatus);
        paymentMode.setText("Cash");

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
