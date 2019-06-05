package enrich.enrichacademy.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CoursePagerAdapter;
import enrich.enrichacademy.fragments.ServicesFragment;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseCategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TopologyModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import enrich.enrichacademy.view.SlidingTabLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CourseListActivity extends AppCompatActivity {

    SlidingTabLayout coursesTabs;
    ViewPager coursesViewPager;
    ArrayList<CourseModel> coursesList;
    CoursePagerAdapter pagerAdapter;

    ImageView backButton, search, courseHistory;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        coursesTabs = (SlidingTabLayout) findViewById(R.id.courses_tabs);
        coursesViewPager = (ViewPager) findViewById(R.id.courses_view_pager);
        backButton = (ImageView) findViewById(R.id.back_button);
        search = (ImageView) findViewById(R.id.search);
        courseHistory = (ImageView)findViewById(R.id.course_history);

        courseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(CourseListActivity.this, CourseHistoryActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(CourseListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                onBackPressed();
            }
        });

        fetchAllCategories();
//        fetchAllTopology();
    }

//    private void fetchAllTopology() {
//        EnrichUtils.showProgressDialog(CourseListActivity.this);
//
//        Call<ListResponseModel<TopologyModel>> getCourses = EnrichUtils.getRetrofit().getTopology(EnrichURLs.HOST + EnrichURLs.TOPOLOGY);
//        getCourses.enqueue(new Callback<ListResponseModel<TopologyModel>>() {
//            @Override
//            public void onResponse(Call<ListResponseModel<TopologyModel>> call, Response<ListResponseModel<TopologyModel>> response) {
//                fetchAllCourses(response.body().data);
//            }
//
//            @Override
//            public void onFailure(Call<ListResponseModel<TopologyModel>> call, Throwable t) {
//                EnrichUtils.log("" + t.getLocalizedMessage());
//                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
//            }
//        });
//    }

    private void fetchAllCategories () {
        EnrichUtils.showProgressDialog(CourseListActivity.this);

        Call<ListResponseModel<CourseCategoryModel>> getCourses = EnrichUtils.getRetrofit().getCategories(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_COURSE_CATEGORY);
        getCourses.enqueue(new Callback<ListResponseModel<CourseCategoryModel>>() {
            @Override
            public void onResponse (Call<ListResponseModel<CourseCategoryModel>> call, Response<ListResponseModel<CourseCategoryModel>> response) {

                EnrichUtils.log("" + response.code());

                fetchAllCourses(response.body().data);
            }

            @Override
            public void onFailure (Call<ListResponseModel<CourseCategoryModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
            }
        });
    }

    private void fetchAllCourses (final CourseCategoryModel[] categoryModels) {
        UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(CourseListActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

        Call<ListResponseModel<CourseModel>> getCourses = EnrichUtils.getRetrofit().getCourses(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_ALL_COURSES_BY_ID + "/" + userModel.getId());
        getCourses.enqueue(new Callback<ListResponseModel<CourseModel>>() {
            @Override
            public void onResponse (Call<ListResponseModel<CourseModel>> call, Response<ListResponseModel<CourseModel>> response) {

                EnrichUtils.log("" + response.code());
                coursesList = new ArrayList<>(Arrays.asList(response.body().data));

                mapCategoriesAndCourses(response.body().data, categoryModels);
                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
            }

            @Override
            public void onFailure (Call<ListResponseModel<CourseModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
            }
        });
    }

    public void mapCategoriesAndCourses (CourseModel[] courseModels, CourseCategoryModel[] topologyModels) {
        if (courseModels != null && topologyModels.length > 0 && courseModels != null && topologyModels.length > 0) {
            EnrichUtils.cancelCurrentDialog(this);
            HashMap<Integer, ArrayList<CourseModel>> courseMap = new HashMap<>();
            for (int i = 0; i < courseModels.length; i++) {
                CourseModel courseModelsTemp = courseModels[i];
                CourseCategoryModel topologyModelsTemp = getTopologyNameById(courseModelsTemp.CourseCategoryId, topologyModels);
                if (courseMap.containsKey(topologyModelsTemp.Id)) {
                    EnrichUtils.log(" Service Id " + courseModelsTemp.Id + " Service Name " + courseModelsTemp.Name);
                    courseMap.get(topologyModelsTemp.Id).add(courseModelsTemp);
                } else {
                    ArrayList<CourseModel> servicesItemsModels = new ArrayList<>();
                    servicesItemsModels.add(courseModelsTemp);
                    courseMap.put(topologyModelsTemp.Id, servicesItemsModels);
                    EnrichUtils.log(" Service Id " + courseModelsTemp.Id + " Service Name " + courseModelsTemp.Name);
                }
            }
            initAdapter(topologyModels, courseMap);
        }
    }

    private CourseCategoryModel getTopologyNameById (int topologyId, CourseCategoryModel[] topologyModels) {
        for (int i = 0; i < topologyModels.length; i++) {
            if (topologyModels[i].Id == topologyId)
                return topologyModels[i];
        }
        return new CourseCategoryModel(99, "Un-Categories");
    }

    public void initAdapter (CourseCategoryModel[] topologyModels, HashMap<Integer, ArrayList<CourseModel>> courseMap) {
        pagerAdapter = new CoursePagerAdapter(getSupportFragmentManager(), topologyModels, coursesList, courseMap);
        coursesViewPager.setAdapter(pagerAdapter);
        coursesTabs.setViewPager(coursesViewPager);
    }
}
