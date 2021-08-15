package com.hasaneke.yourlolspy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSummonerInfo {
    @SerializedName("queueType")
    @Expose
    private String queueType;
    @SerializedName("tier")
    @Expose
    private String tier;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("summonerName")
    @Expose
    private String summonerName;
    @SerializedName("wins")
    @Expose
    private Integer wins;
    @SerializedName("losses")
    @Expose
    private Integer losses;

    public String getQueueType() {
        return queueType;
    }

    public String getTier() {
        return tier;
    }

    public String getRank() {
        return rank;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getLosses() {
        return losses;
    }
}
