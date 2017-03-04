package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.CartModel;
import enrich.enrichacademy.model.ServicesModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<ServicesModel> list;

    public CartAdapter(Context context, ArrayList<ServicesModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_list_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);
        holder.rate.setText("Rs. " + list.get(position).DiscountPrice);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, rate;

        public CartViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.cart_name);
            rate = (TextView) itemView.findViewById(R.id.cart_rate);
        }
    }
}
