package com.project.messmanagement.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class PickImage {

    public void pickImage(Activity activity){

        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.Q){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
        }else {
            ImagePicker.Companion.with(activity)
                    .crop(1,2)//Crop image(Optional), Check Customization for more option
                    .galleryOnly()
                    .start();
        }

        // old code
//        try {
//            ActivityQuotesEditor.binding.tvSelectImage.setVisibility(View.GONE);
//            Intent intent = new Intent("android.intent.action.GET_CONTENT");
//            intent.setType("image/*");
//            activity.startActivityForResult(intent, ActivityQuotesEditor.request_code);
//        } catch (Exception e) {
//            Context applicationContext = activity.getApplicationContext();
//            Toast.makeText(applicationContext, "" + e, Toast.LENGTH_LONG).show();
//        }
    }
}
