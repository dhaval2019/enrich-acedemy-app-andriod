package enrich.enrichacademy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enrich.enrichacademy.fragments.CoursesTabFragment;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CoursePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<CourseModel> coursesList;
    ArrayList<CategoryModel> categoryModelsList;
    CategoryModel[] categoryModels;
    HashMap<Integer, ArrayList<CourseModel>> coursesMap;

    public CoursePagerAdapter(FragmentManager fm, CategoryModel[] categoryModels, ArrayList<CourseModel> coursesList, HashMap<Integer, ArrayList<CourseModel>> coursesMap) {
        super(fm);
        this.categoryModels = categoryModels;
        this.coursesList = coursesList;
        this.coursesMap = coursesMap;
        categoryModelsList = new ArrayList<>();
        initAllData(categoryModels);
    }

    private void initAllData(CategoryModel[] allServiceTopologyModels) {
        for (CategoryModel serviceTopologyModel : allServiceTopologyModels)
            if (coursesMap.containsKey(serviceTopologyModel.Id))
                categoryModelsList.add(serviceTopologyModel);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryModelsList.get(position).Name;
    }

    @Override
    public Fragment getItem(int position) {
        return CoursesTabFragment.getInstance(coursesMap.get(categoryModelsList.get(position).Id));
    }

    @Override
    public int getCount() {
        return categoryModelsList.size();
    }
}
