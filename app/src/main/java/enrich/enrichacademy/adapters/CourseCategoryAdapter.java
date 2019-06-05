package enrich.enrichacademy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.CourseListActivity;
import enrich.enrichacademy.model.CourseCategoryModel;

/**
 * Created by varunbarve on 12/12/17.
 */

public class CourseCategoryAdapter extends RecyclerView.Adapter<CourseCategoryAdapter.CourseCategoryViewHolder> {

    Context context;
    ArrayList<CourseCategoryModel> list;
    LayoutInflater inflater;

    public CourseCategoryAdapter(Context context, ArrayList<CourseCategoryModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CourseCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_category_list_item, parent, false);
        return new CourseCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseCategoryViewHolder holder, int position) {
        holder.courseCategoryName.setText(list.get(position).Name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CourseCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView courseCategoryName;

        public CourseCategoryViewHolder(View itemView) {
            super(itemView);
            courseCategoryName = itemView.findViewById(R.id.course_category_name);

        }
    }
}
