package com.rutger.uijtendaal.ikpmd.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Omdb REST API access point
 */
public interface OmdbService {
    @GET("?apikey=85758f1e")
    Call<OmdbSearchResponse> searchSuggestions(@Query("s") String query);
}
