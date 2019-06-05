package enrich.enrichacademy.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.HomeActivity;
import enrich.enrichacademy.model.StoreModel;

import static enrich.enrichacademy.utils.Constants.storeModel;

/**
 * Created by varunbarve on 05/10/17.
 */

public class StoreTabAdapter extends RecyclerView.Adapter<StoreTabAdapter.StoreTabViewHolder> {

    Context context;
    ArrayList<StoreModel> list;
    LayoutInflater inflater;

    public StoreTabAdapter (Context context, ArrayList<StoreModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public StoreTabViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.store_tab_list_item, parent, false);
        return new StoreTabViewHolder(view);
    }

    @Override
    public void onBindViewHolder (StoreTabViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.address.setText(list.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                storeModel = list.get(position);
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("Store", list.get(position));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    class StoreTabViewHolder extends RecyclerView.ViewHolder {

        TextView name, address;

        public StoreTabViewHolder (View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.store_name);
            address = itemView.findViewById(R.id.store_address);
        }
    }

}
