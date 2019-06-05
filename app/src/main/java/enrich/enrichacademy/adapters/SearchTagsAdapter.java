package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import enrich.enrichacademy.R;
import enrich.enrichacademy.activities.SearchActivity;
import enrich.enrichacademy.model.TopologyModel;

/**
 * Created by Admin on 05-Mar-17.
 */

public class SearchTagsAdapter extends RecyclerView.Adapter<SearchTagsAdapter.TagsViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<TopologyModel> list;
    SearchActivity activity;

    public SearchTagsAdapter (Context context, ArrayList<TopologyModel> list, SearchActivity activity) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public TagsViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_tags_list_item, parent, false);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder (final TagsViewHolder holder, int position) {
        holder.name.setText(list.get(position).Name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                activity.searchByKeyword(holder.name.getText().toString());
                activity.searchEdit.setText(holder.name.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    class TagsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView delete;

        public TagsViewHolder (View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tag_name);
            delete = (ImageView) itemView.findViewById(R.id.tag_delete);
        }
    }
}
