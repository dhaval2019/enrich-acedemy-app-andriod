package enrich.enrichacademy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.DateTimeSlotsBottomSheetDialogAdapter;
import enrich.enrichacademy.adapters.TimeSlotsBottomSheetDialogAdapter;
import enrich.enrichacademy.model.DateTimeSlotModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TimeSlotModel;
import enrich.enrichacademy.model.TimingModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;

/**
 * Created by Admin on 22-Feb-17.
 */

public class TimeSlotsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    RecyclerView mTimeSlotsRecyclerView;
    TextView mServiceName;
    BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener;
    ArrayList<DateTimeSlotModel> timeSlots;
    String title;
    TimeSlotsBottomSheetDialogAdapter adapter;

//    public static TimeSlotsBottomSheetDialogFragment getInstance(String title, ArrayList<TimeSlotModel> timeSlots, BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener) {
//        TimeSlotsBottomSheetDialogFragment fragment = new TimeSlotsBottomSheetDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("Title", title);
//        bundle.putParcelableArrayList("TimeSlots", timeSlots);
//        bundle.putSerializable("BottomSheetListOnItemClickListener", bottomSheetListOnItemClickListener);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static TimeSlotsBottomSheetDialogFragment getInstance(String title, ArrayList<DateTimeSlotModel> timeSlots, BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener, ServicesModel servicesModel) {
        TimeSlotsBottomSheetDialogFragment fragment = new TimeSlotsBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Title", title);
        bundle.putParcelableArrayList("TimeSlots", timeSlots);
        bundle.putSerializable("BottomSheetListOnItemClickListener", bottomSheetListOnItemClickListener);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("Title");
        timeSlots = getArguments().getParcelableArrayList("TimeSlots");
        bottomSheetListOnItemClickListener = (BottomSheetListOnItemClickListener) getArguments().getSerializable("BottomSheetListOnItemClickListener");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_slot_bottom_sheet, container, false);

        mServiceName = (TextView) rootView.findViewById(R.id.time_slot_service_name);
        mTimeSlotsRecyclerView = (RecyclerView) rootView.findViewById(R.id.time_slots_rv);

        mServiceName.setText("Slots for "+title);

//        adapter = new TimeSlotsBottomSheetDialogAdapter(TimeSlotsBottomSheetDialogFragment.this.getActivity(), timeSlots, bottomSheetListOnItemClickListener, TimeSlotsBottomSheetDialogFragment.this);
//        mTimeSlotsRecyclerView.setAdapter(adapter);
//        mTimeSlotsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        GridLayoutManager manager = new GridLayoutManager(this.getActivity(), 3);
        DateTimeSlotsBottomSheetDialogAdapter adapter = new DateTimeSlotsBottomSheetDialogAdapter(this.getActivity(), timeSlots,bottomSheetListOnItemClickListener, TimeSlotsBottomSheetDialogFragment.this);
        mTimeSlotsRecyclerView.setAdapter(adapter);
        mTimeSlotsRecyclerView.setLayoutManager(manager);
        adapter.setLayoutManager(manager);

        return rootView;
    }
}
