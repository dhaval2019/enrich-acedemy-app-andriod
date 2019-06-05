package enrich.enrichacademy.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CartActivity;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.ServicesModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Activity activity;
    LayoutInflater inflater;
    ArrayList<ServicesModel> list;
    EnrichAcademyApplication application;

    public CartAdapter (Activity activity, ArrayList<ServicesModel> list) {
        this.activity = activity;
        this.list = list;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        application = (EnrichAcademyApplication) activity.getApplicationContext();
    }

    @Override
    public CartViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_list_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder (CartViewHolder holder, final int position) {
        final ServicesModel servicesModel = list.get(position);
        holder.name.setText(servicesModel.Name);
        holder.rate.setText("Rs. " + servicesModel.Price);

        try {
            String timeStr = servicesModel.timeSlotModel.Start;
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date time = null;
            time = sdf.parse(timeStr);
            DateFormat timeFormat = new SimpleDateFormat("hh a");

            String dateStr = servicesModel.slotDate;
            DateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = dateSDF.parse(dateStr);

            DateFormat dateFromat = new SimpleDateFormat("E, dd/MM/yyyy");

            holder.selectedTimeSlot.setText(dateFromat.format(date)+" @"+timeFormat.format(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                application.removeFromCart(servicesModel);
                notifyDataSetChanged();
                if(list.size()==0){
                    activity.onBackPressed();
                }
                CartActivity.updateItemAndAmount();
            }
        });
    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, rate, selectedTimeSlot;
        ImageView removeFromCart;

        public CartViewHolder (View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cart_name);
            rate = itemView.findViewById(R.id.cart_rate);
            removeFromCart = itemView.findViewById(R.id.cart_delete_button);
            selectedTimeSlot = itemView.findViewById(R.id.selected_time_slot);
        }
    }
}
