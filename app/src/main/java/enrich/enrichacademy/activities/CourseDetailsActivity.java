package enrich.enrichacademy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {

    ImageView backButton;
    TextView overview, goals, requirements, evaluations, title, subTitle;
    Button apply;

    CourseModel courseModel;

    CategoryModel[] categoryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseModel = getIntent().getParcelableExtra("CourseModel");

        backButton = (ImageView) findViewById(R.id.back_button);
        overview = (TextView) findViewById(R.id.overview);
        goals = (TextView) findViewById(R.id.goals);
        requirements = (TextView) findViewById(R.id.requirements);
        evaluations = (TextView) findViewById(R.id.evaluations);
        title = (TextView) findViewById(R.id.toolbar_title);
        subTitle = (TextView) findViewById(R.id.toolbar_sub_title);
        apply = (Button) findViewById(R.id.course_apply_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnrichUtils.showMessage(CourseDetailsActivity.this, "Application successful");
            }
        });

        fetchAllCategories(courseModel);
    }

    private void fetchAllCategories(final CourseModel courseModel) {
        EnrichUtils.showProgressDialog(CourseDetailsActivity.this);

        Call<CategoryModel[]> getCourses = EnrichUtils.getRetrofit().getCategories(EnrichURLs.ENRICH_HOST + EnrichURLs.CATEGORIES);
        getCourses.enqueue(new Callback<CategoryModel[]>() {
            @Override
            public void onResponse(Call<CategoryModel[]> call, Response<CategoryModel[]> response) {
                categoryModels = response.body();
                String categoryName = null;

                for (int i = 0; i < categoryModels.length; i++) {
                    if (categoryModels[i].Id == courseModel.CategoryId)
                        categoryName = categoryModels[i].Name;
                }
                setData(courseModel, categoryName);
                EnrichUtils.cancelCurrentDialog(CourseDetailsActivity.this);
            }

            @Override
            public void onFailure(Call<CategoryModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CourseDetailsActivity.this);
            }
        });
    }

    public void setData(CourseModel courseModel, String categoryName) {
        title.setText(categoryName);
        subTitle.setText(courseModel.name);
        overview.setText(courseModel.description);
        goals.setText(courseModel.Goals);
        requirements.setText(courseModel.Prerequisites);
    }
}
