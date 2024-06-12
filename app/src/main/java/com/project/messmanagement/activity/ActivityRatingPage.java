package com.project.messmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.project.messmanagement.adapters.AdapterReviewList;
import com.project.messmanagement.databinding.ActivityRatingPageBinding;

import java.util.ArrayList;

public class ActivityRatingPage extends AppCompatActivity {
    public static ArrayList<String> arrManagerNms=new ArrayList<>();

    public static ArrayList<String> arrReviewGiverNms=new ArrayList<>();
    public static String arrManagerNm;
    ActivityRatingPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRatingPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if(arrManagerNms.size()==0&&arrReviewGiverNms.size()==0){
            binding.tvReviewNotAvailable.setVisibility(View.VISIBLE);
        }else if(arrManagerNms.size()!=0&&arrReviewGiverNms.size()!=0){
            binding.tvReviewNotAvailable.setVisibility(View.GONE);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            binding.recyclerView.setAdapter(new AdapterReviewList(getApplicationContext()));
        }
    }
}