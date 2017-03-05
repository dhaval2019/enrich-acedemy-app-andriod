package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.HistoryModel;
import enrich.enrichacademy.model.OrderModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CurrentHistoryAdapter extends RecyclerView.Adapter<CurrentHistoryAdapter.CurrentViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<OrderModel> list;

    public CurrentHistoryAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CurrentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.current_list_item, parent, false);
        return new CurrentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrentViewHolder holder, int position) {
        holder.name.setText(list.get(position).serviceName);
        holder.rate.setText("Rs. " + list.get(position).Amount);
        holder.time.setText(new SimpleDateFormat("dd/MM/yyyy").format(list.get(position).BookingDate));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        TextView name, rate, time;
        ImageView image;

        public CurrentViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.current_service_name);
            rate = (TextView) itemView.findViewById(R.id.current_service_rate);
            time = (TextView) itemView.findViewById(R.id.current_service_time);
            image = (ImageView) itemView.findViewById(R.id.current_services_list_image);
        }
    }
}
