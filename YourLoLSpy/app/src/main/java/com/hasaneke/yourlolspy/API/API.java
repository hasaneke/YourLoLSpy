package com.hasaneke.yourlolspy.API;

import com.hasaneke.yourlolspy.Models.GetSummonerIdByName;
import com.hasaneke.yourlolspy.Models.GetSummonerInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("{summonerName}?api_key=${key here}")
    Call<GetSummonerIdByName> getData(@Path("summonerName") String summonerName);
    @GET("{summonerId}?api_key={key here}")
    Call<List<GetSummonerInfo>> getWholeInfo(@Path("summonerId")String summonerId);
}
