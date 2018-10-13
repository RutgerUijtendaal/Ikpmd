package com.rutger.uijtendaal.ikpmd.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OmdbResponseList {

    @SerializedName("Search")
    private List<OmdbResponse> search;
    @SerializedName("totalResults")
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<OmdbResponse> getSearch() {
        return search;
    }

    public void setSearch(List<OmdbResponse> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "OmdbResponseList{" +
                "totalResults='" + totalResults + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
