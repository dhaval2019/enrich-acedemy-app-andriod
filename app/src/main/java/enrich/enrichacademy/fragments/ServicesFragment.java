package enrich.enrichacademy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CartActivity;
import enrich.enrichacademy.activities.CourseListActivity;
import enrich.enrichacademy.adapters.CoursePagerAdapter;
import enrich.enrichacademy.adapters.ServicesPagerAdapter;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.view.SlidingTabLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 21-Feb-17.
 */

public class ServicesFragment extends Fragment {

    SlidingTabLayout mServicesTabHost;
    ViewPager mServicesViewPager;
    ArrayList<ServicesModel> servicesList;
    ArrayList<CategoryModel> categoryModels;
    ServicesPagerAdapter pagerAdapter;
    String[] topologyStrings = new String[]{"Hair", "Beauty", "Face", "Skin", "Feet", "Hand"};
    FloatingActionButton cart;

    public static ServicesFragment getInstance() {
        return new ServicesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        mServicesTabHost = (SlidingTabLayout) rootView.findViewById(R.id.services_tabs);
        mServicesViewPager = (ViewPager) rootView.findViewById(R.id.services_view_pager);
        cart = (FloatingActionButton) rootView.findViewById(R.id.cart);

//        servicesList = new ArrayList<>();
//        servicesList.add(new ServicesModel("Hair Cut", "120", "This course will train the student in al the aesthetic skin treatments that are offered in beauty industry worldwide and give them the requisite theory and practical knowledge to perform these services professionally."));
//        servicesList.add(new ServicesModel("Hair Cut", "120", "This course will train the student in al the aesthetic skin treatments that are offered in beauty industry worldwide and give them the requisite theory and practical knowledge to perform these services professionally."));
//        servicesList.add(new ServicesModel("Hair Cut", "120", "This course will train the student in al the aesthetic skin treatments that are offered in beauty industry worldwide and give them the requisite theory and practical knowledge to perform these services professionally."));
//        servicesList.add(new ServicesModel("Hair Cut", "120", "This course will train the student in al the aesthetic skin treatments that are offered in beauty industry worldwide and give them the requisite theory and practical knowledge to perform these services professionally."));
//        servicesList.add(new ServicesModel("Hair Cut", "120", "This course will train the student in al the aesthetic skin treatments that are offered in beauty industry worldwide and give them the requisite theory and practical knowledge to perform these services professionally."));
//        servicesList.add(new ServicesModel("Hair Cut", "120", "This course will train the student in al the aesthetic skin treatments that are offered in beauty industry worldwide and give them the requisite theory and practical knowledge to perform these services professionally."));
//
//        pagerAdapter = new ServicesPagerAdapter(getFragmentManager(), topologyStrings, servicesList);
//        mServicesViewPager.setAdapter(pagerAdapter);
//        mServicesTabHost.setViewPager(mServicesViewPager);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServicesFragment.this.getActivity(), CartActivity.class);
                getActivity().startActivity(intent);
            }
        });

        fetchAllCategories();

        return rootView;
    }

    private void fetchAllCategories() {
        EnrichUtils.showProgressDialog(ServicesFragment.this.getActivity());

        Call<CategoryModel[]> getCourses = EnrichUtils.getRetrofit().getCategories(EnrichURLs.ENRICH_HOST + EnrichURLs.CATEGORIES);
        getCourses.enqueue(new Callback<CategoryModel[]>() {
            @Override
            public void onResponse(Call<CategoryModel[]> call, Response<CategoryModel[]> response) {

                EnrichUtils.log("" + response.code());

                categoryModels = new ArrayList<CategoryModel>();
                CategoryModel[] categoryModelsTemp = response.body();

                for (int i = 0; i < categoryModelsTemp.length; i++) {
                    if (categoryModelsTemp[i].IsActive) {
                        categoryModels.add(categoryModelsTemp[i]);
                    }
                }
                fetchAllServices();
            }

            @Override
            public void onFailure(Call<CategoryModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }
        });
    }

    private void fetchAllServices() {
        Call<ServicesModel[]> getCourses = EnrichUtils.getRetrofit().getServices(EnrichURLs.ENRICH_HOST + EnrichURLs.SERVICES);
        getCourses.enqueue(new Callback<ServicesModel[]>() {
            @Override
            public void onResponse(Call<ServicesModel[]> call, Response<ServicesModel[]> response) {

                EnrichUtils.log("" + response.code());
                servicesList = new ArrayList<ServicesModel>(Arrays.asList(response.body()));

                pagerAdapter = new ServicesPagerAdapter(getFragmentManager(), categoryModels, servicesList);
                mServicesViewPager.setAdapter(pagerAdapter);
                mServicesTabHost.setViewPager(mServicesViewPager);

                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }

            @Override
            public void onFailure(Call<ServicesModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }
        });
    }

//    public void goToNextPage(ServicesModel[] servicesModels, CategoryModel[] categoryModels) {
//        if (servicesModels != null && categoryModels.length > 0 && servicesModels != null && categoryModels.length > 0) {
//            EnrichUtils.cancelCurrentDialog(getActivity());
//            HashMap<String, ArrayList<ServicesModel>> servicesMap = new HashMap<>();
//            for (int i = 0; i < servicesModels.length; i++) {
//                ServicesModel servicesModelsTemp = servicesModels[i];
//                CategoryModel categoryModelsTemp = getTopologyNameById(servicesModelsTemp.CategoryId, categoryModels);
//                if (servicesMap.containsKey(categoryModelsTemp.Name)) {
//                    EnrichUtils.log(" Service Id " + servicesModelsTemp.Id + " Service Name " + servicesModelsTemp.name);
//                    servicesMap.get(categoryModelsTemp.Name).add(servicesModelsTemp);
//                } else {
//                    ArrayList<ServicesModel> servicesItemsModels = new ArrayList<>();
//                    servicesItemsModels.add(servicesModelsTemp);
//                    servicesMap.put(categoryModelsTemp.Name, servicesItemsModels);
//                    EnrichUtils.log(" Service Id " + servicesModelsTemp.Id + " Service Name " + servicesModelsTemp.name);
//                }
//            }
//            initAdapter(servicesMap);
//        }
//    }
//
//    private CategoryModel getTopologyNameById(int topologyId, CategoryModel[] categoryModels) {
//        for (int i = 0; i < categoryModels.length; i++) {
//            if (categoryModels[i].Id == topologyId)
//                return categoryModels[i];
//        }
//        return new CategoryModel(99, "Un-Categories");
//    }
}
