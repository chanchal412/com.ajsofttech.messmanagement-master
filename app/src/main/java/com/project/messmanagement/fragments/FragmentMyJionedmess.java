package com.project.messmanagement.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.MainActivity;
import com.project.messmanagement.models.ModelMyJoinedMess;
import com.project.messmanagement.utils.TaskX;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMyJionedmess extends Fragment {
TextView tvPaidAmount,tvMessNm,tvContactNumber,tvMessAddress,tvManagerEmail,tvTime,tvMessRoutine;
    CircleImageView imgProfilePic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_jionedmess, container, false);

        tvPaidAmount=v.findViewById(R.id.tvPaidAmount);
        tvMessNm=v.findViewById(R.id.tvMessNm);

        tvContactNumber=v.findViewById(R.id.tvContactNumber);
        tvMessAddress=v.findViewById(R.id.tvMessAddress);
        tvManagerEmail=v.findViewById(R.id.tvManagerEmail);
        imgProfilePic=v.findViewById(R.id.imgProfilePic);
        tvMessRoutine=v.findViewById(R.id.tvMessRoutine);
        tvTime=v.findViewById(R.id.tvTime);


        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("myJionedMess")
                .child(new TaskX().replaceEmail(MainActivity.modelRegisterUser.emailAddress))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelMyJoinedMess modelMyJoinedMess=snapshot.getValue(ModelMyJoinedMess.class);
                        if(modelMyJoinedMess!=null){
                            tvMessRoutine.setText(""+modelMyJoinedMess.months
                                    +" months daily "+modelMyJoinedMess.times+" times");
                            tvPaidAmount.setText("Paid amount ="+modelMyJoinedMess.paidAmount+"/-");
                            tvMessAddress.setText("Address : "+modelMyJoinedMess.messAddress);
                            tvMessNm.setText(modelMyJoinedMess.messNm);
                            tvTime.setText(""+modelMyJoinedMess.startAt
                                    +" to "+modelMyJoinedMess.closeAt);
                            tvContactNumber.setText("Mobile : "+modelMyJoinedMess.contactNumber);
                            tvManagerEmail.setText("Email : "+modelMyJoinedMess.managerEmail);
                            loadProfilePic(modelMyJoinedMess.managerEmail);
                        }else {
                            UtilUITask.getToastMessage(getContext(),"");
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        return v;
    }

    private void loadProfilePic(String email) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("messManagement/profilePics/"+ new TaskX().replaceEmail(email) +".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                // Data for "images/island.jpg" is returns, use this as needed
                Glide.with(getContext()).load(bytes).into(imgProfilePic);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}