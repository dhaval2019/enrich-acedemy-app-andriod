package enrich.enrichacademy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import enrich.enrichacademy.fragments.StoreTabFragment;
import enrich.enrichacademy.model.CityModel;
import enrich.enrichacademy.model.StoreModel;

/**
 * Created by varunbarve on 05/10/17.
 */

public class StorePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<CityModel> cityList;
    HashMap<Integer, ArrayList<StoreModel>> storeMap;

    public StorePagerAdapter (FragmentManager fm, CityModel[] cityModels, HashMap<Integer, ArrayList<StoreModel>> storeMap) {
        super(fm);
        this.storeMap = storeMap;
        cityList = new ArrayList<>();
        initAllData(cityModels);
    }

    private void initAllData (CityModel[] cityModels) {
        for (CityModel cityModel : cityModels)
            if (storeMap.containsKey(cityModel.getId()))
                cityList.add(cityModel);
    }

    @Override
    public CharSequence getPageTitle (int position) {
        return cityList.get(position).getCityName();
    }

    @Override
    public Fragment getItem (int position) {
        return StoreTabFragment.getInstance(storeMap.get(cityList.get(position).getId()));
    }

    @Override
    public int getCount () {
        return cityList.size();
    }
}
