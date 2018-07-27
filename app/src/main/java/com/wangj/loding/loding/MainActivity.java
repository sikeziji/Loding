package com.wangj.loding.loding;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mButton, dis;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new LoadingDialog(MainActivity.this);
                dialog.setLoadingBuilder(new TestBuilder())
                        .setLoadingColor(Color.parseColor("#ff5305"))
                        .setHintTextColor(Color.GRAY)
                        .setDialogBackgroundColor(Color.parseColor("#cc111111"))
                        .show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(5000);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}
