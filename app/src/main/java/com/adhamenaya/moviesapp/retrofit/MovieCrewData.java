package com.adhamenaya.moviesapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AENAYA on 8/1/2015.
 */
public class MovieCrewData {


    private String creditId;
    private String department;
    @SerializedName("credit_id")
    private String id;
    private String job;
    private String name;
    @SerializedName("profile_path")
    private String profilePath;

    public String getCreditId() {
        return creditId;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDepartment() {
        return department;
    }

    public String getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public MovieCrewData(){}

    public MovieCrewData(String id, String name,String job, String profilePath) {
        this.id = id;
        this.job = job;
        this.name = name;
        this.profilePath = profilePath;
    }

    public class CreditResult {

        private long id;
        @SerializedName("cast")
        private List<MovieCastData> castData;
        @SerializedName("crew")
        private List<MovieCrewData> crewData;

        public void setId(long id) {
            this.id = id;
        }

        public void setCastData(List<MovieCastData> castData) {
            this.castData = castData;
        }

        public void setCrewData(List<MovieCrewData> crewData) {
            this.crewData = crewData;
        }

        public long getId() {
            return id;
        }

        public List<MovieCastData> getCastData() {
            return castData;
        }

        public List<MovieCrewData> getCrewData() {
            return crewData;
        }
    }
}
