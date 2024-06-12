package com.project.messmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.messmanagement.R;
import com.project.messmanagement.database.SharedPref;
import com.project.messmanagement.databinding.ActivityMainBinding;
import com.project.messmanagement.fragments.FragmentCustomer1;
import com.project.messmanagement.fragments.FragmentManager1;
import com.project.messmanagement.models.ModelMyCustomers;
import com.project.messmanagement.models.ModelMyJoinedMess;
import com.project.messmanagement.models.ModelRegisterUser;
import com.project.messmanagement.utils.CoreTask;
import com.project.messmanagement.utils.TaskX;
import com.project.messmanagement.utils.UtilMakePayment;
import com.project.messmanagement.utils.UtilUITask;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.razorpay.PaymentResultListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    public static ModelRegisterUser modelRegisterUser;
    ActivityMainBinding binding;
    public static Activity activity;
    public static AlertDialog alert_loading;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences spf=new SharedPref().createSharedPref(MainActivity.this,"AppSettings").getSharedPreferences();
        boolean isLoggeIn=spf.getBoolean("isLoggedIn",false);

        if(isLoggeIn==true){

//            ModelRegisterUser modelRegisterUser= new ModelRegisterUser(
//                    spf.getString("accountType",""),
//                    spf.getString("userName",""),
//                    spf.getString("emailAddress",""),
//                    spf.getString("password",""));
//
//            MainActivity.modelRegisterUser=modelRegisterUser;

            // startActivity(new Intent(MainActivity.this,MainActivity.class));
        }else {
            startActivity(new Intent(MainActivity.this,ActivityLogin.class));
        }


    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        new CoreTask().getPermission(MainActivity.this);




        activity=MainActivity.this;
        this.binding.imgDrawerController.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.binding.dl.openDrawer(Gravity.LEFT);
            }
        });








        SharedPreferences spf=new SharedPref().createSharedPref(MainActivity.this,"AppSettings").getSharedPreferences();
        ModelRegisterUser modelRegisterUser= new ModelRegisterUser(
                spf.getString("accountType",""),
                spf.getString("userName",""),
                spf.getString("emailAddress",""),
                spf.getString("password",""),
                spf.getString("address",""),
                spf.getString("contactNumber",""));

        MainActivity.modelRegisterUser=modelRegisterUser;
        Log.d("sddvdv","emailx"+modelRegisterUser.emailAddress);


        boolean isLoggeIn=spf.getBoolean("isLoggedIn",false);
        if(isLoggeIn==true){

            if(spf.contains("emailAddress")){
                loadPrifilePic();
                initTypeOfUi();
            }else {
                FirebaseDatabase.getInstance().getReference().
                        child("Database").
                        child("userProfiles").
                        child(MainActivity.modelRegisterUser.accountType).
                        child(spf.getString("emailAddress","")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelRegisterUser modelRegisterUser1=snapshot.getValue(ModelRegisterUser.class);
                        MainActivity.modelRegisterUser=modelRegisterUser1;

                        spf.edit().putString("accountType",""+MainActivity.modelRegisterUser.accountType).commit();
                        spf.edit().putString("userName",""+MainActivity.modelRegisterUser.userName).commit();
                        spf.edit().putString("emailAddress",""+MainActivity.modelRegisterUser.emailAddress).commit();
                        spf.edit().putString("password",""+MainActivity.modelRegisterUser.password).commit();
                        spf.edit().putString("address",""+MainActivity.modelRegisterUser.address).commit();

                        loadPrifilePic();
                        initTypeOfUi();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        }

        this.binding.navLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if(R.id.nav_left_update_profile==menuItem.getItemId()){
                    ActivityRegister.isUpdateProfile=true;
                    finish();
                    startActivity(new Intent(MainActivity.this,ActivityRegister.class));
                }
                if (R.id.nav_left_share_app == menuItem.getItemId()) {
                    MainActivity.this.binding.dl.closeDrawers();
                    try {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("text/plain");
                        intent.setPackage("com.whatsapp");
                        intent.putExtra("android.intent.extra.TEXT", "Mess management system. \nhttp://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                        MainActivity.this.startActivity(Intent.createChooser(intent, "Share with"));
                    } catch (Exception unused) {
                        Toast.makeText(MainActivity.this, "Unable to send this try again",Toast.LENGTH_LONG).show();
                    }
                }

                if (R.id.nav_left_rate_app == menuItem.getItemId()) {
                    MainActivity.this.binding.dl.closeDrawers();
                    // MainActivity.this.inAppReview();
                }
                if (R.id.nav_left_developer == menuItem.getItemId()) {
                    MainActivity.this.binding.dl.closeDrawers();
                    Toast.makeText(MainActivity.this, "Developer : Mess_Mng.t", Toast.LENGTH_LONG).show();
                }
                if (R.id.nav_left_feedback == menuItem.getItemId()) {
                    MainActivity.this.binding.dl.closeDrawers();
                    try {
                        MainActivity.this.binding.dl.closeDrawers();
                        Intent intent2 = new Intent("android.intent.action.SEND");
                        intent2.setType("message/*");
                        intent2.setPackage("com.google.android.gm");
                        intent2.putExtra("android.intent.extra.EMAIL", new String[]{"ishika@gmail.com"});
                        intent2.putExtra("android.intent.extra.SUBJECT", "Mess management app feedback");
                        MainActivity.this.startActivity(Intent.createChooser(intent2, "Choose an Email"));
                    } catch (Exception unused3) {
                        Toast.makeText(MainActivity.this, "Unable to send this try again", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });


        binding.mkLoader.setVisibility(View.VISIBLE);

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPreferences.Editor editor=new SharedPref().createSharedPref(MainActivity.this,"AppSettings").getEditor();
                editor.putBoolean("isLoggedIn",false).commit();
                startActivity(new Intent(MainActivity.this,ActivityLogin.class));
            }
        });
    }

    private void initTypeOfUi() {
        if(MainActivity.modelRegisterUser.accountType.equals("Cutomer")){
            alert_loading=UtilUITask.getLoadingUi(MainActivity.this,"Loading...");
            getSupportFragmentManager().beginTransaction().replace(R.id.linearTypeOfUi,new FragmentCustomer1()).commit();
        }
        if(MainActivity.modelRegisterUser.accountType.equals("Manager")){
            Log.d("dvfdv","coming....");
            getSupportFragmentManager().beginTransaction().replace(R.id.linearTypeOfUi,new FragmentManager1()).commit();
        }
    }

    private void loadPrifilePic() {
        // Create a reference with an initial file path and name


        binding.tvCutomerTypeUserName.setText("Hi, "+modelRegisterUser.userName);
        binding.tvEmailAddress.setText(modelRegisterUser.emailAddress);


        Log.d("sdcsdc","userNm"+modelRegisterUser.emailAddress);




        String pattern = "HH:mm:ss.SSSZ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Log.d("sadfsdfds","X:"+date);
        binding.tvDate.setText(date);



        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("messManagement/profilePics/"+ new TaskX().replaceEmail(modelRegisterUser.emailAddress) +".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                binding.mkLoader.setVisibility(View.GONE);
                // Data for "images/island.jpg" is returns, use this as needed
                Glide.with(getApplicationContext()).load(bytes).into(binding.imgProfilePic);
                binding.imgProfilePic.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("sdfsdf","success");
        try{
            ModelMyCustomers modelMyCustomers=new ModelMyCustomers(UtilMakePayment.modelMyCustomers.userNm,UtilMakePayment.modelMyCustomers.address,UtilMakePayment.modelMyCustomers.emailAddress,UtilMakePayment.modelMyCustomers.months,UtilMakePayment.modelMyCustomers.times,UtilMakePayment.modelMyCustomers.paidAmount,MainActivity.modelRegisterUser.contactNumber);
            FirebaseDatabase.getInstance().getReference()
                    .child("Database")
                    .child("myCustomers")
                    .child(new TaskX().replaceEmail(UtilMakePayment.messManagerNm))
                    .setValue(modelMyCustomers)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("assadd","success");

                            SharedPreferences.Editor editor=new SharedPref().createSharedPref(MainActivity.this,"joinedMess").getEditor();
//                            editor.putString("months",modelMyCustomers.months).commit();
//                            editor.putString("times",modelMyCustomers.times).commit();
//                            editor.putString("paidAmount",modelMyCustomers.paidAmount).commit();
//                            editor.putString("messAddress",UtilMakePayment.modelCreatMess.messAddress).commit();
//                            editor.putString("messNm",UtilMakePayment.modelCreatMess.messName).commit();
//                            editor.putString("startAt",UtilMakePayment.modelCreatMess.startAt).commit();
//                            editor.putString("closeAt",UtilMakePayment.modelCreatMess.closeAt).commit();
//                            editor.putString("contactNumber",UtilMakePayment.modelCreatMess.contactNumber).commit();
//                            editor.putString("managerEmail",UtilMakePayment.messManagerNm).commit();
//                           editor.putString("contactNumber",MainActivity.modelRegisterUser.contactNumber).commit();
                            ModelMyJoinedMess modelMyJoinedMess=new ModelMyJoinedMess(
                                    modelMyCustomers.months
                                    ,modelMyCustomers.times
                                    ,modelMyCustomers.paidAmount
                                    ,UtilMakePayment.modelCreatMess.messAddress
                                    ,UtilMakePayment.modelCreatMess.messName,
                                    UtilMakePayment.modelCreatMess.startAt
                                    ,UtilMakePayment.modelCreatMess.closeAt,
                                    UtilMakePayment.modelCreatMess.contactNumber,
                                    UtilMakePayment.messManagerNm
                            );
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Database")
                                    .child("myJionedMess")
                                    .child(new TaskX().replaceEmail(MainActivity.modelRegisterUser.emailAddress))
                                    .setValue(modelMyJoinedMess);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("assadd","failes"+e.getMessage());
                }
            });
        }catch (Exception e){
            Log.d("assadd","failesxx"+e.getMessage());
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("sdfsdf","failes");
    }
}