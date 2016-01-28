package com.adhamenaya.moviesapp.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AENAYA on 7/10/2015.
 */
public class ParcelableMovieData implements Parcelable {

    private Long id;
    private String title;
    private String overview;
    private String releaseDate;
    private double voteAvg;
    private String posterPath;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.title);
        out.writeString(this.overview);
        out.writeString(this.releaseDate);
        out.writeDouble(this.voteAvg);
        out.writeString(this.releaseDate);
    }

    public ParcelableMovieData(MovieData movieData) {
        this.id = movieData.getId();
        this.title = movieData.getTitle();
        this.overview = movieData.getOverview();
        this.releaseDate = movieData.getReleaseDate();
        this.voteAvg = movieData.getVoteAvg();
        this.posterPath = movieData.getPosterPath();
    }

    public ParcelableMovieData(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAvg = in.readDouble();
        this.posterPath = in.readString();
    }

    public MovieData get() {
        MovieData movieData = new MovieData();
        movieData.setId(this.id);
        movieData.setTitle(this.title);
        movieData.setOverview(this.overview);
        movieData.setReleaseDate(this.releaseDate);
        movieData.setVoteAvg(this.voteAvg);
        movieData.setPosterPath(this.posterPath);
        return movieData;
    }

    public final static Parcelable.Creator<ParcelableMovieData> CREATOR = new Parcelable.Creator<ParcelableMovieData>() {
        @Override
        public ParcelableMovieData createFromParcel(Parcel source) {
            return new ParcelableMovieData(source);
        }

        @Override
        public ParcelableMovieData[] newArray(int size) {
            return new ParcelableMovieData[size];
        }
    };

}
