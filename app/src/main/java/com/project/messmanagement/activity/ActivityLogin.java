package com.project.messmanagement.activity;

import static com.project.messmanagement.activity.MainActivity.modelRegisterUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.project.messmanagement.database.SharedPref;
import com.project.messmanagement.databinding.ActivityLoginBinding;
import com.project.messmanagement.models.ModelRegisterUser;
import com.project.messmanagement.utils.TaskX;
import com.project.messmanagement.utils.UtilUITask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityLogin extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences spf=new SharedPref().createSharedPref(ActivityLogin.this,"AppSettings").getSharedPreferences();
        boolean isLoggeIn=spf.getBoolean("isLoggedIn",false);

        if(isLoggeIn==true){
            /// update account type
            SharedPreferences.Editor editor=new SharedPref().createSharedPref(ActivityLogin.this,"AppSettings").getEditor();

            if(binding.radioButton.isChecked()){
                modelRegisterUser.accountType="Manager";
            }if(binding.radioButton2.isChecked()) {
                modelRegisterUser.accountType="Cutomer";
            }
            editor.putString("accountType", modelRegisterUser.accountType).commit();




            ModelRegisterUser modelRegisterUser= new ModelRegisterUser(
                    spf.getString("accountType",""),
                    spf.getString("userName",""),
                    spf.getString("emailAddress",""),
                    spf.getString("password",""),
                    spf.getString("address",""),
                    spf.getString("contactNumber",""));

            MainActivity.modelRegisterUser =modelRegisterUser;
            Log.d("asdsf","browser+"+MainActivity.modelRegisterUser.userName);


            finish();
            startActivity(new Intent(ActivityLogin.this,MainActivity.class));
        }
        Log.d("asdsf","else");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        binding.tvRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this,ActivityRegister.class));
            }
        });

        binding.tvLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(binding.radioButton.isChecked()||binding.radioButton2.isChecked()){
                    if(!binding.etEmail.getText().toString().trim().equals("")&&
                            binding.etEmail.getText().toString().trim().contains("@gmail.com")&&
                            !binding.etPass.getText().toString().trim().equals("")){

                        if(binding.radioButton.isChecked()){
                            modelRegisterUser.accountType="Manager";
                        }else {
                            modelRegisterUser.accountType="Cutomer";
                        }
                        /// update account type
                        SharedPreferences.Editor editor=new SharedPref().createSharedPref(ActivityLogin.this,"AppSettings").getEditor();
                        editor.putString("emailAddress", binding.etEmail.getText().toString()).commit();
                        if(binding.radioButton.isChecked()){
                            modelRegisterUser.accountType="Manager";
                        }if(binding.radioButton2.isChecked()) {
                            modelRegisterUser.accountType="Cutomer";
                        }
                        editor.putString("accountType", modelRegisterUser.accountType).commit();


                        FirebaseDatabase.getInstance().getReference().child("Database").child("userProfiles").child(modelRegisterUser.accountType).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int x=0;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    String name = ds.getKey();
                                    Log.d("sizexxx","");
                                    if(ds.getKey().equals(new TaskX().replaceEmail(binding.etEmail.getText().toString()))){
                                        checkPass();
                                        x=1;
                                        break;
                                    }else {
                                        Log.d("sizexxx","not found");
                                    }
                                }
                                if(x==0){
                                    UtilUITask.getToastMessage(getApplicationContext(),binding.etEmail.getText().toString().trim()+" User not fount");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("sizexxxscdc","failes");
                            }
                        });


                    }else{
                        Log.d("sizexxx","failes");
                        UtilUITask.getToastMessage(getApplicationContext(),"Please enter valid credentials !");

                    }
//                if(binding.etEmail.getText().toString().trim().equals("")||
//                        binding.etPass.getText().toString().trim().equals("")){
//                }
                }else {
                    UtilUITask.getToastMessage(getApplicationContext(),"Please select an account type");
                }

            }
        });

    }

    private void checkPass() {

        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("userProfiles")
                .child(modelRegisterUser.accountType)
                .child(new TaskX().replaceEmail(binding.etEmail.getText().toString())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                MainActivity.modelRegisterUser=snapshot.getValue(ModelRegisterUser.class);
                if(binding.etPass.getText().toString().trim().equals(modelRegisterUser.password)){
                    finish();
//                    SharedPreferences spf=new SharedPref().createSharedPref(ActivityLogin.this,"AppSettings").getSharedPreferences();

//                    ModelRegisterUser modelRegisterUser1= new ModelRegisterUser(
//                            spf.getString("accountType",""),
//                            spf.getString("userName",""),
//                            spf.getString("emailAddress",""),
//                            spf.getString("password",""),
//                            spf.getString("address",""),
//                            spf.getString("contactNumber",""));

                   // MainActivity.modelRegisterUser =modelRegisterUser1;

                    startActivity(new Intent(ActivityLogin.this,MainActivity.class));
                    UtilUITask.getToastMessage(getApplicationContext(),"Login success !");

                    new SharedPref().createSharedPref(getApplicationContext(),"AppSettings").getEditor()
                            .putBoolean("isLoggedIn",true).commit();

                }
                else {
                    UtilUITask.getToastMessage(getApplicationContext(),"Please enter valid credentials !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}