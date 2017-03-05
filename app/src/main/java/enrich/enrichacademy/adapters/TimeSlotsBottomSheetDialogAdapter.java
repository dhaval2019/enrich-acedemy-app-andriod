package enrich.enrichacademy.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.fragments.TimeSlotsBottomSheetDialogFragment;
import enrich.enrichacademy.model.TimingModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;

/**
 * Created by Admin on 22-Feb-17.
 */

public class TimeSlotsBottomSheetDialogAdapter extends RecyclerView.Adapter<TimeSlotsBottomSheetDialogAdapter.TimeSlotsViewHolder> {

    Context context;
    ArrayList<TimingModel> timeSlots;
    LayoutInflater inflater;
    BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener;
    TimeSlotsBottomSheetDialogFragment timeSlotsBottomSheetDialogFragment;

    public TimeSlotsBottomSheetDialogAdapter(Context context, ArrayList<TimingModel> timeSlots, BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener, TimeSlotsBottomSheetDialogFragment timeSlotsBottomSheetDialogFragment) {
        this.context = context;
        this.timeSlots = timeSlots;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bottomSheetListOnItemClickListener = bottomSheetListOnItemClickListener;
        this.timeSlotsBottomSheetDialogFragment = timeSlotsBottomSheetDialogFragment;
    }

    @Override
    public TimeSlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.time_slot_list_item, parent, false);
        return new TimeSlotsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeSlotsViewHolder holder, final int position) {
        holder.timeSlot.setText(timeSlots.get(position).Timings);
        holder.timeSlot.setTag(position);
        holder.timeSlot.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                holder.timeSlot.setTextColor(Color.parseColor("#ffffff"));
                holder.timeSlot.setBackground(context.getDrawable(R.drawable.timing_bg_red));
                bottomSheetListOnItemClickListener.onListItemSelected(position);
                timeSlotsBottomSheetDialogFragment.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    class TimeSlotsViewHolder extends RecyclerView.ViewHolder {

        TextView timeSlot;

        public TimeSlotsViewHolder(View itemView) {
            super(itemView);

            timeSlot = (TextView) itemView.findViewById(R.id.time_slot);
        }
    }
}
