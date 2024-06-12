package com.project.messmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.ActivityRatingPage;
import com.project.messmanagement.activity.MainActivity;
import com.project.messmanagement.models.ModelCreatMess;
import com.project.messmanagement.utils.TaskX;
import com.project.messmanagement.utils.UtilMakePayment;
import com.project.messmanagement.utils.UtilUITask;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMessList extends RecyclerView.Adapter<AdapterMessList.myViewHolder> {
    Context context;
    int size;
    ArrayList<String> managerList = new ArrayList<>();

    public AdapterMessList(Context context, int size, ArrayList<String> keys) {
        this.context = context;
        this.size = size;
        this.managerList = keys;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater l = LayoutInflater.from(context);
        View v = l.inflate(R.layout.adapter_mess_list, parent, false);
        myViewHolder viewHolder = new myViewHolder(v);
        TextView tvOneTimePrice = v.findViewById(R.id.tvOneTimePrice);
        LinearLayout linearCall = v.findViewById(R.id.linearCall);
        TextView tvContactNumber = v.findViewById(R.id.tvContactNumber);
        v.findViewById(R.id.rateMess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TaskX().addReview(context,managerList.get(viewHolder.getAdapterPosition()));

            }
        });
        v.findViewById(R.id.imgRatingList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityRatingPage.arrManagerNms.clear();
                ActivityRatingPage.arrReviewGiverNms.clear();



                checkFirebaseRatingsExist(managerList.get(viewHolder.getAdapterPosition()),viewHolder,viewHolder.getAdapterPosition());
            }
        });
        linearCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber(tvContactNumber.getText().toString());
            }
        });
        v.findViewById(R.id.tvMakePayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("Database").child("ManagersList").child(managerList.get(viewHolder.getAdapterPosition())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UtilMakePayment.modelCreatMess = snapshot.getValue(ModelCreatMess.class);


                        UtilMakePayment.messManagerNm=managerList.get(viewHolder.getAdapterPosition());
                        new UtilMakePayment().makePayment(context,Float.parseFloat(tvOneTimePrice.getText().toString()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        return viewHolder;
    }

    private void checkFirebaseRatingsExist(String managerNm, myViewHolder viewHolder, int adapterPosition) {
        String halfEmail = MainActivity.modelRegisterUser.emailAddress.substring(0, MainActivity.modelRegisterUser.emailAddress.indexOf('@'));
        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("RateReview")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ActivityRatingPage.arrManagerNms.add(ds.getKey());
                        }

                        Log.d("scsdcdsv","arrManagerNms>"+ActivityRatingPage.arrManagerNms);

                        // this below line
                        ActivityRatingPage.arrManagerNm=managerNm;
                        Log.d("scsdcdsv","arrManagerNms0 the post>"+managerNm);
                        reviewMakersNm(managerNm);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void reviewMakersNm(String managerNm) {
        FirebaseDatabase.getInstance().getReference().child("Database").child("RateReview").child(managerNm)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ActivityRatingPage.arrReviewGiverNms.add(ds.getKey());
                        }
                        if(ActivityRatingPage.arrReviewGiverNms.size()==0){
                            UtilUITask.getToastMessage(context,"Review not available...");
                        }else {
                            context.startActivity(new Intent(context, ActivityRatingPage.class));
                        }
                        Log.d("sdvsdv","arrReviewGiverNms>"+ActivityRatingPage.arrReviewGiverNms);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        initUi(managerList.get(position), holder, position);
        loadPrifilePic(context, holder, managerList.get(position));

        ///Log.d("sdfsdf","coming"+listMessList.get(position).contactNumber);
    }


    @Override
    public int getItemCount() {
        return size;
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        TextView items, oneTimePrice, messAddress, messLiveTime, contactNumber, tvMessName;
        CircleImageView imgProfilePic;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            items = itemView.findViewById(R.id.tvItems);
            oneTimePrice = itemView.findViewById(R.id.tvOneTimePrice);
            messAddress = itemView.findViewById(R.id.messAddress);
            messLiveTime = itemView.findViewById(R.id.messLiveTime);
            contactNumber = itemView.findViewById(R.id.tvContactNumber);
            imgProfilePic = itemView.findViewById(R.id.imgProfilePic);
            tvMessName = itemView.findViewById(R.id.tvMessName);

        }
    }

    private void initUi(String messManagerName, myViewHolder holder, int position) {
        FirebaseDatabase.getInstance().getReference().child("Database").child("ManagersList").child(messManagerName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ModelCreatMess modelCreatMess = snapshot.getValue(ModelCreatMess.class);
                holder.items.setText("Items : " + modelCreatMess.items);
                holder.oneTimePrice.setText(modelCreatMess.oneTimePrice);
                holder.messAddress.setText(modelCreatMess.messAddress);
                holder.messLiveTime.setText(modelCreatMess.startAt + " to " + modelCreatMess.closeAt);
                holder.contactNumber.setText(modelCreatMess.contactNumber);
                holder.tvMessName.setText(modelCreatMess.messName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadPrifilePic(Context context, myViewHolder holder, String emailAddress) {


        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("messManagement/profilePics/" + emailAddress + ".jpg");


        Log.d("sdfdsf", "" + storageReference.getPath());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                try{
                    Glide.with(context).load(bytes).into(holder.imgProfilePic);
                }catch (Exception e){

                }

                Log.d("sdfdsf", "sucess");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("sdfdsf", "fail" + exception.getMessage());
                // Handle any errors
            }
        });
    }



    private void callPhoneNumber(String mobileNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobileNumber));
        context.startActivity(callIntent);
    }


}
