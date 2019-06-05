package enrich.enrichacademy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import enrich.enrichacademy.fragments.CoursesTabFragment;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseCategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.TopologyModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CoursePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<CourseModel> coursesList;
    ArrayList<CourseCategoryModel> topologyModelsList;
    CourseCategoryModel[] topologyModels;
    HashMap<Integer, ArrayList<CourseModel>> coursesMap;

    public CoursePagerAdapter(FragmentManager fm, CourseCategoryModel[] topologyModels, ArrayList<CourseModel> coursesList, HashMap<Integer, ArrayList<CourseModel>> coursesMap) {
        super(fm);
        this.topologyModels = topologyModels;
        this.coursesList = coursesList;
        this.coursesMap = coursesMap;
        topologyModelsList = new ArrayList<>();
        initAllData(topologyModels);
    }

    private void initAllData(CourseCategoryModel[] topologyModels) {
        for (CourseCategoryModel serviceTopologyModel : topologyModels)
            if (coursesMap.containsKey(serviceTopologyModel.Id))
                topologyModelsList.add(serviceTopologyModel);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return topologyModelsList.get(position).Name;
    }

    @Override
    public Fragment getItem(int position) {
        return CoursesTabFragment.getInstance(coursesMap.get(topologyModelsList.get(position).Id));
    }

    @Override
    public int getCount() {
        return topologyModelsList.size();
    }
}
