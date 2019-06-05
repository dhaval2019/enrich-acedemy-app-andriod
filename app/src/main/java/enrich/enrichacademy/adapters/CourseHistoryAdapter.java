package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import enrich.enrichacademy.R;
import enrich.enrichacademy.model.CourseApplicationModel;

import static android.view.View.GONE;

/**
 * Created by varunbarve on 04/10/17.
 */

public class CourseHistoryAdapter extends RecyclerView.Adapter<CourseHistoryAdapter.CourseHistoryViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<CourseApplicationModel> list;

    public CourseHistoryAdapter (Context context, ArrayList<CourseApplicationModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CourseHistoryViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_history_list_item, parent, false);
        return new CourseHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder (CourseHistoryViewHolder holder, int position) {
        holder.courseHistoryName.setText(list.get(position).CourseName);

        try {
            SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = toDate.parse(list.get(position).ApplicationDate);

            SimpleDateFormat toString = new SimpleDateFormat("hh:mm a, dd-MM-yyyy");

            holder.courseHistoryTime.setText(toString.format(parsedDate));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.courseHistoryTime.setVisibility(GONE);
        }

        if (list.get(position).CourseApplicationStatus == 1) {
            holder.courseHistoryStatus.setText("Applied");
        } else if (list.get(position).CourseApplicationStatus == 2) {
            holder.courseHistoryStatus.setText("Confirmed");
        } else {
            holder.courseHistoryStatus.setText("Pending Approval");
        }
    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    class CourseHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView courseHistoryName, courseHistoryStatus, courseHistoryTime;
        ImageView pay, cancel;

        public CourseHistoryViewHolder (View itemView) {
            super(itemView);

            courseHistoryName = (TextView) itemView.findViewById(R.id.course_history_name);
            courseHistoryStatus = (TextView) itemView.findViewById(R.id.course_history_status);
            courseHistoryTime = (TextView) itemView.findViewById(R.id.course_history_time);
            pay = (ImageView) itemView.findViewById(R.id.course_history_pay);
            cancel = (ImageView) itemView.findViewById(R.id.course_history_cancel);
        }
    }
}
