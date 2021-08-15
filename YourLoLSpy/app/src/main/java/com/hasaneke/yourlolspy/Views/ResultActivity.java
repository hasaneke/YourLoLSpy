package com.hasaneke.yourlolspy.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hasaneke.yourlolspy.API.API;
import com.hasaneke.yourlolspy.Models.GetSummonerInfo;
import com.hasaneke.yourlolspy.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity extends AppCompatActivity {
    TextView soloRankedText,flexRankedText,soloWinrateText,flexWinrateText;
    String summonerId;
    Retrofit retrofit;
    String serverInfo;
    Intent intent;
    ArrayList<GetSummonerInfo> getSummonerInfos;
    ImageView soloImageView,flexImageView;
    TextView summonerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        soloWinrateText = findViewById(R.id.soloWinRateText);
        flexWinrateText = findViewById(R.id.flextWinRateText);
        soloRankedText = findViewById(R.id.soloRankedText);
        flexRankedText = findViewById(R.id.flexRankedText);
        intent = getIntent();
        serverInfo = intent.getStringExtra("serverInfo");
        summonerId = intent.getStringExtra("summonerId");
        soloImageView = findViewById(R.id.soloImageView);
        flexImageView = findViewById(R.id.flexImageView);
        summonerName = findViewById(R.id.summonerNameText);
        getData();

    }
    public void getData(){
        String BASE_URL = "https://"+serverInfo+".api.riotgames.com/lol/league/v4/entries/by-summoner/";
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API lolApı = retrofit.create(API.class);
        final Call<List<GetSummonerInfo>> getSummonerInfo = lolApı.getWholeInfo(summonerId);
        getSummonerInfo.enqueue(new Callback<List<GetSummonerInfo>>() {
            @Override
            public void onResponse(Call<List<GetSummonerInfo>> call, Response<List<GetSummonerInfo>> response) {
                if(response.isSuccessful()){
                    summonerName.setText(intent.getStringExtra("summonerName"));
                    getSummonerInfos = new ArrayList<>(response.body());
                    if(getSummonerInfos.size() == 2){
                        if(getSummonerInfos.get(0).getQueueType().equals("RANKED_FLEX_SR")) {
                            changeFlexImages(0);
                            fillingDataForFlex(0);
                            changeSoloImages(1);
                            fillingDatForSolo(1);
                        }else{
                            changeFlexImages(1);
                            fillingDataForFlex(1);
                            changeSoloImages(0);
                            fillingDatForSolo(0);
                        }
                    }else if(getSummonerInfos.size() == 1){
                        if(getSummonerInfos.get(0).getQueueType().equals("RANKED_FLEX_SR")) {
                            changeFlexImages(0);
                            float x = getSummonerInfos.get(0).getWins() + getSummonerInfos.get(0).getLosses();
                            float y = (getSummonerInfos.get(0).getWins() / x) * 100;
                            int z = Math.round(y);
                            flexWinrateText.setText("%"+z);
                            flexRankedText.setText(getSummonerInfos.get(0).getTier()+" "+getSummonerInfos.get(0).getRank());
                            soloRankedText.setText("UNRANKED");
                        }else{
                            changeSoloImages(0);
                            float a = getSummonerInfos.get(0).getWins() + getSummonerInfos.get(0).getLosses();
                            float b = (getSummonerInfos.get(0).getWins() / a) * 100;
                            int c = Math.round(b);
                            soloWinrateText.setText("%"+c);
                            soloRankedText.setText(getSummonerInfos.get(0).getTier()+" "+getSummonerInfos.get(0).getRank());
                            flexRankedText.setText("UNRANKED");
                        }
                    }else{
                        soloRankedText.setText("UNRANKED");
                        flexRankedText.setText("UNRANKED");
                    }
                }else{

                }
            }
            @Override
            public void onFailure(Call<List<GetSummonerInfo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void changeFlexImages(int position){
        String flexTier = getSummonerInfos.get(position).getTier();
        switch (flexTier){
                case "IRON":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.iron);
                    break;
                case "BRONZE":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.bronze);
                case "GOLD":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.gold);
                    break;
                case "SILVER":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.silver);
                    break;
                case "PLATINUM":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.platinium);
                    break;
                case "DIAMOND":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.diamond);
                    break;
                case "MASTER":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.master);
                    break;
                case "GRANDMASTER":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.grandmaster);
                    break;
                case "CHALLENGER":
                    flexImageView.setVisibility(View.VISIBLE);
                    flexImageView.setImageResource(R.drawable.challenger);
                    break;
        }
    }
    public void changeSoloImages(int position){
        String soloTier = getSummonerInfos.get(position).getTier();
        switch (soloTier){
            case "IRON":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.iron);
                break;
            case "BRONZE":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.bronze);
                break;
            case "GOLD":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.gold);
                break;
            case "SILVER":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.silver);
                break;
            case "PLATINUM":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.platinium);
                break;
            case "DIAMOND":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.diamond);
                break;
            case "MASTER":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.master);
                break;
            case "GRANDMASTER":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.grandmaster);
                break;
            case "CHALLENGER":
                soloImageView.setVisibility(View.VISIBLE);
                soloImageView.setImageResource(R.drawable.challenger);
                break;
        }
    }
    public void fillingDataForFlex(int position){
        //flexpart
        float x = getSummonerInfos.get(position).getWins() + getSummonerInfos.get(position).getLosses();
        float y = (getSummonerInfos.get(position).getWins() / x) * 100;
        int z = Math.round(y);
        flexWinrateText.setText("%"+z);
        flexRankedText.setText(getSummonerInfos.get(position).getTier()+" "+getSummonerInfos.get(position).getRank());
        //solopart

    }
    public void fillingDatForSolo(int position){
        float a = getSummonerInfos.get(position).getWins() + getSummonerInfos.get(position).getLosses();
        float b = (getSummonerInfos.get(position).getWins() / a) * 100;
        int c = Math.round(b);
        soloWinrateText.setText("%"+c);
        soloRankedText.setText(getSummonerInfos.get(position).getTier()+" "+getSummonerInfos.get(position).getRank());
    }
}