package com.hasaneke.yourlolspy.API;

import com.hasaneke.yourlolspy.Models.GetSummonerIdByName;
import com.hasaneke.yourlolspy.Models.GetSummonerInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("{summonerName}?api_key=RGAPI-c5b74081-c0de-4716-aef8-63ad50453059")
    Call<GetSummonerIdByName> getData(@Path("summonerName") String summonerName);
    @GET("{summonerId}?api_key=RGAPI-c5b74081-c0de-4716-aef8-63ad50453059")
    Call<List<GetSummonerInfo>> getWholeInfo(@Path("summonerId")String summonerId);
}
