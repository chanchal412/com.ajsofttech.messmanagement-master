package com.project.messmanagement.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.messmanagement.R;
import com.project.messmanagement.adapters.AdapterMyOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentMyOrders extends Fragment {
    RecyclerView recyclerView;
    TextView tvShowStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_orders, container, false);
        tvShowStatus=v.findViewById(R.id.tvShowStatus);
        recyclerView=v.findViewById(R.id.recyclerView);
        initAdapter();
        return recyclerView;
    }

    private void initAdapter() {


        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("myCustomers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> arrMyOrders=new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            arrMyOrders.add(ds.getKey());
                        }
                        if(arrMyOrders.size()==0){
                            tvShowStatus.setVisibility(View.VISIBLE);
                        }else if(arrMyOrders.size()>0){
                            Log.d("scscdc","success...");
                            tvShowStatus.setVisibility(View.GONE);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(new AdapterMyOrders(getContext(),arrMyOrders));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}