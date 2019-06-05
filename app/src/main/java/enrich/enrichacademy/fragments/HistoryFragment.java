package enrich.enrichacademy.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.HomeActivity;
import enrich.enrichacademy.adapters.CurrentHistoryAdapter;
import enrich.enrichacademy.model.HistoryModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.OrderModel;
import enrich.enrichacademy.model.OrderResponseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.TopologyModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 21-Feb-17.
 */

@TargetApi(Build.VERSION_CODES.M)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class HistoryFragment extends Fragment {

    LinearLayout currentTab, pastTab;
    TextView currentLabel, currentSubLabel, pastLabel, pastSubLabel;
    RecyclerView historyRecyclerView;

    CurrentHistoryAdapter currentHistoryAdapter;

    ImageView historyBackButton;

//    ArrayList<OrderModel> orderModelsList;

    boolean fromOrderPage;

    public static HistoryFragment getInstance (boolean fromOrderPage) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putBoolean("FromOrderPage", fromOrderPage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromOrderPage = getArguments().getBoolean("FromOrderPage");
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        currentTab = (LinearLayout) rootView.findViewById(R.id.current_tab);
        pastTab = (LinearLayout) rootView.findViewById(R.id.past_tab);
        currentLabel = (TextView) rootView.findViewById(R.id.current_tab_label);
        currentSubLabel = (TextView) rootView.findViewById(R.id.current_tab_sub_label);
        pastLabel = (TextView) rootView.findViewById(R.id.past_tab_label);
        pastSubLabel = (TextView) rootView.findViewById(R.id.past_tab_sub_label);
        historyRecyclerView = (RecyclerView) rootView.findViewById(R.id.history_rv);
        historyBackButton = (ImageView) rootView.findViewById(R.id.history_back_button);

        switchTabs(true);

        historyBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (fromOrderPage) {
                    Intent intent = new Intent(HistoryFragment.this.getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    getActivity().finish();
                }
            }
        });

        currentTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                switchTabs(true);
            }
        });

        pastTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                switchTabs(false);
            }
        });

        return rootView;
    }

    private void switchTabs (boolean isCurrentSelected) {
        if (isCurrentSelected) {
            // BACKGROUND
            currentTab.setBackground(getActivity().getDrawable(R.drawable.history_current_bg_selected));
            pastTab.setBackground(getActivity().getDrawable(R.drawable.history_past_bg_unselected));

            // LABEL COLOR
            currentLabel.setTextColor(Color.parseColor("#ffffff"));
            currentSubLabel.setTextColor(Color.parseColor("#ffffff"));
            pastLabel.setTextColor(Color.parseColor("#37393c"));
            pastSubLabel.setTextColor(Color.parseColor("#37393c"));

            getCurrentAppointments();
//            getOrders();
        } else {
            // BACKGROUND
            currentTab.setBackground(getActivity().getDrawable(R.drawable.history_current_bg_unselected));
            pastTab.setBackground(getActivity().getDrawable(R.drawable.history_past_bg_selected));

            // LABEL COLOR
            currentLabel.setTextColor(Color.parseColor("#37393c"));
            currentSubLabel.setTextColor(Color.parseColor("#37393c"));
            pastLabel.setTextColor(Color.parseColor("#ffffff"));
            pastSubLabel.setTextColor(Color.parseColor("#ffffff"));

            getPastAppointments();
//            getOrders();
        }

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryFragment.this.getActivity()));
    }

    public void getCurrentAppointments () {
        EnrichUtils.showProgressDialog(HistoryFragment.this.getActivity());
        String id = EnrichUtils.getUser(HistoryFragment.this.getActivity()).Id;

        Call<ListResponseModel<OrderResponseModel>> call = EnrichUtils.getRetrofit().getCurrentAppointments(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_CURRENT_APPOINTMENTS + "/" + id);
        call.enqueue(new Callback<ListResponseModel<OrderResponseModel>>() {
            @Override
            public void onResponse (Call<ListResponseModel<OrderResponseModel>> call, Response<ListResponseModel<OrderResponseModel>> response) {
                if (response.code() == 200) {
                    if (response.body().status == 0) {
                        EnrichUtils.log("" + response.body().data.length);
                        List<OrderResponseModel> orderModelsList = Arrays.asList(response.body().data);
                        currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList, true, HistoryFragment.this);
                        historyRecyclerView.setAdapter(currentHistoryAdapter);
                    } else {
                        List<OrderResponseModel> orderModelsList = new ArrayList<OrderResponseModel>();
                        currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList, true, HistoryFragment.this);
                        historyRecyclerView.setAdapter(currentHistoryAdapter);
                    }
                } else {
                    List<OrderResponseModel> orderModelsList = new ArrayList<OrderResponseModel>();
                    currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList, true, HistoryFragment.this);
                    historyRecyclerView.setAdapter(currentHistoryAdapter);
                }
                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
            }

            @Override
            public void onFailure (Call<ListResponseModel<OrderResponseModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
            }
        });
    }

    public void getPastAppointments () {
        EnrichUtils.showProgressDialog(HistoryFragment.this.getActivity());
        String id = EnrichUtils.getUser(HistoryFragment.this.getActivity()).Id;

        Call<ListResponseModel<OrderResponseModel>> call = EnrichUtils.getRetrofit().getCurrentAppointments(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_PAST_APPOINTMENTS + "/" + id);
        call.enqueue(new Callback<ListResponseModel<OrderResponseModel>>() {
            @Override
            public void onResponse (Call<ListResponseModel<OrderResponseModel>> call, Response<ListResponseModel<OrderResponseModel>> response) {
                if (response.code() == 200) {
                    if (response.body().status == 0) {
                        EnrichUtils.log("" + response.body().data.length);
                        List<OrderResponseModel> orderModelsList = Arrays.asList(response.body().data);
                        currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList, false, HistoryFragment.this);
                        historyRecyclerView.setAdapter(currentHistoryAdapter);
                    } else {
                        List<OrderResponseModel> orderModelsList = new ArrayList<OrderResponseModel>();
                        currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList, false, HistoryFragment.this);
                        historyRecyclerView.setAdapter(currentHistoryAdapter);
                    }
                } else {
                    List<OrderResponseModel> orderModelsList = new ArrayList<OrderResponseModel>();
                    currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList, false, HistoryFragment.this);
                    historyRecyclerView.setAdapter(currentHistoryAdapter);
                }

                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
            }

            @Override
            public void onFailure (Call<ListResponseModel<OrderResponseModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
            }
        });
    }

