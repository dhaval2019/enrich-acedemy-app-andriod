package enrich.enrichacademy.activities;

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
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
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

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        coursesTabs = (SlidingTabLayout) findViewById(R.id.courses_tabs);
        coursesViewPager = (ViewPager) findViewById(R.id.courses_view_pager);
        backButton = (ImageView) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fetchAllCategories();
    }

    private void fetchAllCategories() {
        EnrichUtils.showProgressDialog(CourseListActivity.this);

        Call<CategoryModel[]> getCourses = EnrichUtils.getRetrofit().getCategories(EnrichURLs.ENRICH_HOST + EnrichURLs.CATEGORIES);
        getCourses.enqueue(new Callback<CategoryModel[]>() {
            @Override
            public void onResponse(Call<CategoryModel[]> call, Response<CategoryModel[]> response) {

                EnrichUtils.log("" + response.code());

                fetchAllCourses(response.body());
            }

            @Override
            public void onFailure(Call<CategoryModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
            }
        });
    }

    private void fetchAllCourses(final CategoryModel[] categoryModels) {
        Call<CourseModel[]> getCourses = EnrichUtils.getRetrofit().getCourses(EnrichURLs.ENRICH_HOST + EnrichURLs.COURSES);
        getCourses.enqueue(new Callback<CourseModel[]>() {
            @Override
            public void onResponse(Call<CourseModel[]> call, Response<CourseModel[]> response) {

                EnrichUtils.log("" + response.code());
                coursesList = new ArrayList<>(Arrays.asList(response.body()));

                mapCategoriesAndCourses(response.body(), categoryModels);
                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
            }

            @Override
            public void onFailure(Call<CourseModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CourseListActivity.this);
            }
        });
    }

    public void mapCategoriesAndCourses(CourseModel[] courseModels, CategoryModel[] categoryModels) {
        if (courseModels != null && categoryModels.length > 0 && courseModels != null && categoryModels.length > 0) {
            EnrichUtils.cancelCurrentDialog(this);
            HashMap<Integer, ArrayList<CourseModel>> courseMap = new HashMap<>();
            for (int i = 0; i < courseModels.length; i++) {
                CourseModel courseModelsTemp = courseModels[i];
                CategoryModel categoryModelsTemp = getTopologyNameById(courseModelsTemp.CategoryId, categoryModels);
                if (courseMap.containsKey(categoryModelsTemp.Id)) {
                    EnrichUtils.log(" Service Id " + courseModelsTemp.Id + " Service Name " + courseModelsTemp.name);
                    courseMap.get(categoryModelsTemp.Id).add(courseModelsTemp);
                } else {
                    ArrayList<CourseModel> servicesItemsModels = new ArrayList<>();
                    servicesItemsModels.add(courseModelsTemp);
                    courseMap.put(categoryModelsTemp.Id, servicesItemsModels);
                    EnrichUtils.log(" Service Id " + courseModelsTemp.Id + " Service Name " + courseModelsTemp.name);
                }
            }
            initAdapter(categoryModels, courseMap);
        }
    }

    private CategoryModel getTopologyNameById(int topologyId, CategoryModel[] categoryModels) {
        for (int i = 0; i < categoryModels.length; i++) {
            if (categoryModels[i].Id == topologyId)
                return categoryModels[i];
        }
        return new CategoryModel(99, "Un-Categories");
    }

    public void initAdapter(CategoryModel[] categoryModels, HashMap<Integer, ArrayList<CourseModel>> courseMap) {
        pagerAdapter = new CoursePagerAdapter(getSupportFragmentManager(), categoryModels, coursesList, courseMap);
        coursesViewPager.setAdapter(pagerAdapter);
        coursesTabs.setViewPager(coursesViewPager);
    }
}
