package com.adhamenaya.moviesapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AENAYA on 7/10/2015.
 */
public class MovieData {

    private Long id;
    @SerializedName("original_title")
    private String title;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private double voteAvg;
    @SerializedName("poster_path")
    private String posterPath;


    private Long budget;
    private Long revenue;
    private String homepage;
    private int runtime;

    @SerializedName("vote_count")
    private Long voteCount;

    private boolean fromFavourite = false;
    private List<MovieReviewData> mMovieReviewDataList;
    private List<MovieCastData> mMovieCastDataList;
    private List<MovieCrewData> mMovieCrewDataList;
    private List<MovieVideoData> mMovieVideoDataList;

    public List<MovieVideoData> getMovieVideoDataList() {
        return mMovieVideoDataList;
    }

    public void setMovieVideoDataList(List<MovieVideoData> mMovieVideoDataList) {
        this.mMovieVideoDataList = mMovieVideoDataList;
    }

    public boolean isFromFavourite() {
        return fromFavourite;
    }

    public void setFromFavourite(boolean fromFavourite) {
        this.fromFavourite = fromFavourite;
    }
    public List<MovieCastData> getMovieCastDataList() {
        return mMovieCastDataList;
    }

    public void setMovieCastDataList(List<MovieCastData> mMovieCastDataList) {
        this.mMovieCastDataList = mMovieCastDataList;
    }

    public List<MovieReviewData> getMovieReviewDataList() {
        return mMovieReviewDataList;
    }

    public void setMovieReviewDataList(List<MovieReviewData> mMovieReviewDataList) {
        this.mMovieReviewDataList = mMovieReviewDataList;
    }

    public List<MovieCrewData> getMovieCrewDataList() {
        return mMovieCrewDataList;
    }

    public void setMovieCrewDataList(List<MovieCrewData> mMovieCrewDataList) {
        this.mMovieCrewDataList = mMovieCrewDataList;
    }



    public MovieData() {
    }

    public MovieData(Long id, String title, String overview, String releaseDate, double voteAvg, String posterPath, Long budget, Long revenue, String homepage, int runtime, Long voteCount) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAvg = voteAvg;
        this.posterPath = posterPath;
        this.budget = budget;
        this.revenue = revenue;
        this.homepage = homepage;
        this.runtime = runtime;
        this.voteCount = voteCount;

    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAvg=" + voteAvg +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }

    public class MovieResult {

        private int page;
        @SerializedName("results")
        private List<MovieData> movies;
        @SerializedName("total_pages")
        private long totalPages;
        @SerializedName("total_results")
        private Long totalResults;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<MovieData> getMovies() {
            return movies;
        }

        public void setMovies(List<MovieData> movies) {
            this.movies = movies;
        }

        public long getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(long totalPages) {
            this.totalPages = totalPages;
        }

        public Long getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Long totalResults) {
            this.totalResults = totalResults;
        }
    }
}
