package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.fragments.TimeSlotsBottomSheetDialogFragment;
import enrich.enrichacademy.model.DateTimeSlotModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.SearchModelSmall;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TimeSlotModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by varunbarve on 04/10/17.
 */

public class SearchResultAdapter extends ExpandableRecyclerAdapter<SearchModelSmall, String, SearchResultAdapter.SearchResultParentViewHolder, SearchResultAdapter.SearchResultChildViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<SearchModelSmall> searchList;
    FragmentManager fragmentManager;
    EnrichAcademyApplication application;

    /**
     * Primary constructor. Sets up {@link #mParentList} and {@link #mFlatItemList}.
     * <p>
     * Any changes to {@link #mParentList} should be made on the original instance, and notified via
     * {@link #notifyParentInserted(int)}
     * {@link #notifyParentRemoved(int)}
     * {@link #notifyParentChanged(int)}
     * {@link #notifyParentRangeInserted(int, int)}
     * {@link #notifyChildInserted(int, int)}
     * {@link #notifyChildRemoved(int, int)}
     * {@link #notifyChildChanged(int, int)}
     * methods and not the notify methods of RecyclerView.Adapter.
     *
     * @param parentList List of all parents to be displayed in the RecyclerView that this
     *                   adapter is linked to
     */
    public SearchResultAdapter (FragmentManager fragmentManager, Context context, @NonNull List<SearchModelSmall> parentList) {
        super(parentList);
        this.searchList = parentList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentManager = fragmentManager;

        application = (EnrichAcademyApplication) context.getApplicationContext();
    }

    @NonNull
    @Override
    public SearchResultParentViewHolder onCreateParentViewHolder (@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.search_result_parent, parentViewGroup, false);
        return new SearchResultParentViewHolder(view);
    }

    @NonNull
    @Override
    public SearchResultChildViewHolder onCreateChildViewHolder (@NonNull ViewGroup childViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.search_result_child, childViewGroup, false);
        return new SearchResultChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder (@NonNull SearchResultParentViewHolder holder, int parentPosition, @NonNull SearchModelSmall parent) {
        SearchModelSmall searchModelSmall = searchList.get(parentPosition);
        holder.name.setText(searchModelSmall.name);
        holder.rate.setText("Rs. " + searchModelSmall.price);

        if (searchModelSmall.dataType.equals("S")) {
            holder.image.setImageResource(R.drawable.services_default);
            holder.datatype.setText("Service");
        } else if (searchModelSmall.dataType.equals("C")) {
            holder.image.setImageResource(R.drawable.courses_default);
            holder.datatype.setText("Course");
        }
    }

    @Override
    public void onBindChildViewHolder (@NonNull SearchResultChildViewHolder holder, final int parentPosition, final int childPosition, @NonNull String child) {
        final SearchModelSmall modelSmall = searchList.get(parentPosition);
        holder.description.setText(modelSmall.description);

        if (modelSmall.dataType.equals("S")) {
            holder.addButton.setText("Add");
        } else if (modelSmall.dataType.equals("C")) {
            holder.addButton.setText("Details");
        }

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (modelSmall.dataType.equals("S")) {
                    ServicesModel servicesModel = new ServicesModel();
                    servicesModel.Id = modelSmall.id;
                    servicesModel.Name = modelSmall.name;
                    servicesModel.Description = modelSmall.description;
                    servicesModel.Price = Double.parseDouble(modelSmall.price);

                    getTimings(servicesModel, parentPosition, childPosition);
                } else if (modelSmall.dataType.equals("C")) {

                }
            }
        });
    }

    public void getTimings (final ServicesModel servicesModel, final int parentPosition, final int childPosition) {
        Call<ListResponseModel<DateTimeSlotModel>> timingCall = EnrichUtils.getRetrofit().getDateTimeSlots(EnrichURLs.ENRICH_HOST + EnrichURLs.GET_DATE_TIME_SLOTS + "/" + servicesModel.Id);
        timingCall.enqueue(new Callback<ListResponseModel<DateTimeSlotModel>>() {
            @Override
            public void onResponse (Call<ListResponseModel<DateTimeSlotModel>> call, Response<ListResponseModel<DateTimeSlotModel>> response) {
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
                            @Override
                            public void onListItemSelected (int dateId, String slotDate, TimeSlotModel timeSlotModel) {
                                servicesModel.timeSlotModel = timeSlotModel;
                                servicesModel.slotDate = slotDate;
                                servicesModel.dateTimeSlotId = dateId;
                                application.addToCart(servicesModel);
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
            public void onFailure (Call<ListResponseModel<DateTimeSlotModel>> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
            }
        });
    }

    class SearchResultParentViewHolder extends ParentViewHolder {

        TextView name, rate, datatype;
        ImageView image;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public SearchResultParentViewHolder (@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_result_name);
            rate = itemView.findViewById(R.id.search_result_rate);
            image = itemView.findViewById(R.id.search_result_image);
            datatype = itemView.findViewById(R.id.search_result_data_type);
        }
    }

    class SearchResultChildViewHolder extends ChildViewHolder {

        TextView description, addButton;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public SearchResultChildViewHolder (@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.search_description);
            addButton = itemView.findViewById(R.id.search_add_button);
        }
    }
}
