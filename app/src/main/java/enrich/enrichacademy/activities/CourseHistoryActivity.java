package enrich.enrichacademy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

import enrich.enrichacademy.R;
import enrich.enrichacademy.adapters.CourseHistoryAdapter;
import enrich.enrichacademy.model.CourseApplicationModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseHistoryActivity extends AppCompatActivity {

    RecyclerView courseHistoryRecyclerView;
    ImageView courseBackButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_history);

        courseHistoryRecyclerView = (RecyclerView) findViewById(R.id.course_history_recycler_view);
        courseBackButton = (ImageView) findViewById(R.id.course_back_button);

        courseBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                finish();
            }
        });

        getCourseApplications();
    }

    private void getCourseApplications () {
        EnrichUtils.showProgressDialog(this);

        UserModel model = EnrichUtils.getUser(this);

        Call<ListResponseModel<CourseApplicationModel>> call = EnrichUtils.getRetrofit().getAppliedCourses(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_APPLIED_COURSES + model.Id);
        call.enqueue(new Callback<ListResponseModel<CourseApplicationModel>>() {
            @Override
            public void onResponse (Call<ListResponseModel<CourseApplicationModel>> call, Response<ListResponseModel<CourseApplicationModel>> response) {
                if (response.code() == 200) {
                    if (response.body().data.length != 0) {
                        setAdapter(new ArrayList<>(Arrays.asList(response.body().data)));
                    }
                }

                EnrichUtils.cancelCurrentDialog(CourseHistoryActivity.this);
            }

            @Override
            public void onFailure (Call<ListResponseModel<CourseApplicationModel>> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
                EnrichUtils.cancelCurrentDialog(CourseHistoryActivity.this);
            }
        });
    }

    private void setAdapter (ArrayList<CourseApplicationModel> list) {
        CourseHistoryAdapter adapter = new CourseHistoryAdapter(this, list);
        courseHistoryRecyclerView.setAdapter(adapter);
        courseHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

}
