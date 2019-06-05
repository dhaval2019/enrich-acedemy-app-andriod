package enrich.enrichacademy.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.HomeActivity;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.fragments.TimeSlotsBottomSheetDialogFragment;
import enrich.enrichacademy.model.DateTimeSlotModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TimeSlotModel;
import enrich.enrichacademy.model.TimingModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import enrich.enrichacademy.utils.TimeSlotComparator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 22-Feb-17.
 */

public class ServicesTabAdapter extends ExpandableRecyclerAdapter<ServicesModel, String, ServicesTabAdapter.ServicesParentViewHolder, ServicesTabAdapter.ServicesChildViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<ServicesModel> servicesList;
    FragmentManager fragmentManager;

    EnrichAcademyApplication application;
    HomeActivity activity;

    public ServicesTabAdapter(FragmentManager fragmentManager, Context context, @NonNull List<ServicesModel> parentItemList) {
        super(parentItemList);
        this.servicesList = parentItemList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentManager = fragmentManager;
        this.activity = (HomeActivity) context;

        application = (EnrichAcademyApplication) context.getApplicationContext();
    }

    @NonNull
    @Override
    public ServicesParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.services_tab_list_item, parentViewGroup, false);
        return new ServicesParentViewHolder(view);
    }

    @NonNull
    @Override
    public ServicesChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.services_tab_child_list_item, childViewGroup, false);
        return new ServicesChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ServicesParentViewHolder holder, int parentPosition, @NonNull ServicesModel parent) {
        ServicesModel servicesModel = servicesList.get(parentPosition);
        holder.name.setText(servicesModel.Name);

        if (servicesModel.DiscountPrice != 0) {
            if (servicesModel.DiscountPrice < servicesModel.Price) {
                holder.rate.setText("Rs. " + (int) servicesModel.DiscountPrice);
                holder.originalRate.setText("Rs. " + (int) servicesModel.Price);
            } else {
                holder.rate.setText("Rs. " + (int) servicesModel.Price);
                holder.originalRate.setVisibility(View.GONE);
            }
        } else {
            holder.rate.setText("Rs. " + (int) servicesModel.Price);
            holder.originalRate.setVisibility(View.GONE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindChildViewHolder(@NonNull ServicesChildViewHolder holder, final int parentPosition, final int childPosition, @NonNull String child) {
        final ServicesModel servicesModel = servicesList.get(parentPosition);
        holder.description.setText(servicesModel.Description);

        if (application.alreadyExist(servicesModel)) {
            holder.addButton.setEnabled(false);
            holder.addButton.setBackground(context.getDrawable(R.drawable.button_bg_round_rect_grey));
            holder.addButton.setText("ADDED");
        } else {
            holder.addButton.setEnabled(true);
            holder.addButton.setBackground(context.getDrawable(R.drawable.button_bg_round_rect_red));
            holder.addButton.setText("ADD");
        }

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimings(servicesModel, parentPosition, childPosition);
            }
        });
    }

    public void getTimings(final ServicesModel servicesModel, final int parentPosition, final int childPosition) {
        Call<ListResponseModel<DateTimeSlotModel>> timingCall = EnrichUtils.getRetrofit().getDateTimeSlots(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_DATE_TIME_SLOTS + "/" + servicesModel.Id);
        timingCall.enqueue(new Callback<ListResponseModel<DateTimeSlotModel>>() {
            @Override
            public void onResponse(Call<ListResponseModel<DateTimeSlotModel>> call, Response<ListResponseModel<DateTimeSlotModel>> response) {
                if (response.body().status == 0) {
                    DateTimeSlotModel[] dateTimeSlotModelTempArray = response.body().data;
//                    final ArrayList<TimeSlotModel> timeSlotModelTempList = new ArrayList<TimeSlotModel>();
                    if (dateTimeSlotModelTempArray.length > 0) {
                        for (int i = 0; i < dateTimeSlotModelTempArray.length; i++) {
                            Arrays.sort(dateTimeSlotModelTempArray[i].TimeSlots);
//                            for (int j = 0; j < dateTimeSlotModelTempArray[i].TimeSlots.length; j++) {
//                                timeSlotModelTempList.add(dateTimeSlotModelTempArray[i].TimeSlots[j]);
//                            }
//                            Collections.sort(timeSlotModelTempList, new Comparator<TimeSlotModel>() {
//                                @Override
//                                public int compare (TimeSlotModel o1, TimeSlotModel o2) {
//                                    return o2.Order - o1.Order;
//                                }
//                            });
                        }

                        BottomSheetDialogFragment bottomSheetDialogFragment = TimeSlotsBottomSheetDialogFragment.getInstance(servicesModel.Name, new ArrayList<>(Arrays.asList(dateTimeSlotModelTempArray)), new BottomSheetListOnItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onListItemSelected(int dateId, String slotDate, TimeSlotModel timeSlotModel) {
                                servicesModel.timeSlotModel = timeSlotModel;
                                servicesModel.slotDate = slotDate;
                                servicesModel.dateTimeSlotId = dateId;
                                application.addToCart(servicesModel);
                                activity.updateCart();
                                notifyChildChanged(parentPosition, childPosition);
                            }
                        }, servicesModel);
                        bottomSheetDialogFragment.show(fragmentManager, "Dialog");
                    } else {
                        Toast.makeText(context, "No Slots Available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "No Slots Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListResponseModel<DateTimeSlotModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
            }
        });
    }

    class ServicesParentViewHolder extends ParentViewHolder {

        TextView name, rate, originalRate;
        ImageView image;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public ServicesParentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.service_name);
            rate = itemView.findViewById(R.id.service_rate);
            image = itemView.findViewById(R.id.services_list_image);
            originalRate = itemView.findViewById(R.id.service_original_rate);
        }
    }

    class ServicesChildViewHolder extends ChildViewHolder {

        TextView description, addButton;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public ServicesChildViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.service_description);
            addButton = itemView.findViewById(R.id.add_button);
        }
    }
}
