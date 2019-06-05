package enrich.enrichacademy.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CourseListActivity;
import enrich.enrichacademy.activities.SearchActivity;
import enrich.enrichacademy.adapters.CourseCategoryAdapter;
import enrich.enrichacademy.model.CourseCategoryModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 21-Feb-17.
 */

public class CoursesFragment extends Fragment {

    RelativeLayout beautyCourses, hairCourses, makeUpCourses, salonCourses;
    ImageView search;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView courseCategoryRecyclerView;

    CourseCategoryAdapter courseCategoryAdapter;

    public static CoursesFragment getInstance() {
        return new CoursesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);

        collapsingToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        assert collapsingToolbarLayout != null;
        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quicksand-Bold.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        collapsingToolbarLayout.setTitle(" ");

        courseCategoryRecyclerView = rootView.findViewById(R.id.course_category_recycler_view);

//        beautyCourses = rootView.findViewById(R.id.beauty_courses);
//        hairCourses = rootView.findViewById(R.id.hair_courses);
//        makeUpCourses = rootView.findViewById(R.id.makeup_courses);
//        salonCourses = rootView.findViewById(R.id.salon_courses);
        search = rootView.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesFragment.this.getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
//
//        beautyCourses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        hairCourses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        makeUpCourses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        salonCourses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
//                startActivity(intent);
//            }
//        });

        getCourseCategory();

        return rootView;
    }

    private void getCourseCategory() {
        EnrichUtils.showProgressDialog(this.getActivity());
        Call<ListResponseModel<CourseCategoryModel>> call = EnrichUtils.getRetrofit().getCategories(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_COURSE_CATEGORY);
        call.enqueue(new Callback<ListResponseModel<CourseCategoryModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<CourseCategoryModel>> call, Response<ListResponseModel<CourseCategoryModel>> response) {
                if (response.code() == 200) {
                    setCourseCategoryAdapter(new ArrayList<>(Arrays.asList(response.body().data)));
                }
                EnrichUtils.cancelCurrentDialog(CoursesFragment.this.getActivity());
            }

            @Override
            public void onFailure(Call<ListResponseModel<CourseCategoryModel>> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CoursesFragment.this.getActivity());
            }
        });
    }

    private void setCourseCategoryAdapter(ArrayList<CourseCategoryModel> list) {
        courseCategoryAdapter = new CourseCategoryAdapter(this.getContext(), list);
        courseCategoryRecyclerView.setAdapter(courseCategoryAdapter);
        courseCategoryRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
    }
}
