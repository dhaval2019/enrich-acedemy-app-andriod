package enrich.enrichacademy.fragments;

import android.annotation.TargetApi;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CurrentHistoryAdapter;
import enrich.enrichacademy.model.HistoryModel;

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

    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        currentTab = (LinearLayout) rootView.findViewById(R.id.current_tab);
        pastTab = (LinearLayout) rootView.findViewById(R.id.past_tab);
        currentLabel = (TextView) rootView.findViewById(R.id.current_tab_label);
        currentSubLabel = (TextView) rootView.findViewById(R.id.current_tab_sub_label);
        pastLabel = (TextView) rootView.findViewById(R.id.past_tab_label);
        pastSubLabel = (TextView) rootView.findViewById(R.id.past_tab_sub_label);
        historyRecyclerView = (RecyclerView) rootView.findViewById(R.id.history_rv);

        switchTabs(true);

        currentTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTabs(true);
            }
        });

        pastTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTabs(false);
            }
        });

        return rootView;
    }

    private void switchTabs(boolean isCurrentSelected) {
        if (isCurrentSelected) {
            // BACKGROUND
            currentTab.setBackground(getActivity().getDrawable(R.drawable.history_current_bg_selected));
            pastTab.setBackground(getActivity().getDrawable(R.drawable.history_past_bg_unselected));

            // LABEL COLOR
            currentLabel.setTextColor(getActivity().getColor(R.color.white));
            currentSubLabel.setTextColor(getActivity().getColor(R.color.white));
            pastLabel.setTextColor(getActivity().getColor(R.color.text_color));
            pastSubLabel.setTextColor(getActivity().getColor(R.color.text_color));

            List<HistoryModel> historyModelList = new ArrayList<>();
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));

            currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), historyModelList);
            historyRecyclerView.setAdapter(currentHistoryAdapter);

        } else {
            // BACKGROUND
            currentTab.setBackground(getActivity().getDrawable(R.drawable.history_current_bg_unselected));
            pastTab.setBackground(getActivity().getDrawable(R.drawable.history_past_bg_selected));

            // LABEL COLOR
            currentLabel.setTextColor(getActivity().getColor(R.color.text_color));
            currentSubLabel.setTextColor(getActivity().getColor(R.color.text_color));
            pastLabel.setTextColor(getActivity().getColor(R.color.white));
            pastSubLabel.setTextColor(getActivity().getColor(R.color.white));

            List<HistoryModel> historyModelList = new ArrayList<>();
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));
            historyModelList.add(new HistoryModel("Hair Cut", "120", "10:00am, 23-12-2016"));

            currentHistoryAdapter = new CurrentHistoryAdapter(HistoryFragment.this.getActivity(), historyModelList);
            historyRecyclerView.setAdapter(currentHistoryAdapter);
        }

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryFragment.this.getActivity()));
    }
}
