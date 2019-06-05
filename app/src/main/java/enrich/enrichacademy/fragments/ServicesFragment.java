package enrich.enrichacademy.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CartActivity;
import enrich.enrichacademy.activities.CourseListActivity;
import enrich.enrichacademy.activities.RegisterProfileActivity;
import enrich.enrichacademy.activities.SearchActivity;
import enrich.enrichacademy.activities.ServiceHistoryActivity;
import enrich.enrichacademy.activities.StoreSelectorActivity;
import enrich.enrichacademy.adapters.CoursePagerAdapter;
import enrich.enrichacademy.adapters.ServicesPagerAdapter;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.SearchModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.StoreModel;
import enrich.enrichacademy.model.TopologyModel;
import enrich.enrichacademy.utils.Constants;
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
    TextView serviceDate, serviceDay, noServicesAvailable;
    LinearLayout serviceDateContainer, storeLabelContainer, serviceContainer;
    ImageView search, serviceHistory;

    Date dateValue;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            serviceDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            serviceDay.setText(new SimpleDateFormat("EEEE").format(myCalendar.getTime()));

            dateValue = myCalendar.getTime();
        }
    };

    TextView toolbarTitle;

    StoreModel storeModel;

    public static ServicesFragment getInstance(StoreModel storeModel) {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        args.putParcelable("Store", storeModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeModel = getArguments().getParcelable("Store");

        if (storeModel == null) {
            storeModel = Constants.storeModel;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        application = (EnrichAcademyApplication) getActivity().getApplicationContext();

        mServicesTabHost = rootView.findViewById(R.id.services_tabs);
        mServicesViewPager = rootView.findViewById(R.id.services_view_pager);
        cart = rootView.findViewById(R.id.cart);
        serviceDate = rootView.findViewById(R.id.service_date);
        serviceDay = rootView.findViewById(R.id.service_day);
        serviceDateContainer = rootView.findViewById(R.id.service_date_container);
        search = rootView.findViewById(R.id.search);
        serviceHistory = rootView.findViewById(R.id.service_history);
        toolbarTitle = rootView.findViewById(R.id.toolbar_title);
        storeLabelContainer = rootView.findViewById(R.id.store_label_container);
        noServicesAvailable = rootView.findViewById(R.id.no_services_available);
        serviceContainer = rootView.findViewById(R.id.service_container);

        if (storeModel != null)
            toolbarTitle.setText(storeModel.getName());

        serviceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesFragment.this.getActivity(), ServiceHistoryActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServicesFragment.this.getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        serviceDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ServicesFragment.this.getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

        storeLabelContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesFragment.this.getActivity(), StoreSelectorActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        fetchAllTopology();

        return rootView;
    }

    private void fetchAllTopology() {
        EnrichUtils.showProgressDialog(ServicesFragment.this.getActivity());

        Call<ListResponseModel<TopologyModel>> getCourses = EnrichUtils.getRetrofit().getTopology(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_TOPOLOGIES);
        getCourses.enqueue(new Callback<ListResponseModel<TopologyModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<TopologyModel>> call, Response<ListResponseModel<TopologyModel>> response) {
                fetchAllServices(response.body().data);
            }

            @Override
            public void onFailure(Call<ListResponseModel<TopologyModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }
        });
    }

    private void fetchAllServices(final TopologyModel[] topologyModels) {
        EnrichUtils.log(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_ACTIVE_SERVICES_BY_STORE_ID + storeModel.getId());

        Call<ListResponseModel<ServicesModel>> getCourses = EnrichUtils.getRetrofit().getServices(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_ACTIVE_SERVICES_BY_STORE_ID + storeModel.getId());
        getCourses.enqueue(new Callback<ListResponseModel<ServicesModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<ServicesModel>> call, Response<ListResponseModel<ServicesModel>> response) {
                if (response.isSuccessful()) {
                    ServicesModel[] servicesModels = response.body().data;
                    if (servicesModels.length != 0) {
                        for (int i = 0; i < servicesModels.length; i++) {
                            servicesModels[i].storeModel = storeModel;
                        }
                        mapTopologiesAndServices(servicesModels, topologyModels);

                        noServicesAvailable.setVisibility(View.GONE);
                        serviceContainer.setVisibility(View.VISIBLE);
                    } else {
                        noServicesAvailable.setText("No services available at the moment. Please try changing the store or try again later.");
                        noServicesAvailable.setVisibility(View.VISIBLE);
                        serviceContainer.setVisibility(View.GONE);
                    }
                } else {
                    noServicesAvailable.setText("Something went wrong. Please try again later.");

                    noServicesAvailable.setVisibility(View.VISIBLE);
                    serviceContainer.setVisibility(View.GONE);
                }
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }

            @Override
            public void onFailure(Call<ListResponseModel<ServicesModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(ServicesFragment.this.getActivity());
            }
        });
    }

    public void mapTopologiesAndServices(ServicesModel[] servicesModels, TopologyModel[] topologyModels) {
        if (servicesModels != null && servicesModels.length > 0 && topologyModels != null && topologyModels.length > 0) {
            EnrichUtils.cancelCurrentDialog(getActivity());
            HashMap<Integer, ArrayList<ServicesModel>> servicesMap = new HashMap<>();
            for (int i = 0; i < servicesModels.length; i++) {
                ServicesModel servicesModelsTemp = servicesModels[i];
                TopologyModel topologyModelsTemp = getTopologyNameById(servicesModelsTemp.TopologyId, topologyModels);
                if (servicesMap.containsKey(topologyModelsTemp.Id)) {
                    EnrichUtils.log(" Service Id: " + servicesModelsTemp.Id + " Service Name: " + servicesModelsTemp.Name);
                    servicesMap.get(topologyModelsTemp.Id).add(servicesModelsTemp);
                } else {
                    ArrayList<ServicesModel> servicesItemsModels = new ArrayList<>();
                    servicesItemsModels.add(servicesModelsTemp);
                    servicesMap.put(topologyModelsTemp.Id, servicesItemsModels);
                    EnrichUtils.log(" Service Id " + servicesModelsTemp.Id + " Service Name " + servicesModelsTemp.Name);
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

    @Override
    public void onResume() {
        super.onResume();
        fetchAllTopology();
    }
}
