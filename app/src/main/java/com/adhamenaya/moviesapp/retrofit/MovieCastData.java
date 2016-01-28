package com.adhamenaya.moviesapp.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AENAYA on 8/1/2015.
 */
public class MovieCastData {

    @SerializedName("cast_id")
    private int castId;
    private String character;
    private String creditId;
    @SerializedName("credit_id")
    private String id;
    private String name;
    private int order;
    @SerializedName("profile_path")
    private String profilePath;

    public MovieCastData() {
    }

    public MovieCastData(String id, String character, String name, String profilePath) {
        this.character = character;
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
    }

    public int getCastId() {
        return castId;
    }

    public String getCreditId() {
        return creditId;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCharacter() {
        return character;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
