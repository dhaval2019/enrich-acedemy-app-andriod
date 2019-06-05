package enrich.enrichacademy.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.ServicesTabAdapter;
import enrich.enrichacademy.model.ServicesModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class ServicesTabFragment extends Fragment {

    RecyclerView servicesTabRecyclerView;
    List<ServicesModel> servicesList;
    ServicesTabAdapter adapter;

    public static ServicesTabFragment getInstance(List<ServicesModel> servicesList) {
        ServicesTabFragment fragment = new ServicesTabFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("ServiceModeList", (ArrayList<? extends Parcelable>) servicesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        servicesList = getArguments().getParcelableArrayList("ServiceModeList");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services_tab, container, false);

        servicesTabRecyclerView = rootView.findViewById(R.id.services_tab_rv);

        adapter = new ServicesTabAdapter(getFragmentManager(), ServicesTabFragment.this.getActivity(), servicesList);
        servicesTabRecyclerView.setAdapter(adapter);
        servicesTabRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        servicesTabRecyclerView.setLayoutManager(new LinearLayoutManager(ServicesTabFragment.this.getActivity()));

        return rootView;
    }
}
