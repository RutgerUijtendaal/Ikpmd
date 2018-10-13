package com.rutger.uijtendaal.ikpmd.ui.addmovie.suggestions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.api.OmdbResponse;
import com.rutger.uijtendaal.ikpmd.api.OmdbResponseList;
import com.rutger.uijtendaal.ikpmd.api.OmdbService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = MoviesAutoCompleteAdapter.class.getName();

    private Context mContext;
    private OmdbService mOmdbService;

    private List<OmdbResponse> resultList = new ArrayList<>();

    @Inject
    public MoviesAutoCompleteAdapter(Context context, OmdbService omdbService) {
        mContext = context;
        mOmdbService = omdbService;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public OmdbResponse getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.suggestion_item, parent, false);
        }

        String movieTitle = getItem(position).getTitle();
        String movieYear = getItem(position).getYear();

        ((TextView) convertView.findViewById(R.id.suggestion_title)).setText(movieTitle + " (" + movieYear + ")");
//        ((TextView) convertView.findViewById(R.id.suggestion_year)).setText(getItem(position).getYear());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<OmdbResponse> suggestions = new ArrayList<>(0);

                if(constraint != null && constraint.toString().length() > 5) {
                    Call<OmdbResponseList> call = mOmdbService.searchSuggestions(constraint.toString());
                    try {
                        // Filters run on worker thread so we can .execute() instead of waiting for a callback response
                        Response<OmdbResponseList> response = call.execute();
                        OmdbResponseList result = response.body();
                        if(result.getResponse().equals("True")) {
                            for(OmdbResponse r: result.getSearch()) {
                                suggestions.add(r);
                            }
                        }
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    } catch(IOException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    resultList = (List<OmdbResponse>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public List<OmdbResponse> getSuggestions(String query) {
        Call<OmdbResponseList> call = mOmdbService.searchSuggestions(query);

        final List<OmdbResponse> suggestions = new ArrayList<>(0);

        call.enqueue(new Callback<OmdbResponseList>() {
            @Override
            public void onResponse(Call<OmdbResponseList> call, Response<OmdbResponseList> response) {
                OmdbResponseList result = response.body();
                Log.d(TAG, result.getResponse());
                if(result.getResponse().equals("True")) {
                    for(OmdbResponse r: result.getSearch()) {
                        suggestions.add(r);
                    }
                }
            }

            @Override
            public void onFailure(Call<OmdbResponseList> call, Throwable t) {
                Log.e(TAG, "error in getting remote data" + t.getMessage());
            }
        });

        return suggestions;
    }
}
