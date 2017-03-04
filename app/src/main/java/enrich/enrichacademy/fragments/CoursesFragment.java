package enrich.enrichacademy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CourseListActivity;

/**
 * Created by Admin on 21-Feb-17.
 */

public class CoursesFragment extends Fragment {

    RelativeLayout beautyCourses, hairCourses, makeUpCourses, salonCourses;

    public static CoursesFragment getInstance() {
        return new CoursesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);

        beautyCourses = (RelativeLayout) rootView.findViewById(R.id.beauty_courses);
        hairCourses = (RelativeLayout) rootView.findViewById(R.id.hair_courses);
        makeUpCourses = (RelativeLayout) rootView.findViewById(R.id.makeup_courses);
        salonCourses = (RelativeLayout) rootView.findViewById(R.id.salon_courses);

        beautyCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
                startActivity(intent);
            }
        });

        hairCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
                startActivity(intent);
            }
        });

        makeUpCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
                startActivity(intent);
            }
        });

        salonCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesFragment.this.getActivity(), CourseListActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
