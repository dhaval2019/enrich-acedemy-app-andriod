package enrich.enrichacademy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.fragments.ServicesTabFragment;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.ServicesModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class ServicesPagerAdapter extends FragmentStatePagerAdapter {

    List<ServicesModel> servicesList;
    ArrayList<CategoryModel> categoryModels;

    public ServicesPagerAdapter(FragmentManager fm, ArrayList<CategoryModel> categoryModels, List<ServicesModel> servicesList) {
        super(fm);
        this.categoryModels = categoryModels;
        this.servicesList = servicesList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryModels.get(position).Name;
    }

    @Override
    public Fragment getItem(int position) {
        return ServicesTabFragment.getInstance(servicesList);
    }

    @Override
    public int getCount() {
        return categoryModels.size();
    }
}
