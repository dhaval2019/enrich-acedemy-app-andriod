package enrich.enrichacademy.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.SearchSuggestionsAdapter;
import enrich.enrichacademy.adapters.SearchTagsAdapter;
import enrich.enrichacademy.fragments.ServicesFragment;

public class SearchActivity extends AppCompatActivity {

    TextView searchDate, searchDay;
    LinearLayout searchDateContainer;
    EditText searchEdit;
    ImageView clearSearch;
    RecyclerView searchTags, searchSuggestions;
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

        searchDate = (TextView) findViewById(R.id.search_date);
        searchDay = (TextView) findViewById(R.id.search_day);
        searchDateContainer = (LinearLayout) findViewById(R.id.search_date_container);
        searchEdit = (EditText) findViewById(R.id.search_edit);
        clearSearch = (ImageView) findViewById(R.id.clear_search);
        searchTags = (RecyclerView) findViewById(R.id.search_tags);
        searchSuggestions = (RecyclerView) findViewById(R.id.search_suggestions);
        male = (TextView) findViewById(R.id.gender_male);
        female = (TextView) findViewById(R.id.gender_female);
        genderSwitch = (SwitchCompat) findViewById(R.id.gender_switch);

        searchDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        searchTags.setAdapter(new SearchTagsAdapter(SearchActivity.this, tagsArray));
        searchTags.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        searchSuggestions.setAdapter(new SearchSuggestionsAdapter(SearchActivity.this, suggestionsArray));
        searchSuggestions.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

    }
}
