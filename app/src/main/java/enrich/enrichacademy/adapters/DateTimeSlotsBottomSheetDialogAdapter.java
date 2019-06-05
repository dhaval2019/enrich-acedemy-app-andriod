package enrich.enrichacademy.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import enrich.enrichacademy.R;
import enrich.enrichacademy.fragments.TimeSlotsBottomSheetDialogFragment;
import enrich.enrichacademy.model.DateTimeSlotModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;

/**
 * Created by varunbarve on 21/07/17.
 */

public class DateTimeSlotsBottomSheetDialogAdapter extends SectionedRecyclerViewAdapter<DateTimeSlotsBottomSheetDialogAdapter.MainVH> {

    Context context;
    LayoutInflater inflater;
    ArrayList<DateTimeSlotModel> list;
    BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener;
    TimeSlotsBottomSheetDialogFragment timeSlotsBottomSheetDialogFragment;

    public DateTimeSlotsBottomSheetDialogAdapter (Context context, ArrayList<DateTimeSlotModel> list,BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener, TimeSlotsBottomSheetDialogFragment timeSlotsBottomSheetDialogFragment) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.bottomSheetListOnItemClickListener = bottomSheetListOnItemClickListener;
        this.timeSlotsBottomSheetDialogFragment = timeSlotsBottomSheetDialogFragment;
    }

    @Override
    public int getSectionCount () {
        return list.size();
    }

    @Override
    public int getItemCount (int section) {
        return list.get(section).TimeSlots.length;
    }

    @Override
    public void onBindHeaderViewHolder (MainVH holder, int section, boolean expanded) {
        try {
            String time = list.get(section).SlotDate;
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdf.parse(time);

            DateFormat dtsFromat = new SimpleDateFormat("EEEE, dd/MM/yyyy");

            holder.title.setText(dtsFromat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindFooterViewHolder (MainVH holder, int section) {

    }

    @Override
    public void onBindViewHolder (final MainVH holder, final int section, final int relativePosition, int absolutePosition) {
        try {
            String time = list.get(section).TimeSlots[relativePosition].Start;
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date = sdf.parse(time);

            DateFormat dtsFromat = new SimpleDateFormat("hh a");

            holder.name.setText(dtsFromat.format(date));

            if(list.get(section).TimeSlots[relativePosition].isSelected){
                holder.name.setBackgroundResource(R.drawable.timing_bg_red);
            }else{
                holder.name.setBackgroundResource(R.drawable.timing_bg_grey);
            }

            holder.name.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    holder.name.setTextColor(Color.parseColor("#ffffff"));
                    holder.name.setBackground(context.getDrawable(R.drawable.timing_bg_red));
                    bottomSheetListOnItemClickListener.onListItemSelected(list.get(section).DateTimeSlotId, list.get(section).SlotDate, list.get(section).TimeSlots[relativePosition]);
                    timeSlotsBottomSheetDialogFragment.dismiss();
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static class MainVH extends SectionedViewHolder {

        TextView name, title;

        public MainVH (View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.section_title);
            name = (TextView) itemView.findViewById(R.id.time_slot);
        }
    }

    @Override
    public MainVH onCreateViewHolder (ViewGroup parent, int viewType) {
        int layoutRes;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layoutRes = R.layout.section_label_layout;
                break;
            default:
                layoutRes = R.layout.time_slot_list_item;
                break;
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);
        return new MainVH(v);
    }
}
