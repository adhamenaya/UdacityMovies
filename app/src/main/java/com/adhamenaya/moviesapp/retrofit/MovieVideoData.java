package com.adhamenaya.moviesapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AENAYA on 8/1/2015.
 */
public class MovieVideoData {

    private String id;
    private String iso_639_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public MovieVideoData() {
    }

    public MovieVideoData(String id, String name, String site) {
        this.id = id;
        this.name = name;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }

    public class VideoResult {
        private long id;
        @SerializedName("results")
        private List<MovieVideoData> mMovieVideoData;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<MovieVideoData> getVideoDataList() {
            return mMovieVideoData;
        }

        public void setReviewDataList(List<MovieVideoData> movieVideoData) {
            this.mMovieVideoData = movieVideoData;
        }
    }
}
