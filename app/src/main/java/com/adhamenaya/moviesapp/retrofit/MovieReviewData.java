package com.adhamenaya.moviesapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AENAYA on 8/1/2015.
 */
public class MovieReviewData {

    private String id;
    private String author;
    private String content;
    private String url;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MovieReviewData(){}

    public MovieReviewData(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public class ReviewResult{
        private long id;
        @SerializedName("results")
        private List<MovieReviewData> reviewDataList;
        @SerializedName("total_pages")
        private long totalPages;
        @SerializedName("total_results")
        private Long totalResults;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<MovieReviewData> getReviewDataList() {
            return reviewDataList;
        }

        public void setReviewDataList(List<MovieReviewData> reviewDataList) {
            this.reviewDataList = reviewDataList;
        }
    }
}
