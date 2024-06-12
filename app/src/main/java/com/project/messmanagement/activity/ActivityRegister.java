package com.project.messmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.project.messmanagement.database.SharedPref;
import com.project.messmanagement.databinding.ActivityRegisterBinding;
import com.project.messmanagement.models.ModelRegisterUser;
import com.project.messmanagement.utils.PickImage;
import com.project.messmanagement.utils.TaskX;
import com.project.messmanagement.utils.UtilUITask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ActivityRegister extends AppCompatActivity {
    public static boolean isUpdateProfile;
    ActivityRegisterBinding binding;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Register new user");



        if(isUpdateProfile==true){
            getSupportActionBar().setTitle("Update profile");
            binding.tvRegisterUser.setText("Update profile");
            loadExistedData();
        }
        binding.imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickImage().pickImage(ActivityRegister.this);

            }
        });
        binding.tvRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(binding.radioButton.isChecked()||binding.radioButton2.isChecked()){
                    Log.d("aksfdf","sucess1");
                    if(!binding.etEmail.getText().toString().trim().equals("")&&binding.etEmail.getText().toString().trim().contains("@gmail.com")){
                        Log.d("aksfdf","sucess2");
                        if(!binding.etUserName.getText().toString().trim().equals("")&&!binding.etAddress.getText().toString().trim().equals("")){
                            Log.d("aksfdf","sucess3");
                            if(!binding.etPass.getText().toString().trim().equals("")&&binding.etPass.getText().toString().trim().length()>7){
                                Log.d("aksfdf","sucess4");
                                if(!binding.etMobileNumber.getText().toString().equals(null)&&binding.etMobileNumber.getText().toString().length()==10) {
                                    if (binding.etPass.getText().toString().trim().equals(binding.etPass1.getText().toString().trim())) {
                                        Log.d("aksfdf", "sucess5");
                                        if (uri != null) {
                                            Log.d("aksfdf", "imagenotnull");
                                            String accountType;
                                            Log.d("aksfdf", "coming...1");

                                            if (binding.radioButton.isChecked()) {
                                                accountType = "Manager";
                                            } else {
                                                accountType = "Cutomer";
                                            }

                                            Log.d("aksfdf", "coming...2");
                                            ModelRegisterUser modelRegisterUser = new ModelRegisterUser(
                                                    accountType,
                                                    binding.etUserName.getText().toString(),
                                                    binding.etEmail.getText().toString().trim(),
                                                    binding.etPass.getText().toString().trim(),
                                                    binding.etMobileNumber.getText().toString(),
                                                    binding.etAddress.getText().toString()
                                                    );

                                            Log.d("aksfdf", "coming...3");
                                            uploadImage(modelRegisterUser.emailAddress);
                                            binding.linearCreatingProfile.setVisibility(View.VISIBLE);
                                            MainActivity.modelRegisterUser = modelRegisterUser;
                                            Log.d("aksfdf", "coming...4");
                                        } else {
                                            Log.d("aksfdf", "imageNull");
                                            UtilUITask.getToastMessage(getApplicationContext(), "Please select profile pic");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if(!binding.radioButton.isChecked()||!binding.radioButton2.isChecked()){
                    UtilUITask.getToastMessage(getApplicationContext(),"Please select account type");
                }


                if(binding.etEmail.getText().toString().trim().equals("")||!binding.etEmail.getText().toString().trim().contains("@gmail.com")){
                    binding.etEmail.setError("Enter valid email address");
                }
                if(binding.etUserName.getText().toString().equals("")){
                    binding.etUserName.setError("Enter valid user name");
                }
                if(binding.etPass.getText().toString().trim().equals("")){
                    binding.etPass.setError("Enter valid password");
                }
                if(binding.etMobileNumber.getText().toString().equals(null)
                        ||binding.etMobileNumber.getText().toString().length()!=10) {
                    binding.etMobileNumber.setError("Enter valid contact number");
                }

                    if(binding.etAddress.getText().toString().equals("")){
                    binding.etAddress.setError("Enter valid information");
                }
                if(!binding.etPass.getText().toString().trim().equals(binding.etPass1.getText().toString().trim())){
                    binding.etPass1.setError("Password not match");
                }
                if(binding.etPass.getText().toString().trim().length()<8){
                    binding.etPass1.setError("Password must be 8 characters");
                }
            }
        });
    }


    private void uploadImage(String emailAdderess) {
        Log.d("aksfdf","coming....5");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("messManagement/profilePics/"+new TaskX().replaceEmail(emailAdderess)+".jpg");

        UploadTask uploadTask = storageReference.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("aksfdf","failesx"+exception);
                binding.linearCreatingProfile.setVisibility(View.GONE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("aksfdf","success");

                addProfileMetadata(emailAdderess);
            }
        });
    }

    private void loadExistedData() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Database")
                .child("userProfiles")
                .child(MainActivity.modelRegisterUser.accountType)
                .child(new TaskX().replaceEmail(MainActivity.modelRegisterUser.emailAddress))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MainActivity.modelRegisterUser=snapshot.getValue(ModelRegisterUser.class);
                        binding.etMobileNumber.setText(MainActivity.modelRegisterUser.contactNumber);
                        binding.etAddress.setText(MainActivity.modelRegisterUser.address);
                        binding.etPass.setText(MainActivity.modelRegisterUser.password);
                        binding.etPass1.setText(MainActivity.modelRegisterUser.password);
                        binding.etUserName.setText(MainActivity.modelRegisterUser.userName);
                        binding.etEmail.setText(MainActivity.modelRegisterUser.emailAddress);

                        if(MainActivity.modelRegisterUser.accountType.equals("Manager")){
                            binding.radioButton.setChecked(true);
                        }else {
                            binding.radioButton2.setChecked(true);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void addProfileMetadata(String emailAdderess) {
         DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Database")
                .child("userProfiles")
                .child(MainActivity.modelRegisterUser.accountType)
                .child(new TaskX().replaceEmail(emailAdderess))
                .setValue(MainActivity.modelRegisterUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                SharedPreferences.Editor editor=new SharedPref().createSharedPref(ActivityRegister.this,"AppSettings").getEditor();
                editor.putBoolean("isLoggedIn",true).commit();
//                editor.putString("accountType",""+MainActivity.modelRegisterUser.accountType).commit();
//                editor.putString("userName",""+MainActivity.modelRegisterUser.userName).commit();
//                editor.putString("emailAddress",""+MainActivity.modelRegisterUser.emailAddress).commit();
//                editor.putString("password",""+MainActivity.modelRegisterUser.password).commit();
//                editor.putString("address",""+MainActivity.modelRegisterUser.address).commit();
//                editor.putString("contactNumber",""+MainActivity.modelRegisterUser.contactNumber).commit();
//                editor.putString("userName",""+MainActivity.modelRegisterUser.userName).commit();
//                Log.d("aksfdf","successX");
                finish();
                startActivity(new Intent(ActivityRegister.this,MainActivity.class));
                binding.linearCreatingProfile.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.linearCreatingProfile.setVisibility(View.GONE);
                Log.d("aksfdf",""+e.getMessage());
            }
        });

    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        try {
            uri=intent.getData();
            binding.imgProfilePic.setImageURI(intent.getData());
        } catch (Exception unused) {
            UtilUITask.getToastMessage(getApplicationContext(),"Image not selected");
        }
    }
}