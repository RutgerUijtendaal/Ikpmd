package com.rutger.uijtendaal.ikpmd.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * OmdbSearchResponse class to hold the API response.
 *
 * API JSON Response layout:
 *
 * "Search": [
 *          {OmdbMovie],
 *          {OmdbMovie}
 *          ],
 * "totalResults" : number of matches found,
 * "Response" :  boolean if response has successful
 *
 */
public class OmdbSearchResponse {

    @SerializedName("Search")
    private List<OmdbMovie> search;
    @SerializedName("totalResults")
    private String totalResults;
    @SerializedName("Response")
    private Boolean response;

    /**
     *
     * @return
     */
    public List<OmdbMovie> getSearch() {
        return search;
    }

    public void setSearch(List<OmdbMovie> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public Boolean getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "OmdbSearchResponse{" +
                "totalResults='" + totalResults + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public void setResponse(String response) {
        this.response = response.equalsIgnoreCase("True");
    }
}
