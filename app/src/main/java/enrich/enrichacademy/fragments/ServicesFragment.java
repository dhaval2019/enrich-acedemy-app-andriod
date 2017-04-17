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
    TextView serviceDate, serviceDay;
    LinearLayout serviceDateContainer;
    ImageView search;

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
        serviceDate = (TextView) rootView.findViewById(R.id.service_date);
        serviceDay = (TextView) rootView.findViewById(R.id.service_day);
        serviceDateContainer = (LinearLayout) rootView.findViewById(R.id.service_date_container);
        search = (ImageView) rootView.findViewById(R.id.search);

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
        if (servicesModels != null && servicesModels.length > 0 && topologyModels != null && topologyModels.length > 0) {
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
