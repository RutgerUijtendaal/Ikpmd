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

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.api.OmdbMovie;
import com.rutger.uijtendaal.ikpmd.api.OmdbSearchResponse;
import com.rutger.uijtendaal.ikpmd.api.OmdbService;

import java.io.IOException;
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

    private List<OmdbMovie> resultList = new ArrayList<>();

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
    public OmdbMovie getItem(int position) {
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
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<OmdbMovie> suggestions = new ArrayList<>(0);

                if(constraint != null && constraint.toString().length() > 5) {
                    Call<OmdbSearchResponse> call = mOmdbService.searchSuggestions(constraint.toString());
                    try {
                        // Filters run on worker thread so we can .execute() instead of waiting for a callback response
                        Log.d(TAG, "Register filter");
                        Response<OmdbSearchResponse> response = call.execute();
                        OmdbSearchResponse result = response.body();
                        if(result.getResponse()){
                            for(OmdbMovie r: result.getSearch()) {
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
                    resultList = (List<OmdbMovie>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public List<OmdbMovie> getSuggestions(String query) {
        Call<OmdbSearchResponse> call = mOmdbService.searchSuggestions(query);

        final List<OmdbMovie> suggestions = new ArrayList<>(0);
        Log.d(TAG, "Success Response");

        call.enqueue(new Callback<OmdbSearchResponse>() {
            @Override
            public void onResponse(Call<OmdbSearchResponse> call, Response<OmdbSearchResponse> response) {
                OmdbSearchResponse result = response.body();
                Log.d(TAG, "Success Response");
                if(result.getResponse()) {
                    for(OmdbMovie r: result.getSearch()) {
                        suggestions.add(r);
                    }
                }
            }

            @Override
            public void onFailure(Call<OmdbSearchResponse> call, Throwable t) {
                Log.e(TAG, "error in getting remote data" + t.getMessage());
            }
        });

        return suggestions;
    }
}
