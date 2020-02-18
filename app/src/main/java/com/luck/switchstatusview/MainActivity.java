package com.luck.switchstatusview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.luck.switchstatusview.statusview.IPageStatusView;
import com.luck.switchstatusview.statusview.NoticeLoadingDotView;
import com.luck.switchstatusview.statusview.PageStatusParams;
import com.luck.switchstatusview.statusview.PageStatusView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRlContent;
    private PageStatusView mStatusView;
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
        mStatusView.setPageStatusParams(new PageStatusParams.Builder()
                .pageType(IPageStatusView.TYPE_ERROR)
                .pageBtnContent("请重试")
                .pageRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLoading();
                        mHandler.sendEmptyMessageDelayed(3, 2000);
                    }
                })
                .build());
    }

    public void showEmpty() {
        mRlContent.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.setPageStatusParams(new PageStatusParams.Builder()
                .pageType(IPageStatusView.TYPE_EMPTY)
                .build());
        mHandler.sendEmptyMessageDelayed(4, 2000);
    }

    public void showWheelLoading() {
        mRlContent.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.setPageStatusParams(new PageStatusParams.Builder()
                .pageType(IPageStatusView.TYPE_LOADING)
                .build());
        mHandler.sendEmptyMessageDelayed(5, 2000);
    }

    public void showContent() {
        mRlContent.setVisibility(View.VISIBLE);
        mStatusView.setVisibility(View.GONE);
    }

    public void showLoading() {
        mRlContent.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.setPageStatusParams(new PageStatusParams.Builder()
                .pageType(IPageStatusView.TYPE_LOADING)
                .pageView(new NoticeLoadingDotView(this), null)
                .build());
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
                case 4:
                    reference.showWheelLoading();
                    break;
                case 5:
                    reference.showContent();
                    break;
            }
        }
    }
}
