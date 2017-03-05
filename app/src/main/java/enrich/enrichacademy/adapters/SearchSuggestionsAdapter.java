package enrich.enrichacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import enrich.enrichacademy.R;

/**
 * Created by Admin on 05-Mar-17.
 */

public class SearchSuggestionsAdapter extends RecyclerView.Adapter<SearchSuggestionsAdapter.SuggestionsViewHolder> {

    Context context;
    LayoutInflater inflater;
    String[] suggestions;

    public SearchSuggestionsAdapter(Context context, String[] suggestions) {
        this.context = context;
        this.suggestions = suggestions;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SearchSuggestionsAdapter.SuggestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_suggestions_list_item, parent, false);
        return new SearchSuggestionsAdapter.SuggestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchSuggestionsAdapter.SuggestionsViewHolder holder, int position) {
        holder.name.setText(suggestions[position]);
    }

    @Override
    public int getItemCount() {
        return suggestions.length;
    }

    class SuggestionsViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public SuggestionsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.suggestion_name);
        }
    }
}
