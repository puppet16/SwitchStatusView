package com.luck.switchstatusview.statusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luck.switchstatusview.R;


/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020/2/3
 * 描 述 : 消息模块的等待状态
 * ============================================================
 **/
public class PageStatusView extends RelativeLayout implements IPageStatusView {


    //等待页面相差
    private View mRootLoading;
    private TextView mTvLoadingContent;
    //错误页面相关
    private View mRootError;
    private ImageView mIvError;
    private TextView mTvErrorRetry;
    private TextView mTvErrorTips;
    //空页面相关
    private View mRootEmpty;
    private ImageView mIvEmpty;
    private TextView mTvEmpty;

    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;

    private PageStatusParams mData;
    private LayoutInflater mInflater;

    public PageStatusView(@NonNull Context context) {
        this(context, null);
    }

    public PageStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PageStatusView);
        mEmptyViewResId = a.getResourceId(R.styleable.PageStatusView_emptyView, R.layout.layout_page_status_empty);
        mErrorViewResId = a.getResourceId(R.styleable.PageStatusView_errorView, R.layout.layout_page_status_error);
        mLoadingViewResId = a.getResourceId(R.styleable.PageStatusView_loadingView, R.layout.layout_page_status_loading);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
    }

    /**
     * 设置参数
     *
     * @return
     */
    @Override
    public void setPageStatusParams(PageStatusParams params) {
        if (params == null) return;
        mData = params;
        switch (mData.getType()) {
            default:
            case TYPE_EMPTY:
                showEmpty();
                break;
            case TYPE_LOADING:
                showLoading();
                break;
            case TYPE_ERROR:
                showError();
                break;
        }
    }

    /**
     * 控制容器内某View显示，其它隐藏
     *
     * @param viewId
     */
    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }

    private void showEmpty() {
        if (mRootEmpty == null) {
            if (mData.getView() == null) {
                mRootEmpty = mInflater.inflate(mEmptyViewResId, null);
                addView(mRootEmpty, 0, DEFAULT_LAYOUT_PARAMS);
            } else {
                mRootEmpty = mData.getView();
                if(mData.getLayoutParams() == null) {
                    addView(mRootEmpty, 0, DEFAULT_LAYOUT_PARAMS);
                } else {
                    addView(mRootEmpty, 0, mData.getLayoutParams());
                }
            }
            mTvEmpty = findViewById(R.id.page_empty_content_view);
            mIvEmpty = findViewById(R.id.page_empty_picture_view);
        }
        showViewById(mRootEmpty.getId());
        if (mTvEmpty != null && !TextUtils.isEmpty(mData.getContent())) {
            mTvEmpty.setText(mData.getContent());
        }
        if (mIvEmpty != null && mData.getPicResId() != -1) {
            mIvEmpty.setImageResource(mData.getPicResId());
        }
    }

    /**
     * 页面显示逻辑
     * 若有这个View则根据viewId显示，隐藏其它view,设置页面内容
     * 若没有这个view，且没通过参数传过来，则使用布局方式add进容器
     */
    private void showError() {
        if (mRootError == null) {
            if (mData.getView() == null) {
                mRootError = mInflater.inflate(mErrorViewResId, null);
                addView(mRootError, 0, DEFAULT_LAYOUT_PARAMS);
            } else {
                mRootError = mData.getView();
                if(mData.getLayoutParams() == null) {
                    addView(mRootError, 0, DEFAULT_LAYOUT_PARAMS);
                } else {
                    addView(mRootError, 0, mData.getLayoutParams());
                }
            }
            mTvErrorRetry = findViewById(R.id.page_error_retry_view);
            mIvError = findViewById(R.id.page_error_picture_view);
            mTvErrorTips = findViewById(R.id.page_error_content_view);

        }
        showViewById(mRootError.getId());
        if (mTvErrorTips != null && !TextUtils.isEmpty(mData.getContent())) {
            mTvErrorTips.setVisibility(VISIBLE);
            mTvErrorTips.setText(mData.getContent());
        }
        if (mTvErrorRetry != null) {
            if (mData.getListener() != null) {
                mTvErrorRetry.setOnClickListener(mData.getListener());
            }
            if(!TextUtils.isEmpty(mData.getBtnContent())) {
                mTvErrorRetry.setText(mData.getBtnContent());
            }
        }
        if (mIvError != null && mData.getPicResId() != -1) {
            mIvError.setImageResource(mData.getPicResId());
        }
    }

    private void showLoading() {
        if (mRootLoading == null) {
            if (mData.getView() == null) {
                mRootLoading = mInflater.inflate(mLoadingViewResId, null);
                addView(mRootLoading, 0, DEFAULT_LAYOUT_PARAMS);
            } else {
                mRootLoading = mData.getView();
                if(mData.getLayoutParams() == null) {
                    addView(mRootLoading, 0, DEFAULT_LAYOUT_PARAMS);
                } else {
                    addView(mRootLoading, 0, mData.getLayoutParams());
                }
            }
            mTvLoadingContent = findViewById(R.id.page_loading_content_view);
        }
        showViewById(mRootLoading.getId());
        if (!TextUtils.isEmpty(mData.getContent()) && mTvLoadingContent != null) {
            mTvLoadingContent.setText(mData.getContent());
        }
    }
}
