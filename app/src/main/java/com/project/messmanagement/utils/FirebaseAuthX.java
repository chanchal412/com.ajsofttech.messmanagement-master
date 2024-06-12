package com.project.messmanagement.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class FirebaseAuthX {
    public void signIn(FirebaseAuth mAuth){
                mAuth.signInWithEmailAndPassword("ajs@gmail.com", "93707370")
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                           Log.d("sdfsdf","inFailes...");

                        } else {
               Log.d("sdfsdf","inFailes...");

                        }
                    }
                });
    }
    public void signUp(FirebaseAuth mAuth){
        mAuth.createUserWithEmailAndPassword("ajsoft9@gmail.com", "9370732370")
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("sdfsdf","success...");
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("sdfsdf","failes..."+task.toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("sdfsdf","inFailes..."+e.getMessage());
            }
        });
    }
}
