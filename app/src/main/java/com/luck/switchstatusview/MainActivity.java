package com.luck.switchstatusview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.luck.switchstatusview.statusview.NoticeStatusView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRlContent;
    private NoticeStatusView mStatusView;
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRlContent = findViewById(R.id.rl_content);
        mStatusView = findViewById(R.id.v_status);
        mHandler = new MyHandler(this);
        showLoading();
        mHandler.sendEmptyMessageDelayed(2, 2000);
    }

    public void showError() {
        mRlContent.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.resetData()
                .pageType(NoticeStatusView.TYPE_ERROR)
                .pageListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoading();
                        mHandler.sendEmptyMessageDelayed(3, 2000);
                    }
                }).apply();

    }

    public void showEmpty() {
        mRlContent.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.resetData()
                .pageType(NoticeStatusView.TYPE_EMPTY)
                .apply();
    }

    public void showLoading() {
        mRlContent.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.resetData().pageType(NoticeStatusView.TYPE_LOADING_LOCAL).apply();

    }


    private static class MyHandler extends WeakReferenceHandler<MainActivity> {

        public MyHandler(MainActivity reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(MainActivity reference, Message msg) {
            switch (msg.what) {
                case 1:
                    reference.showLoading();
                    break;
                case 2:
                    reference.showError();
                    break;
                case 3:
                    reference.showEmpty();
                    break;
            }
        }
    }
}
