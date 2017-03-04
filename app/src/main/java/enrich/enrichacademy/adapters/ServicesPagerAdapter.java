package enrich.enrichacademy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enrich.enrichacademy.fragments.ServicesTabFragment;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TopologyModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class ServicesPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<TopologyModel> topologyModelsList;
    TopologyModel[] topologyModels;
    HashMap<Integer, ArrayList<ServicesModel>> serviceMap;

    public ServicesPagerAdapter(FragmentManager fm, TopologyModel[] topologyModels, HashMap<Integer, ArrayList<ServicesModel>> serviceMap) {
        super(fm);
        this.topologyModels = topologyModels;
        this.serviceMap = serviceMap;
        topologyModelsList = new ArrayList<>();
        initAllData(topologyModels);
    }

    private void initAllData(TopologyModel[] topologyModels) {
        for (TopologyModel serviceTopologyModel : topologyModels)
            if (serviceMap.containsKey(serviceTopologyModel.Id))
                topologyModelsList.add(serviceTopologyModel);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return topologyModelsList.get(position).Name;
    }

    @Override
    public Fragment getItem(int position) {
        return ServicesTabFragment.getInstance(serviceMap.get(topologyModelsList.get(position).Id));
    }

    @Override
    public int getCount() {
        return topologyModelsList.size();
    }
}
