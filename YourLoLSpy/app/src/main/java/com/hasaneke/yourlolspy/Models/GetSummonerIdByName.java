package com.hasaneke.yourlolspy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSummonerIdByName {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }
}
