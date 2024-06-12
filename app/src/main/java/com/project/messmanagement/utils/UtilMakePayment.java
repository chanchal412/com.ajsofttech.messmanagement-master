package com.project.messmanagement.utils;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.MainActivity;
import com.project.messmanagement.models.ModelCreatMess;
import com.project.messmanagement.models.ModelMyCustomers;
import com.razorpay.Checkout;

import org.json.JSONObject;
public class UtilMakePayment {
        public static ModelMyCustomers modelMyCustomers;
        public static String messManagerNm;
        public static ModelCreatMess modelCreatMess;
        int months,times;
        public  AlertDialog makePayment(Context context, Float price ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.inflater_make_payment, (ViewGroup) null);
        builder.setView(inflate);
        AlertDialog show = builder.show();
                Spinner sp=inflate.findViewById(R.id.spinnerMonths);
                String arrMonths[]={"1 month","2 month","3 month","4 month","5 month","6 month","7 month","8 month","9 month","10 month","11 month","12 month",};
                ArrayAdapter<String> ad=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arrMonths);
                sp.setAdapter(ad);

                Spinner spx=inflate.findViewById(R.id.spinnerTimes);
                String arrTimes[]={"daily 1 time","daily 2 time","daily 3 time"};
                ArrayAdapter<String> adx=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arrTimes);
                spx.setAdapter(adx);

                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                              months=i+1;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                });
                spx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                times=times+1;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                });

                inflate.findViewById(R.id.tvMakePayment).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                float val=price*times*(months*30);
                                modelMyCustomers =new ModelMyCustomers(MainActivity.modelRegisterUser.userName,
                                        MainActivity.modelRegisterUser.address,
                                        MainActivity.modelRegisterUser.emailAddress,Integer.toString(months),Integer.toString(times),Float.toString(val)
                                ,MainActivity.modelRegisterUser.contactNumber);

                              payNow(val);
                        }
                });

        return show;
    }


        private void payNow(float price) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_OdGWUL99PSOPId");
                checkout.setImage(R.drawable.ic_launcher_foreground);
                try {
                        JSONObject options = new JSONObject();
                        options.put("name", "Mess Management App");
                        options.put("description", "Reference No. #123456");
                        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                        // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                        options.put("theme.color", "#3A3845");
                        options.put("currency", "INR");
                        options.put("amount", price * 100);//300 X 100
                        options.put("prefill.email", "test_address_@gmail.com");
                        options.put("prefill.contact", "+91 0000000000");
                        checkout.open(MainActivity.activity, options);

                       // addMyOrders();
                } catch (Exception e) {
                        Log.e("TAG", "Error in starting Razorpay Checkout", e);
                }
        }


}
