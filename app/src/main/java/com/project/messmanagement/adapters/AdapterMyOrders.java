package com.project.messmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.messmanagement.R;
import com.project.messmanagement.models.ModelMyCustomers;
import com.project.messmanagement.utils.TaskX;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterMyOrders extends RecyclerView.Adapter<AdapterMyOrders.myViewHolder>{
    Context context;
    ArrayList<String> arrMyOrders;
    public AdapterMyOrders(Context context, ArrayList<String> arrMyOrders){
        this.context=context;
        this.arrMyOrders=arrMyOrders;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater l=LayoutInflater.from(context);
        View v=l.inflate(R.layout.adapter_myoders,parent,false);
        myViewHolder viewHolder=new myViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        initAdapter(holder,position);

    }

    private void initProfilePic(myViewHolder holder, String emailAddress) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("messManagement/profilePics/"+ new TaskX().replaceEmail(emailAddress) +".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Glide.with(context).load(bytes).into(holder.imgProfilePic);
                holder.imgProfilePic.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrMyOrders.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmailAddress,tvAddress,tvMessRoutine,tvUserNm,tvPaidAmount;
        ImageView imgProfilePic;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmailAddress=itemView.findViewById(R.id.tvEmailAddress);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvMessRoutine=itemView.findViewById(R.id.tvMessRoutine);
            tvUserNm=itemView.findViewById(R.id.tvUserNm);
            tvPaidAmount=itemView.findViewById(R.id.tvPaidAmount);
            imgProfilePic=itemView.findViewById(R.id.imgProfilePic);
        }
    }

    private void initAdapter(myViewHolder holder, int position) {
        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("myCustomers")
                .child(new TaskX().replaceEmail(arrMyOrders.get(position)))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelMyCustomers modelMyCustomers=snapshot.getValue(ModelMyCustomers.class);

                        if(!modelMyCustomers.emailAddress.equals(null)){
                            initProfilePic(holder,modelMyCustomers.emailAddress);
                            holder.tvAddress.setText("Address : "+modelMyCustomers.address);
                            holder.tvEmailAddress.setText("Email : "+modelMyCustomers.emailAddress);
                            holder.tvUserNm.setText("Name : "+modelMyCustomers.userNm);



                            holder.tvMessRoutine.setText(""+modelMyCustomers.months
                                    +" months daily "+modelMyCustomers.times+" times");



                            holder.tvPaidAmount.setText("Paid amount ="+modelMyCustomers.paidAmount+"/-");


                        }else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
