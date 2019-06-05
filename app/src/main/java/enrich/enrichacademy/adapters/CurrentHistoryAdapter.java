package enrich.enrichacademy.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enrich.enrichacademy.R;
import enrich.enrichacademy.fragments.HistoryFragment;
import enrich.enrichacademy.model.CancelRequestModel;
import enrich.enrichacademy.model.HistoryModel;
import enrich.enrichacademy.model.OrderModel;
import enrich.enrichacademy.model.OrderResponseModel;
import enrich.enrichacademy.utils.EnrichUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CurrentHistoryAdapter extends RecyclerView.Adapter<CurrentHistoryAdapter.CurrentViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<OrderResponseModel> list;
    boolean isCurrent;
    HistoryFragment historyFragment;

    public CurrentHistoryAdapter (Context context, List<OrderResponseModel> list, boolean isCurrent, HistoryFragment historyFragment) {
        this.context = context;
        this.list = list;
        this.isCurrent = isCurrent;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.historyFragment = historyFragment;
    }

    @Override
    public CurrentViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.current_list_item, parent, false);
        return new CurrentViewHolder(view);
    }

    @Override
    public void onBindViewHolder (final CurrentViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position).getId());

        holder.name.setText(list.get(position).ServiceName);
        holder.rate.setText("Rs. " + list.get(position).Price);

        if (isCurrent) {
            holder.currentCancel.setVisibility(View.VISIBLE);
        } else {
            holder.currentCancel.setVisibility(View.GONE);
        }

        try {
            SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = toDate.parse(list.get(position).BookDate);

            SimpleDateFormat toString = new SimpleDateFormat("hh:mm a, dd-MM-yyyy");

            holder.time.setText(toString.format(parsedDate));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.time.setVisibility(GONE);
        }

        holder.currentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                showCancelDialog((Integer) holder.itemView.getTag());
            }
        });
    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        TextView name, rate, time;
        ImageView image, currentCancel;

        public CurrentViewHolder (View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.current_service_name);
            rate = (TextView) itemView.findViewById(R.id.current_service_rate);
            time = (TextView) itemView.findViewById(R.id.current_service_time);
            image = (ImageView) itemView.findViewById(R.id.current_services_list_image);
            currentCancel = (ImageView) itemView.findViewById(R.id.current_cancel);
        }
    }

    public void cancelService (int id, String reason) {
        EnrichUtils.showProgressDialog((Activity) context);
        CancelRequestModel model = new CancelRequestModel();
        model.setId(id);
        model.setReason(reason);
        EnrichUtils.log("" + EnrichUtils.newGson().toJson(model));

        Call<Boolean> call = EnrichUtils.getRetrofitForEnrich().cancelOrder(model);
        call.enqueue(new Callback<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse (Call<Boolean> call, Response<Boolean> response) {
                EnrichUtils.log("" + response.body());
                if (response.body()) {
                    historyFragment.getCurrentAppointments();
                } else {
                    Toast.makeText(context, "Some thing went wrong.", Toast.LENGTH_SHORT).show();
                }
                EnrichUtils.cancelCurrentDialog((Activity) context);
            }

            @Override
            public void onFailure (Call<Boolean> call, Throwable t) {
                EnrichUtils.log(t.getLocalizedMessage());
            }
        });
    }

    public void showCancelDialog (final int id) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cancel_dialog);

        final RadioGroup reasonsGroup = (RadioGroup) dialog.findViewById(R.id.reasons_container);
        TextView yes = (TextView) dialog.findViewById(R.id.cancel_yes);
        TextView no = (TextView) dialog.findViewById(R.id.cancel_no);

        RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.radio_1);
        RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.radio_2);
        RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.radio_3);
        RadioButton rb4 = (RadioButton) dialog.findViewById(R.id.radio_4);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
        rb1.setTypeface(font);
        rb2.setTypeface(font);
        rb3.setTypeface(font);
        rb4.setTypeface(font);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                int reasonRadioId = reasonsGroup.getCheckedRadioButtonId();

                if (reasonRadioId != -1) {
                    String reasonStr = ((RadioButton) dialog.findViewById(reasonRadioId)).getText().toString();

                    cancelService(id, reasonStr);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Please select a reason", Toast.LENGTH_SHORT).show();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                dialog.dismiss();
            }
        });

//        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Android custom dialog example!");
//        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//        image.setImageResource(R.drawable.ic_launcher);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();
    }
}
