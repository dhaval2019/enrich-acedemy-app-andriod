package enrich.enrichacademy.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseApplicationRequestModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.Constants;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.SharedPreferenceStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {

    ImageView backButton;
    TextView overview, goals, requirements, evaluations, title, subTitle;
    Button apply, pay;

    CourseModel courseModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseModel = getIntent().getParcelableExtra("CourseModel");

//        EnrichUtils.log(courseModel.Name + " IS APPLIED: " + courseModel.IsApplied);

        backButton = (ImageView) findViewById(R.id.back_button);
        overview = (TextView) findViewById(R.id.overview);
        goals = (TextView) findViewById(R.id.goals);
        requirements = (TextView) findViewById(R.id.requirements);
        evaluations = (TextView) findViewById(R.id.evaluations);
        title = (TextView) findViewById(R.id.toolbar_title);
        subTitle = (TextView) findViewById(R.id.toolbar_sub_title);
        apply = (Button) findViewById(R.id.course_apply_btn);
        pay = (Button) findViewById(R.id.pay_button_btn);

        if (courseModel.IsApplied == 1) {
            if (courseModel.CourseStatus == 1) {
                apply.setBackground(getResources().getDrawable(R.drawable.button_bg_curved_grey));
                apply.setText("Applied");
                apply.setEnabled(false);
            } else if (courseModel.CourseStatus == 2) {
                apply.setVisibility(View.GONE);
                pay.setVisibility(View.VISIBLE);

                // UNTIL PAYMENT GATEWAY IS IMPLEMENTED
                pay.setText("Confirmed");
                pay.setEnabled(false);
            }
        } else {
            pay.setVisibility(View.GONE);

            apply.setBackground(getResources().getDrawable(R.drawable.button_bg_curved_red));
            apply.setText("Apply");
            apply.setEnabled(true);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                onBackPressed();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                applyForCourse();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

            }
        });

        setData(courseModel, courseModel.Name);

//        fetchAllCategories(courseModel);
    }

    public void applyForCourse () {
        UserModel userModel = EnrichUtils.newGson().fromJson(SharedPreferenceStore.getValue(CourseDetailsActivity.this, Constants.KEY_USER_MODEL, ""), UserModel.class);

        CourseApplicationRequestModel model = new CourseApplicationRequestModel();

        model.CourseId = courseModel.Id;
        model.CourseName = courseModel.Name;
        model.UserName = userModel.Name;
        model.UserId = Integer.parseInt(userModel.Id);
        model.UserPhoneNumber = userModel.Mobile;

        EnrichUtils.log(EnrichUtils.newGson().toJson(model));

        Call<SingleResponseModel<Integer>> orderModelCall = EnrichUtils.getRetrofitForEnrich().applyForCourse(model);
        orderModelCall.enqueue(new Callback<SingleResponseModel<Integer>>() {
            @Override
            public void onResponse (Call<SingleResponseModel<Integer>> call, Response<SingleResponseModel<Integer>> response) {
                EnrichUtils.log("" + response.code());
                EnrichUtils.showMessage(CourseDetailsActivity.this, "Application Successfull.");
            }

            @Override
            public void onFailure (Call<SingleResponseModel<Integer>> call, Throwable t) {
                EnrichUtils.log("" + t);
            }
        });

    }

//    private void fetchAllCategories(final CourseModel courseModel) {
//        EnrichUtils.showProgressDialog(CourseDetailsActivity.this);
//
//        Call<CategoryModel[]> getCourses = EnrichUtils.getRetrofit().getCategories(EnrichURLs.ENRICH_HOST + EnrichURLs.CATEGORIES);
//        getCourses.enqueue(new Callback<CategoryModel[]>() {
//            @Override
//            public void onResponse(Call<CategoryModel[]> call, Response<CategoryModel[]> response) {
//                categoryModels = response.body();
//                String categoryName = null;
//
//                for (int i = 0; i < categoryModels.length; i++) {
//                    if (categoryModels[i].Id == courseModel.Id)
//                        categoryName = categoryModels[i].Name;
//                }
//                setData(courseModel, categoryName);
//                EnrichUtils.cancelCurrentDialog(CourseDetailsActivity.this);
//            }
//
//            @Override
//            public void onFailure(Call<CategoryModel[]> call, Throwable t) {
//                EnrichUtils.log("" + t.getLocalizedMessage());
//                EnrichUtils.cancelCurrentDialog(CourseDetailsActivity.this);
//            }
//        });
//    }

    public void setData (CourseModel courseModel, String categoryName) {
//        title.setText(categoryName);
        subTitle.setText(courseModel.Name);
        overview.setText(courseModel.Description);
        goals.setText(courseModel.Description);
        requirements.setText(courseModel.Description);
        if (courseModel.CourseStatus == 1) {
            apply.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_curved_grey));
            apply.setText("Applied");
            apply.setEnabled(false);
        } else if (courseModel.CourseStatus == 2) {
            apply.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_curved_red));
            apply.setText("Pay");
            apply.setEnabled(false);
        }
    }
}
