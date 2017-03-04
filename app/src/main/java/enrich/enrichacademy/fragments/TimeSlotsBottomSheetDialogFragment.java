package enrich.enrichacademy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.TimeSlotsBottomSheetDialogAdapter;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;

/**
 * Created by Admin on 22-Feb-17.
 */

public class TimeSlotsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    RecyclerView mTimeSlotsRecyclerView;
    TextView mServiceName;
    BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener;
    String[] timeSlots;
    String title;
    TimeSlotsBottomSheetDialogAdapter adapter;

    public static TimeSlotsBottomSheetDialogFragment getInstance(String title, String[] timeSlots, BottomSheetListOnItemClickListener bottomSheetListOnItemClickListener) {
        TimeSlotsBottomSheetDialogFragment fragment = new TimeSlotsBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Title", title);
        bundle.putStringArray("TimeSlots", timeSlots);
        bundle.putSerializable("BottomSheetListOnItemClickListener", bottomSheetListOnItemClickListener);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("Title");
        timeSlots = getArguments().getStringArray("TimeSlots");
        bottomSheetListOnItemClickListener = (BottomSheetListOnItemClickListener) getArguments().getSerializable("BottomSheetListOnItemClickListener");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_slot_bottom_sheet, container, false);

        mServiceName = (TextView) rootView.findViewById(R.id.time_slot_service_name);
        mTimeSlotsRecyclerView = (RecyclerView) rootView.findViewById(R.id.time_slots_rv);

        mServiceName.setText(title);

        adapter = new TimeSlotsBottomSheetDialogAdapter(TimeSlotsBottomSheetDialogFragment.this.getActivity(), timeSlots, bottomSheetListOnItemClickListener, TimeSlotsBottomSheetDialogFragment.this);
        mTimeSlotsRecyclerView.setAdapter(adapter);
        mTimeSlotsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        return rootView;
    }
}
