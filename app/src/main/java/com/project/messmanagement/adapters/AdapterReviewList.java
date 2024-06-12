package com.project.messmanagement.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.ActivityRatingPage;
import com.project.messmanagement.models.ModelRateReview;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdapterReviewList extends RecyclerView.Adapter<AdapterReviewList.myViewHolder>{
    Context context;
    public AdapterReviewList(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater l=LayoutInflater.from(context);
        View v=l.inflate(R.layout.adapter_rates_list,parent,false);
        myViewHolder holder=new myViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        setUi(holder,position);
    }

    @Override
    public int getItemCount() {
        // this statement
        return ActivityRatingPage.arrReviewGiverNms.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserNmOfReviewGiver,tvReviewMessage;
        ImageView imgRate1,imgRate2,imgRate3,imgRate4,imgRate5,imgProfilePic;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserNmOfReviewGiver=itemView.findViewById(R.id.tvUserNmOfReviewGiver);
            tvReviewMessage=itemView.findViewById(R.id.tvReviewMessage);
            imgRate1=itemView.findViewById(R.id.imgRate1);
            imgRate2=itemView.findViewById(R.id.imgRate2);
            imgRate3=itemView.findViewById(R.id.imgRate3);
            imgRate4=itemView.findViewById(R.id.imgRate4);
            imgRate5=itemView.findViewById(R.id.imgRate5);
            imgProfilePic=itemView.findViewById(R.id.imgProfilePic);
        }
    }
    private void setUi(myViewHolder holder, int position) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference()
                // this below line third child
        .child("Database")
                .child("RateReview")
                .child(ActivityRatingPage.arrManagerNm)
                .child(ActivityRatingPage.arrReviewGiverNms.get(position));

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ModelRateReview modelRateReview=snapshot.getValue(ModelRateReview.class);

                        Log.d("sacdc0","arrManagerNm : step>"+position+ActivityRatingPage.arrManagerNm);
                        Log.d("sacdc0","step>"+position+ActivityRatingPage.arrReviewGiverNms.get(position));


                        Log.d("sacdc0","step>"+position+modelRateReview.getRateValue());
                        Log.d("sacdc0","step>"+position+modelRateReview.getReviewMessage());
                        Log.d("sacdc0","step>"+position+modelRateReview.getRatedByUserNm());
                       // Log.d("sacdc0","step : "+position+modelRateReview.getUserNm());




                        loadPrifilePic(holder,modelRateReview.getRatedByUserNm().substring(0,modelRateReview.getRatedByUserNm().length()-1));
                        if(!modelRateReview.reviewMessage.equals(null)){
                            holder.tvReviewMessage.setText(modelRateReview.getReviewMessage());
                            holder.tvUserNmOfReviewGiver.setText(modelRateReview.getRatedByUserNm());
                            if(modelRateReview.getRateValue().toString().equals("1")){
                                holder.imgRate1.setImageResource(R.drawable.starx);
                                holder.imgRate2.setImageResource(R.drawable.stary);
                                holder.imgRate3.setImageResource(R.drawable.stary);
                                holder.imgRate4.setImageResource(R.drawable.stary);
                                holder.imgRate5.setImageResource(R.drawable.stary);
                            }
                            if(modelRateReview.getRateValue().toString().equals("2")){
                                holder.imgRate1.setImageResource(R.drawable.starx);
                                holder.imgRate2.setImageResource(R.drawable.starx);
                                holder.imgRate3.setImageResource(R.drawable.stary);
                                holder.imgRate4.setImageResource(R.drawable.stary);
                                holder.imgRate5.setImageResource(R.drawable.stary);
                            }
                            if(modelRateReview.getRateValue().toString().equals("3")){
                                holder.imgRate1.setImageResource(R.drawable.starx);
                                holder.imgRate2.setImageResource(R.drawable.starx);
                                holder.imgRate3.setImageResource(R.drawable.starx);
                                holder.imgRate4.setImageResource(R.drawable.stary);
                                holder.imgRate5.setImageResource(R.drawable.stary);
                            }
                            if(modelRateReview.getRateValue().toString().equals("4")){
                                holder.imgRate1.setImageResource(R.drawable.starx);
                                holder.imgRate2.setImageResource(R.drawable.starx);
                                holder.imgRate3.setImageResource(R.drawable.starx);
                                holder.imgRate4.setImageResource(R.drawable.starx);
                                holder.imgRate5.setImageResource(R.drawable.stary);
                            }
                            if(modelRateReview.getRateValue().toString().equals("5")){
                                holder.imgRate1.setImageResource(R.drawable.starx);
                                holder.imgRate2.setImageResource(R.drawable.starx);
                                holder.imgRate3.setImageResource(R.drawable.starx);
                                holder.imgRate4.setImageResource(R.drawable.starx);
                                holder.imgRate5.setImageResource(R.drawable.starx);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void loadPrifilePic(myViewHolder viewHolder,String profilePicNm) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("messManagement/profilePics/"+profilePicNm+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Glide.with(context).load(bytes).into(viewHolder.imgProfilePic);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
