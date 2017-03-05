package enrich.enrichacademy.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.util.ArrayList;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.fragments.TimeSlotsBottomSheetDialogFragment;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.TimingModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;
import enrich.enrichacademy.utils.EnrichURLs;
import enrich.enrichacademy.utils.EnrichUtils;
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

    public ServicesTabAdapter(FragmentManager fragmentManager, Context context, @NonNull List<ServicesModel> parentItemList) {
        super(parentItemList);
        this.servicesList = parentItemList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentManager = fragmentManager;

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
        holder.name.setText(servicesModel.name);
        holder.rate.setText("Rs. " + servicesModel.DiscountPrice);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindChildViewHolder(@NonNull ServicesChildViewHolder holder, final int parentPosition, final int childPosition, @NonNull String child) {
        final ServicesModel servicesModel = servicesList.get(parentPosition);
        holder.description.setText(servicesModel.description);

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

    class ServicesParentViewHolder extends ParentViewHolder {

        TextView name, rate;
        ImageView image;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public ServicesParentViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.service_name);
            rate = (TextView) itemView.findViewById(R.id.service_rate);
            image = (ImageView) itemView.findViewById(R.id.services_list_image);
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
            description = (TextView) itemView.findViewById(R.id.service_description);
            addButton = (TextView) itemView.findViewById(R.id.add_button);
        }
    }

    public void getTimings(final ServicesModel servicesModel, final int parentPosition, final int childPosition) {
        Call<TimingModel[]> timingCall = EnrichUtils.getRetrofit().getTimings(EnrichURLs.ENRICH_HOST + EnrichURLs.TIMINGS);
        timingCall.enqueue(new Callback<TimingModel[]>() {
            @Override
            public void onResponse(Call<TimingModel[]> call, Response<TimingModel[]> response) {

                EnrichUtils.log("" + response.code());
                final ArrayList<TimingModel> timingModelsTempList = new ArrayList<TimingModel>();
                TimingModel[] timingModelsTempArray = response.body();
                for (int i = 0; i < timingModelsTempArray.length; i++) {
                    if (servicesModel.Id == timingModelsTempArray[i].Type_Id) {
                        timingModelsTempList.add(timingModelsTempArray[i]);
                    }
                }

                BottomSheetDialogFragment bottomSheetDialogFragment = TimeSlotsBottomSheetDialogFragment.getInstance(servicesModel.name, timingModelsTempList, new BottomSheetListOnItemClickListener() {
                    @Override
                    public void onListItemSelected(int position) {
                        servicesModel.TimingModel = timingModelsTempList.get(position);
                        application.addToCart(servicesModel);
                        notifyChildChanged(parentPosition, childPosition);
                    }
                });
                bottomSheetDialogFragment.show(fragmentManager, "Dialog");
            }

            @Override
            public void onFailure(Call<TimingModel[]> call, Throwable t) {
                EnrichUtils.log("" + t.getLocalizedMessage());
            }
        });
    }
}
