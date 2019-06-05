package enrich.enrichacademy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.StoreTabAdapter;
import enrich.enrichacademy.model.StoreModel;

/**
 * Created by varunbarve on 05/10/17.
 */

public class StoreTabFragment extends Fragment {

    RecyclerView storeRecyclerView;
    ArrayList<StoreModel> list;
    StoreTabAdapter adapter;

    public static StoreTabFragment getInstance (ArrayList<StoreModel> storeList) {
        StoreTabFragment fragment = new StoreTabFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("StoreList", storeList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = getArguments().getParcelableArrayList("StoreList");
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_tab, container, false);

        storeRecyclerView = (RecyclerView) rootView.findViewById(R.id.store_recycler_view);

        adapter = new StoreTabAdapter(this.getActivity(), list);
        storeRecyclerView.setAdapter(adapter);
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return rootView;
    }

}
