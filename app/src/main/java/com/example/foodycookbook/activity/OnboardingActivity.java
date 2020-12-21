package com.example.foodycookbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.foodycookbook.MainActivity;
import com.example.foodycookbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnboardingActivity extends AppCompatActivity {

//    private static int SPLASH_TIMEOUT = 3350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To make the activity go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);

        FloatingActionButton button = findViewById(R.id.continue_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent splashOpen = new Intent(OnboardingActivity.this, MainActivity.class);
                startActivity(splashOpen);
            }
        });
    }
}