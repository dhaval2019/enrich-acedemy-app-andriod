package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.HistoryModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CurrentHistoryAdapter extends RecyclerView.Adapter<CurrentHistoryAdapter.CurrentViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<HistoryModel> list;

    public CurrentHistoryAdapter(Context context, List<HistoryModel> list) {
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
        holder.name.setText(list.get(position).name);
        holder.rate.setText(list.get(position).rate);
        holder.time.setText(list.get(position).time);
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
