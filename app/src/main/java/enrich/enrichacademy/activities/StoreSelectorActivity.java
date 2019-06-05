package enrich.enrichacademy.activities;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.StorePagerAdapter;
import enrich.enrichacademy.model.CityModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.StoreModel;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.view.SlidingTabLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static enrich.enrichacademy.utils.EnrichURLs.ENRICH_HOST;
import static enrich.enrichacademy.utils.EnrichURLs.GET_CITIES;
import static enrich.enrichacademy.utils.EnrichURLs.GET_STORES;

public class StoreSelectorActivity extends AppCompatActivity {

    SlidingTabLayout mCityTabHost;
    ViewPager mStoresViewPager;
    StorePagerAdapter pagerAdapter;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_selector);

        mCityTabHost = findViewById(R.id.city_tabs);
        mStoresViewPager = findViewById(R.id.stores_view_pager);

        getAllCities();

    }

    public void getAllCities() {
        EnrichUtils.showProgressDialog(this);

        Call<ListResponseModel<CityModel>> call = EnrichUtils.getRetrofit().getCities(ENRICH_HOST + GET_CITIES);
        call.enqueue(new Callback<ListResponseModel<CityModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<CityModel>> call, Response<ListResponseModel<CityModel>> response) {
                if (response.code() == 200) {
                    if (response.body().data.length != 0) {
                        getStores(response.body().data);
                    }
                }
//                EnrichUtils.cancelCurrentDialog(StoreSelectorActivity.this);
            }

            @Override
            public void onFailure(Call<ListResponseModel<CityModel>> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(StoreSelectorActivity.this);
            }
        });
    }

    public void getStores(final CityModel[] cityModels) {
        Call<ListResponseModel<StoreModel>> call = EnrichUtils.getRetrofit().getStores(ENRICH_HOST + GET_STORES);
        call.enqueue(new Callback<ListResponseModel<StoreModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<StoreModel>> call, Response<ListResponseModel<StoreModel>> response) {
                if (response.code() == 200) {
                    if (response.body().data.length != 0) {
                        mapCitiesAndStores(response.body().data, cityModels);
                    }
                }
                EnrichUtils.cancelCurrentDialog(StoreSelectorActivity.this);
            }

            @Override
            public void onFailure(Call<ListResponseModel<StoreModel>> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(StoreSelectorActivity.this);
            }
        });
    }

    public void mapCitiesAndStores(StoreModel[] storeModels, CityModel[] cityModels) {
        if (storeModels != null && storeModels.length > 0 && cityModels != null && cityModels.length > 0) {
            HashMap<Integer, ArrayList<StoreModel>> storeMap = new HashMap<>();
            for (int i = 0; i < storeModels.length; i++) {
                StoreModel storeModelsTemp = storeModels[i];
                CityModel cityModelsTemp = getTopologyNameById(storeModelsTemp.getCityId(), cityModels);
                if (storeMap.containsKey(cityModelsTemp.getId())) {
                    EnrichUtils.log(" Service Id: " + storeModelsTemp.getId() + " Service Name: " + storeModelsTemp.getName());
                    storeMap.get(cityModelsTemp.getId()).add(storeModelsTemp);
                } else {
                    ArrayList<StoreModel> storeItemsModels = new ArrayList<>();
                    storeItemsModels.add(storeModelsTemp);
                    storeMap.put(cityModelsTemp.getId(), storeItemsModels);
                }
            }
            initAdapter(cityModels, storeMap);
            EnrichUtils.cancelCurrentDialog(this);
        }
    }

    private CityModel getTopologyNameById(int cityId, CityModel[] cityModels) {
        for (int i = 0; i < cityModels.length; i++) {
            if (cityModels[i].getId() == cityId)
                return cityModels[i];
        }
        return new CityModel(99, "Un-Categories");
    }

    public void initAdapter(CityModel[] cityModels, HashMap<Integer, ArrayList<StoreModel>> storeMap) {
        pagerAdapter = new StorePagerAdapter(getSupportFragmentManager(), cityModels, storeMap);
        mStoresViewPager.setAdapter(pagerAdapter);
        mCityTabHost.setViewPager(mStoresViewPager);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
