
package com.project.messmanagement.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.MainActivity;
import com.project.messmanagement.models.ModelCreatMess;
import com.project.messmanagement.utils.TaskX;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentManager2 extends Fragment {
    LinearLayout linearCreatingProfile;
    String arrTimes[]={"AM","PM"};
    String startAt,endAt;
    TextView addMessParticulars;
    TextInputEditText etMobileNumber;
    TextInputEditText etMessName;
    TextInputEditText etItemName;
    TextInputEditText etOneTimePrice;
    TextInputEditText etMessLocation;
    EditText etStartAt;
    EditText etCloseAt;
    Spinner spinnerStartAt;
    Spinner spinnerEndAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_manager, container, false);

         etMessName=v.findViewById(R.id.etMessName);
         etItemName=v.findViewById(R.id.etItemName);
         etOneTimePrice=v.findViewById(R.id.etOneTimePrice);
         etMessLocation=v.findViewById(R.id.etMessLocation);
         etStartAt=v.findViewById(R.id.etStartAt);
         etCloseAt=v.findViewById(R.id.etCloseAt);
        addMessParticulars=v.findViewById(R.id.addMessParticulars);
         linearCreatingProfile=v.findViewById(R.id.linearCreatingProfile);
         etMobileNumber=v.findViewById(R.id.etMobileNumber);
         spinnerStartAt=v.findViewById(R.id.spinnerStartAt);
         spinnerEndAt=v.findViewById(R.id.spinnerEndAt);

        ArrayAdapter<String> ad=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arrTimes);
        spinnerStartAt.setAdapter(ad);
        spinnerEndAt.setAdapter(ad);
        checkMessExist();
        spinnerStartAt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startAt=arrTimes[i];
                Log.d("acsccc","coming at startAt"+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerEndAt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endAt=arrTimes[i];
                Log.d("acsccc","coming at endAt"+etCloseAt.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        v.findViewById(R.id.linerCreateMess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearCreatingProfile.setVisibility(View.VISIBLE);
                if(!etMessName.getText().toString().equals("")&&
                !etItemName.getText().toString().equals("")&&
                !etOneTimePrice.getText().toString().equals("")&&
                !etMessLocation.getText().toString().equals("")&&
                !etStartAt.getText().toString().equals("")&&etStartAt.getText().toString().length()<6&&
                        !etCloseAt.getText().toString().equals("")&&etCloseAt.getText().toString().length()<6&&
                !etMobileNumber.getText().toString().equals("")){

                    // calling method to add data
                    addNewMess(etMessName.getText().toString()
                            ,etItemName.getText().toString(),
                            etOneTimePrice.getText().toString(),
                            etMessLocation.getText().toString(),
                            etStartAt.getText().toString()+" "+startAt,
                            etCloseAt.getText().toString()+" "+endAt,
                            etMobileNumber.getText().toString());
                }
                else {
                    linearCreatingProfile.setVisibility(View.GONE);
                    if(etStartAt.getText().toString().equals(""))
                        etStartAt.setError("Enter valid data");
                    if(etCloseAt.getText().toString().equals(""))
                        etCloseAt.setError("Enter valid data");

                    if(etMessName.getText().toString().equals(""))
                        etMessName.setError("Enter valid data");
                    if(etItemName.getText().toString().equals(""))
                        etItemName.setError("Enter valid data");
                    if(etOneTimePrice.getText().toString().equals(""))
                        etOneTimePrice.setError("Enter valid data");
                    if(etMessLocation.getText().toString().equals(""))
                        etMessLocation.setError("Enter valid data");
                    if(etStartAt.getText().toString().equals(""))
                        etStartAt.setError("Enter valid data");
                    if(etCloseAt.getText().toString().equals(""))
                        etCloseAt.setError("Enter valid data");
                    if(etMobileNumber.getText().toString().equals(""))
                        etMobileNumber.setError("Enter valid data");

                }

            }
        });
        return v;
    }

    private void addNewMess( String messName,String items, String oneTimePrice, String messAddress,String startAt,String closeAt, String contactNumber) {
      ModelCreatMess modelCreatMess=  new ModelCreatMess(messName,items,oneTimePrice,messAddress,startAt,closeAt,contactNumber);
        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("ManagersList")
                .child(new TaskX().replaceEmail(MainActivity.modelRegisterUser.emailAddress))
                .setValue(modelCreatMess)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                linearCreatingProfile.setVisibility(View.GONE);
                Log.d("dfsdf","success created mess");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                linearCreatingProfile.setVisibility(View.GONE);
                Log.d("dfsdf","unable to create mess");
            }
        });
    }


    private void checkMessExist( ) {
        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("ManagersList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(new TaskX().replaceEmail(MainActivity.modelRegisterUser.emailAddress))){
                            loadMessInfo();
                            addMessParticulars.setText("Update mess data");
                        }else {
                            addMessParticulars.setText("Create mess");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });
    }

    private void loadMessInfo() {
            FirebaseDatabase.getInstance().getReference()
                    .child("Database")
                    .child("ManagersList")
                    .child(new TaskX().replaceEmail(MainActivity.modelRegisterUser.emailAddress))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ModelCreatMess modelCreatMess=snapshot.getValue(ModelCreatMess.class);

                                   etMessName.setText(modelCreatMess.messName);
                                    etItemName.setText(modelCreatMess.items);
                                    etOneTimePrice.setText(modelCreatMess.oneTimePrice);
                                    etMessLocation.setText(modelCreatMess.messAddress);

                                    etCloseAt.setText(modelCreatMess.closeAt);
                                    etMobileNumber.setText(modelCreatMess.contactNumber);


                                    if(modelCreatMess.startAt.contains("AM")) {
                                        spinnerStartAt.setSelection(0,true);
                                        int i=modelCreatMess.startAt.indexOf('A');
                                        etStartAt.setText(modelCreatMess.startAt.substring(0,i-1));

                                    } else if(modelCreatMess.startAt.contains("PM")) {
                                        spinnerStartAt.setSelection(1,true);
                                        int i=modelCreatMess.startAt.indexOf('P');
                                        etStartAt.setText(modelCreatMess.startAt.substring(0,i-1));
                                    }
                            if(modelCreatMess.closeAt.contains("AM")) {
                                spinnerEndAt.setSelection(0,true);
                                int i=modelCreatMess.closeAt.indexOf('A');
                                etCloseAt.setText(modelCreatMess.closeAt.substring(0,i-1));
                            } else if(modelCreatMess.closeAt.contains("PM")) {
                                spinnerEndAt.setSelection(1,true);
                                int i=modelCreatMess.closeAt.indexOf('P');
                                etCloseAt.setText(modelCreatMess.closeAt.substring(0,i-1));
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            }

}