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
import enrich.enrichacademy.model.CourseModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CoursesTabFragment extends Fragment {

    RecyclerView courseTabRecyclerView;
    List<CourseModel> courseList;
    CoursesTabAdapter adapter;

    public static CoursesTabFragment getInstance(List<CourseModel> courseList) {
        CoursesTabFragment fragment = new CoursesTabFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("CourseList", (ArrayList<? extends Parcelable>) courseList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseList = getArguments().getParcelableArrayList("CourseList");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services_tab, container, false);

        courseTabRecyclerView = (RecyclerView) rootView.findViewById(R.id.services_tab_rv);

        adapter = new CoursesTabAdapter(CoursesTabFragment.this.getActivity(), courseList);
        courseTabRecyclerView.setAdapter(adapter);
        courseTabRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        courseTabRecyclerView.setLayoutManager(new LinearLayoutManager(CoursesTabFragment.this.getActivity()));

        return rootView;
    }
}
