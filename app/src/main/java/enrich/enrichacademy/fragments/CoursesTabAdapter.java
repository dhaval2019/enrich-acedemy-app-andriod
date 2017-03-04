package enrich.enrichacademy.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CourseDetailsActivity;
import enrich.enrichacademy.model.CourseModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CoursesTabAdapter extends RecyclerView.Adapter<CoursesTabAdapter.CoursesViewHolder> {

    Context context;
    List<CourseModel> coursesList;
    LayoutInflater inflater;

    public CoursesTabAdapter(Context context, List<CourseModel> coursesList) {
        this.context = context;
        this.coursesList = coursesList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.courses_list_item, parent, false);
        return new CoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder, int position) {
        CourseModel courseModel = coursesList.get(position);
        holder.name.setText(courseModel.name);
        holder.rate.setText("Fee: Rs. " + courseModel.rate);
        holder.duration.setText("Duration: " + courseModel.duration);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    class CoursesViewHolder extends RecyclerView.ViewHolder {

        TextView name, rate, duration;
        ImageView image;

        public CoursesViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.course_name);
            rate = (TextView) itemView.findViewById(R.id.course_rate);
            duration = (TextView) itemView.findViewById(R.id.course_duration);
            image = (ImageView) itemView.findViewById(R.id.courses_image);
        }
    }
}
