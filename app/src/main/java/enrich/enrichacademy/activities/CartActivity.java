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

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CartAdapter;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.CartModel;
import enrich.enrichacademy.model.ServicesModel;

public class CartActivity extends AppCompatActivity {

    RecyclerView cart_rv;
    TextView totalItem, totalAmount;
    Button proceed;
    ImageView back;

    CartAdapter adapter;

    ArrayList<ServicesModel> list;

    EnrichAcademyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        application = (EnrichAcademyApplication) getApplicationContext();

        cart_rv = (RecyclerView) findViewById(R.id.cart_rv);
        totalItem = (TextView) findViewById(R.id.total_items);
        totalAmount = (TextView) findViewById(R.id.total_rate);
        proceed = (Button) findViewById(R.id.cart_proceed_btn);
        back = (ImageView) findViewById(R.id.back_button);

        totalItem.setText("Total Services: " + application.getCartItemCount());
        totalAmount.setText("Total: Rs. " + application.getTotalCartPrice());

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, BookingSummaryActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        list = application.getCart();

        adapter = new CartAdapter(CartActivity.this, list);
        cart_rv.setAdapter(adapter);
        cart_rv.setLayoutManager(new LinearLayoutManager(CartActivity.this));
    }
}
