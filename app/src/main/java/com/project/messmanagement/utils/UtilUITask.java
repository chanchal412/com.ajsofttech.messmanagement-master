package com.project.messmanagement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.project.messmanagement.R;
import com.google.android.material.snackbar.Snackbar;

public class UtilUITask {
    public static String NO_INTERNET_STATEMENT = "Please Check Your Internet Connection...";
    public static void getSnackBar(View view, String str) {
        if (view != null && str != null) {
            Snackbar.make(view, (CharSequence) str, Snackbar.LENGTH_LONG).show();
        }
    }

    public static void getToastMessage(Context context, String str) {
        if (context != null) {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        }
    }

    public static AlertDialog getLoadingUi(Context context, String str) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.inflater_loading, (ViewGroup) null);
            builder.setView(inflate);
            AlertDialog show = builder.show();

        ((TextView) inflate.findViewById(R.id.tvMessage)).setText(str);
        return show;
    }

//    @SuppressLint("WrongConstant")
//    public static boolean InternetChecker(Context context) {
//        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
//    }

//    public void noInternetUi(Context context) {
//        ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//        if (netInfo == null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
//            View inflate = LayoutInflater.from(context).inflate(R.layout.no_internet, (ViewGroup) null);
//            builder.setView(inflate);
//            final AlertDialog show = builder.show();
//            show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            ((TextView) inflate.findViewById(R.id.tvClose)).setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    show.dismiss();
//                }
//            });
//        }else{
//            Log.d("vbchvd","else");
//            new UtilUITask().getLoadingUi(context);
//        }
//    }
//    public void getLoadingUi(Context context) {
//        ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
//        View inflate = LayoutInflater.from(context).inflate(R.layout.ui_loading, (ViewGroup) null);
//        builder.setView(inflate);
//        final AlertDialog show = builder.show();
//        MainActivity.dialog=show;
//         show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//    }
//
//    public AlertDialog getBackpressedUi(Context context, Activity activity) {
//        ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
//        View inflate = LayoutInflater.from(context).inflate(R.layout.ui_backpressed, (ViewGroup) null);
//        builder.setView(inflate);
//        final AlertDialog show = builder.show();
//        MainActivity.dialog=show;
//        show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        inflate.findViewById(R.id.tvYes).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.finish();
//            }
//        });
//        inflate.findViewById(R.id.tvNo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //MainActivity.x=0;
//                show.dismiss();
//            }
//        });
//        return show;
//    }
}
