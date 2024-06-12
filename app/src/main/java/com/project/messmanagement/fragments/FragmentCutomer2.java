package com.project.messmanagement.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.MainActivity;
import com.project.messmanagement.adapters.AdapterMessList;
import com.project.messmanagement.utils.UtilUITask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentCutomer2 extends Fragment {
RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cutomer, container, false);
        recyclerView=v.findViewById(R.id.recyclerView);
        initAdapter();

        return  v;
    }



    private void initAdapter() {
        FirebaseDatabase.getInstance().getReference().child("Database").child("ManagersList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                MainActivity.alert_loading.dismiss();
                ArrayList<String> managerList=new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    managerList.add(ds.getKey());
                }
                if(managerList.size()>0){
                    recyclerView.setAdapter(new AdapterMessList(getContext(),managerList.size(),managerList));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }else {
                    UtilUITask.getToastMessage(getContext(),"no mess found");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}