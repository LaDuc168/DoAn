package com.example.minh.doan.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.minh.doan.MainActivity;
import com.example.minh.doan.R;

public class KhoiDongActivity extends AppCompatActivity {


    ImageView imgNen,imgChinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoi_dong);
        init();
    }

    private void init() {
        imgNen= (ImageView) findViewById(R.id.imgNen);
        imgChinh= (ImageView) findViewById(R.id.imgchinh);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        imgNen.startAnimation(animation);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.anim_xoay);
        imgChinh.startAnimation(animation1);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                startActivity(new Intent(KhoiDongActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }
}
