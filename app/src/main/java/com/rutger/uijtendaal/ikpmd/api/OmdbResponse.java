package com.rutger.uijtendaal.ikpmd.api;

import com.google.gson.annotations.SerializedName;

public class OmdbResponse {

    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("imdbID")
    private String imdbId;
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String posterUrl;

    public String getType() {
        return type;
    }

    public String getPosterUrl() {

        return posterUrl;
    }

    public String getImdbId() {

        return imdbId;
    }

    public String getYear() {

        return year;
    }

    public String getTitle() {
        return title;

    }

    @Override
    public String toString() {
        return title;
    }
}