//    public void getOrders () {
//        EnrichUtils.showProgressDialog(HistoryFragment.this.getActivity());
//        Call<OrderModel[]> call = EnrichUtils.getRetrofitForEnrich().getOrders(EnrichURLs.ENRICH_HOST + EnrichURLs.ORDER);
//        call.enqueue(new Callback<OrderModel[]>() {
//            @Override
//            public void onResponse (Call<OrderModel[]> call, Response<OrderModel[]> response) {
//                EnrichUtils.log("" + response.code());
//                orderModelsList = new ArrayList<OrderModel>();
//                for (int i = 0; i < response.body().length; i++) {
//                    orderModelsList.add(response.body()[i]);
//                }
//
//                fetchAllServices(orderModelsList);
//
//            }
//
//            @Override
//            public void onFailure (Call<OrderModel[]> call, Throwable t) {
//                EnrichUtils.log("" + t.getLocalizedMessage());
//                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
//            }
//        });
//    }

//    private void fetchAllServices (final ArrayList<OrderModel> orderModelsList) {
//        Call<ListResponseModel<ServicesModel>> getCourses = EnrichUtils.getRetrofit().getServices(EnrichURLs.ENRICH_HOST + EnrichURLs.SERVICES);
//        getCourses.enqueue(new Callback<ListResponseModel<ServicesModel>>() {
//            @Override
//            public void onResponse (Call<ListResponseModel<ServicesModel>> call, Response<ListResponseModel<ServicesModel>> response) {
//
////                ServicesModel[] servicesModels = response.body();
////                for (int i = 0; i < servicesModels.length; i++) {
////                    for (int j = 0; j < orderModelsList.size(); j++)
////                        if (orderModelsList.get(j).ServiceId == servicesModels[i].Id) {
////                            orderModelsList.get(j).serviceName = servicesModels[i].name;
////                        }
////                }
//                currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), orderModelsList);
//                historyRecyclerView.setAdapter(currentHistoryAdapter);
//
//                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
//            }
//
//            @Override
//            public void onFailure (Call<ListResponseModel<ServicesModel>> call, Throwable t) {
//                EnrichUtils.log("" + t.getLocalizedMessage());
//                EnrichUtils.cancelCurrentDialog(HistoryFragment.this.getActivity());
//            }
//        });
//    }

}
