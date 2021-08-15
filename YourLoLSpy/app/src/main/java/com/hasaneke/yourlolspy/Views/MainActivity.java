package com.hasaneke.yourlolspy.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hasaneke.yourlolspy.API.API;
import com.hasaneke.yourlolspy.Models.GetSummonerIdByName;
import com.hasaneke.yourlolspy.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    Retrofit retrofit;
    Dialog serverDialog;
    Button krButton,euButton,euwButton,naButton,trButton,serverButton;
    String serverInfo;
    EditText summonerNameText;
    GetSummonerIdByName getSummonerIdByName;
    TextView serverText;
    Button spyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverDialog = new Dialog(this);
        serverButton = findViewById(R.id.serverchoose);
        summonerNameText = findViewById(R.id.summonerNameText);
        serverText = findViewById(R.id.serverchoose);
        spyButton = findViewById(R.id.spyButton);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void chooseTheServer(View view){
        serverDialog.setContentView(R.layout.serveroptions);
        serverDialog.show();
        krButton = serverDialog.findViewById(R.id.krServer);
        krButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverButton.setText("KR");
                serverInfo = "kr";
                serverDialog.cancel();
            }
        });
        euButton = serverDialog.findViewById(R.id.euServer);
        euButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverButton.setText("EU");
                serverInfo = "eun1";
                serverDialog.cancel();
            }
        });
        euwButton = serverDialog.findViewById(R.id.euwServer);
        euwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverButton.setText("EUW");
                serverInfo = "euw1";
                serverDialog.cancel();
            }
        });
        naButton = serverDialog.findViewById(R.id.naServer);
        naButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverButton.setText("NA");
                serverInfo = "na1";
                serverDialog.cancel();

            }
        });
        trButton = serverDialog.findViewById(R.id.trServer);
        trButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverInfo = "tr1";
                serverButton.setText("TR");
                serverDialog.cancel();
            }
        });
    }
    public void goToResultActivity(View view){
        final String summonerName = summonerNameText.getText().toString();
        String BASE_URL = "https://"+serverInfo+".api.riotgames.com/lol/summoner/v4/summoners/by-name/";
        //Retrofit Process
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API lolApı = retrofit.create(API.class);
        Call<GetSummonerIdByName> getSummonerIdByNameCall = lolApı.getData(summonerName);
        getSummonerIdByNameCall.enqueue(new Callback<GetSummonerIdByName>() {
            @Override
            public void onResponse(Call<GetSummonerIdByName> call, Response<GetSummonerIdByName> response) {
                if(response.isSuccessful()){
                    getSummonerIdByName = response.body();
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("summonerId",getSummonerIdByName.getId());
                    intent.putExtra("serverInfo",serverInfo);
                    intent.putExtra("summonerName",summonerName);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Summoner could not find",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<GetSummonerIdByName> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No internet connection or server has not been chosen",Toast.LENGTH_LONG).show();
            }
        });


    }

}