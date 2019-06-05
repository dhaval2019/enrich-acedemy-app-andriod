package enrich.enrichacademy.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.SearchResultAdapter;
import enrich.enrichacademy.adapters.SearchSuggestionsAdapter;
import enrich.enrichacademy.adapters.SearchTagsAdapter;
import enrich.enrichacademy.fragments.ServicesFragment;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.SearchModel;
import enrich.enrichacademy.model.SearchModelSmall;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.TopologyModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    TextView searchDate, searchDay;
    LinearLayout searchDateContainer, searchSuggestionsContainer;
    public EditText searchEdit;
    ImageView clearSearch;
    RecyclerView searchTags, searchSuggestions, searchResultRecyclerView;
    TextView male, female;
    SwitchCompat genderSwitch;

    String[] tagsArray = {"Hair", "Beauty", "Face", "Skin", "Pedicure", "Manicure", "Head"};
    String[] suggestionsArray = {"Hair Cutting", "Hair Washing", "Hair Aligning", "Hair Straighten"};

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

            searchDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            searchDay.setText(new SimpleDateFormat("EEEE").format(myCalendar.getTime()));

            dateValue = myCalendar.getTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchDate = findViewById(R.id.search_date);
        searchDay = findViewById(R.id.search_day);
        searchDateContainer = findViewById(R.id.search_date_container);
        searchSuggestionsContainer = findViewById(R.id.search_suggestions_container);
        searchEdit = findViewById(R.id.search_edit);
        clearSearch = findViewById(R.id.clear_search);
        searchTags = findViewById(R.id.search_tags);
        searchSuggestions = findViewById(R.id.search_suggestions);
        male = findViewById(R.id.gender_male);
        female = findViewById(R.id.gender_female);
        genderSwitch = findViewById(R.id.gender_switch);
        searchResultRecyclerView = findViewById(R.id.search_result);

        searchDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        searchSuggestions.setAdapter(new SearchSuggestionsAdapter(SearchActivity.this, suggestionsArray));
        searchSuggestions.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchByKeyword(searchEdit.getText().toString());
                    return true;
                }
                return false;
            }
        });

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchEdit.getText().toString();
                if (search.isEmpty()) {
                    finish();
                } else {
                    searchEdit.setText("");
                }
            }
        });

        fetchAllTopology();
    }

    private void fetchAllTopology() {
        EnrichUtils.showProgressDialog(SearchActivity.this);

        Call<ListResponseModel<TopologyModel>> getCourses = EnrichUtils.getRetrofit().getTopology(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_TOPOLOGIES);
        getCourses.enqueue(new Callback<ListResponseModel<TopologyModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<TopologyModel>> call, Response<ListResponseModel<TopologyModel>> response) {
//                fetchAllServices(response.body().data);

                if (response.code() == 200) {
                    if (response.body().data.length != 0) {
                        ArrayList<TopologyModel> list = new ArrayList<TopologyModel>(Arrays.asList(response.body().data));
                        setTagsAdapter(list);
                    }
                }

                EnrichUtils.cancelCurrentDialog(SearchActivity.this);
            }

            @Override
            public void onFailure(Call<ListResponseModel<TopologyModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(SearchActivity.this);
            }
        });
    }

    private void setTagsAdapter(ArrayList<TopologyModel> list) {
        SearchTagsAdapter adapter = new SearchTagsAdapter(SearchActivity.this, list, this);
        searchTags.setAdapter(adapter);
        searchTags.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void searchByKeyword(final String keyword) {
        EnrichUtils.showProgressDialog(SearchActivity.this);
        Call<SingleResponseModel<SearchModel>> call = EnrichUtils.getRetrofit().searchByKeyword(EnrichURLs.ENRICH_HOST + EnrichURLs.SEARCH_BY_KEYWORD + keyword);
        call.enqueue(new Callback<SingleResponseModel<SearchModel>>() {
            @Override
            public void onResponse(Call<SingleResponseModel<SearchModel>> call, Response<SingleResponseModel<SearchModel>> response) {
                EnrichUtils.log("" + response.code());
                if (response.code() == 200) {
                    if (response.body().data.services == null && response.body().data.courses == null) {
                        searchSuggestionsContainer.setVisibility(View.VISIBLE);
                        searchResultRecyclerView.setVisibility(View.GONE);
                        EnrichUtils.showMessage(SearchActivity.this, "Couldn't find anything related to " + keyword);
                    } else {
                        searchSuggestionsContainer.setVisibility(View.GONE);
                        searchResultRecyclerView.setVisibility(View.VISIBLE);
                        shortenSearchModel(response.body().data);
                    }
                }

                EnrichUtils.cancelCurrentDialog(SearchActivity.this);
            }

            @Override
            public void onFailure(Call<SingleResponseModel<SearchModel>> call, Throwable t) {
                EnrichUtils.cancelCurrentDialog(SearchActivity.this);
            }
        });
    }

    private void shortenSearchModel(SearchModel models) {
        List<SearchModelSmall> list = new ArrayList<>();

        if (models.services != null) {
            if (!models.services.isEmpty()) {
                for (int i = 0; i < models.services.size(); i++) {
                    SearchModelSmall searchModelSmall = new SearchModelSmall();
                    searchModelSmall.name = models.services.get(i).Name;
                    searchModelSmall.id = models.services.get(i).Id;
                    searchModelSmall.description = models.services.get(i).Description;
                    searchModelSmall.dataType = models.services.get(i).dataType;
                    searchModelSmall.price = "" + models.services.get(i).Price;

                    list.add(searchModelSmall);
                }
            }
        }

        if (models.courses != null) {
            if (!models.courses.isEmpty()) {
                for (int i = 0; i < models.courses.size(); i++) {
                    SearchModelSmall searchModelSmall = new SearchModelSmall();
                    searchModelSmall.name = models.courses.get(i).Name;
                    searchModelSmall.id = models.courses.get(i).Id;
                    searchModelSmall.description = models.courses.get(i).Description;
                    searchModelSmall.dataType = models.courses.get(i).dataType;
                    searchModelSmall.price = "" + models.courses.get(i).price;

                    list.add(searchModelSmall);
                }
            }
        }
        SearchResultAdapter adapter = new SearchResultAdapter(getSupportFragmentManager(), this, list);
        searchResultRecyclerView.setAdapter(adapter);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
