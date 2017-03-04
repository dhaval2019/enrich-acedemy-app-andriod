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
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TopologyModel;
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
    ServicesPagerAdapter pagerAdapter;
    FloatingActionButton cart;
    EnrichAcademyApplication application;

    public static ServicesFragment getInstance() {
        return new ServicesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        application = (EnrichAcademyApplication) getActivity().getApplicationContext();

        mServicesTabHost = (SlidingTabLayout) rootView.findViewById(R.id.services_tabs);
        mServicesViewPager = (ViewPager) rootView.findViewById(R.id.services_view_pager);
        cart = (FloatingActionButton) rootView.findViewById(R.id.cart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (application.isCartEmpty()) {
                    EnrichUtils.showMessage(getActivity(), "Cart is Empty!");
                } else {
                    Intent intent = new Intent(ServicesFragment.this.getActivity(), CartActivity.class);
                    getActivity().startActivity(intent);
                }

            }
        });

        fetchAllTopology();

        return rootView;
    }

    private void fetchAllTopology() {
        EnrichUtils.showProgressDialog(ServicesFragment.this.getActivity());

        Call<TopologyModel[]> getCourses = EnrichUtils.getRetrofit().getTopology(EnrichURLs.ENRICH_HOST + EnrichURLs.TOPOLOGY);
        getCourses.enqueue(new Callback<TopologyModel[]>() {
            @Override
            public void onResponse(Call<TopologyModel[]> call, Response<TopologyModel[]> response) {
                fetchAllServices(response.body());
            }

            @Override
            public void onFailure(Call<TopologyModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }
        });
    }

    private void fetchAllServices(final TopologyModel[] topologyModels) {
        Call<ServicesModel[]> getCourses = EnrichUtils.getRetrofit().getServices(EnrichURLs.ENRICH_HOST + EnrichURLs.SERVICES);
        getCourses.enqueue(new Callback<ServicesModel[]>() {
            @Override
            public void onResponse(Call<ServicesModel[]> call, Response<ServicesModel[]> response) {
                mapTopologiesAndServices(response.body(), topologyModels);
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }

            @Override
            public void onFailure(Call<ServicesModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }
        });
    }

    public void mapTopologiesAndServices(ServicesModel[] servicesModels, TopologyModel[] topologyModels) {
        if (servicesModels != null && topologyModels.length > 0 && servicesModels != null && topologyModels.length > 0) {
            EnrichUtils.cancelCurrentDialog(getActivity());
            HashMap<Integer, ArrayList<ServicesModel>> servicesMap = new HashMap<>();
            for (int i = 0; i < servicesModels.length; i++) {
                ServicesModel servicesModelsTemp = servicesModels[i];
                TopologyModel topologyModelsTemp = getTopologyNameById(servicesModelsTemp.TopologyId, topologyModels);
                if (servicesMap.containsKey(topologyModelsTemp.Id)) {
                    EnrichUtils.log(" Service Id " + servicesModelsTemp.Id + " Service Name " + servicesModelsTemp.name);
                    servicesMap.get(topologyModelsTemp.Id).add(servicesModelsTemp);
                } else {
                    ArrayList<ServicesModel> servicesItemsModels = new ArrayList<>();
                    servicesItemsModels.add(servicesModelsTemp);
                    servicesMap.put(topologyModelsTemp.Id, servicesItemsModels);
                    EnrichUtils.log(" Service Id " + servicesModelsTemp.Id + " Service Name " + servicesModelsTemp.name);
                }
            }
            initAdapter(topologyModels, servicesMap);
        }
    }

    private TopologyModel getTopologyNameById(int topologyId, TopologyModel[] topologyModels) {
        for (int i = 0; i < topologyModels.length; i++) {
            if (topologyModels[i].Id == topologyId)
                return topologyModels[i];
        }
        return new TopologyModel(99, "Un-Categories");
    }

    public void initAdapter(TopologyModel[] topologyModels, HashMap<Integer, ArrayList<ServicesModel>> serviceMap) {
        pagerAdapter = new ServicesPagerAdapter(getFragmentManager(), topologyModels, serviceMap);
        mServicesViewPager.setAdapter(pagerAdapter);
        mServicesTabHost.setViewPager(mServicesViewPager);
    }
}
