package enrich.enrichacademy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CartAdapter;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.CartModel;
import enrich.enrichacademy.model.OrderModel;
import enrich.enrichacademy.model.OrderRequestModel;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;
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
    protected void onCreate (Bundle savedInstanceState) {
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
        address.setText(application.getCart().get(application.getCartItemCount() - 1).storeModel.getAddress());

        StringBuilder timingStr = new StringBuilder();
        for (int i = 0; i < application.getCart().size(); i++) {
            timingStr.append(application.getCart().get(i).timeSlotModel.Start + ",");
        }

        time.setText(timingStr.toString().substring(0, timingStr.toString().length() - 1));
//        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(application.getCart().get(0).timeSlotModel.Start));
        date.setText("07/06/1991");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                onBackPressed();
            }
        });

        adapter = new CartAdapter(BookingSummaryActivity.this, application.getCart());
        bookingSummaryRecyclerview.setAdapter(adapter);
        bookingSummaryRecyclerview.setLayoutManager(new LinearLayoutManager(BookingSummaryActivity.this));

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                createOrder();
            }
        });
    }

    public void createOrder () {
        UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(BookingSummaryActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

        OrderRequestModel model = new OrderRequestModel();
        model.ServiceId = application.getCart().get(0).Id;
        model.ServiceName = application.getCart().get(0).Name;
        model.UserId = Integer.parseInt(userModel.Id);
        model.UserName = userModel.Name;
//        model.OrderDate = "" + new Date();
        model.Price = "" + application.getCart().get(0).Price;
        model.IsCOD = 1;
        model.TimeSlotId = application.getCart().get(0).timeSlotModel.Id;
        model.TimeSlotStart = application.getCart().get(0).timeSlotModel.Start;

        try {

            EnrichUtils.log(application.getCart().get(0).slotDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            Date bookingDate = dateFormat.parse(application.getCart().get(0).slotDate.substring(0, 10) + " " + application.getCart().get(0).timeSlotModel.Start);

            Timestamp timestamp = new Timestamp(bookingDate.getTime());

            model.BookDate = "" + timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EnrichUtils.log(EnrichUtils.newGson().toJson(model));


//        final OrderModel orderModel = new OrderModel();
//        orderModel.Amount = application.getTotalCartPrice();
//        orderModel.ServiceId = application.getCart().get(0).Id;
//        orderModel.BookingDate = new Date();
//        orderModel.BookingStatus = "Booked";
//        orderModel.IsCod = true;
//        orderModel.IsCancelled = false;
//        orderModel.PaymentMode = 1;

        Call<SingleResponseModel<Integer>> orderModelCall = EnrichUtils.getRetrofitForEnrich().createOrder(model);
        orderModelCall.enqueue(new Callback<SingleResponseModel<Integer>>() {
            @Override
            public void onResponse (Call<SingleResponseModel<Integer>> call, Response<SingleResponseModel<Integer>> response) {
                EnrichUtils.log("" + response.code());
                EnrichUtils.log("" + response.body().status);
                EnrichUtils.log("" + response.body().message);
                EnrichUtils.log("" + response.body().data);
                if (response.body().status == 0) {
                    Intent intent = new Intent(BookingSummaryActivity.this, OrderActivity.class);
                    intent.putExtra("OrderId", response.body().data);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    application.clearCart();
                } else {
                    EnrichUtils.showMessage(BookingSummaryActivity.this, "" + response.body().message);
                }

            }

            @Override
            public void onFailure (Call<SingleResponseModel<Integer>> call, Throwable t) {
                EnrichUtils.log("" + t);
            }
        });
    }

}
