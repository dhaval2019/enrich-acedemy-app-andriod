package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import enrich.enrichacademy.R;

/**
 * Created by Admin on 05-Mar-17.
 */

public class SearchTagsAdapter extends RecyclerView.Adapter<SearchTagsAdapter.TagsViewHolder> {

    Context context;
    LayoutInflater inflater;
    String[] tags;

    public SearchTagsAdapter(Context context, String[] tags) {
        this.context = context;
        this.tags = tags;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_tags_list_item, parent, false);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagsViewHolder holder, int position) {
        holder.name.setText(tags[position]);
    }

    @Override
    public int getItemCount() {
        return tags.length;
    }

    class TagsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView delete;

        public TagsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tag_name);
            delete = (ImageView) itemView.findViewById(R.id.tag_delete);
        }
    }
}
