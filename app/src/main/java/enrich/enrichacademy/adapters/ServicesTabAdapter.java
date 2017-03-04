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

import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.application.EnrichAcademyApplication;
import enrich.enrichacademy.fragments.TimeSlotsBottomSheetDialogFragment;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.utils.BottomSheetListOnItemClickListener;

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
        holder.rate.setText(servicesModel.rate);
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
            holder.addButton.setBackground(context.getDrawable(R.drawable.button_bg_round_rect_red));
            holder.addButton.setText("ADD");
        }

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strings = new String[]{"10:00 am", "11:00 am", "12:00 am", "1:00 am", "2:00 am", "3:00 am", "4:00 am"};

                BottomSheetDialogFragment bottomSheetDialogFragment = TimeSlotsBottomSheetDialogFragment.getInstance(servicesModel.name, strings, new BottomSheetListOnItemClickListener() {
                    @Override
                    public void onListItemSelected(int position) {
                        application.addToCart(servicesModel);
                        notifyChildChanged(parentPosition, childPosition);
                    }
                });
                bottomSheetDialogFragment.show(fragmentManager, "Dialog");
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
}
