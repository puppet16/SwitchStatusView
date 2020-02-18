package com.luck.switchstatusview.statusview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luck.switchstatusview.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020/2/3
 * 描 述 : 消息模块的等待状态
 * ============================================================
 **/
public class NoticeStatusView extends RelativeLayout {
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_ERROR = 2;
    public static final int TYPE_LOADING_ALL = 3;
    public static final int TYPE_LOADING_LOCAL = 4;

    @IntDef({TYPE_LOADING_ALL, TYPE_LOADING_LOCAL, TYPE_ERROR, TYPE_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private View mRootLoadingAll;
    private TextView mTvLoadingAllContent;

    private NoticeLoadingLocalView mViewLoadingLocal;

    private View mRootError;
    private ImageView mIvError;
    private TextView mTvErrorContent;

    private View mRootEmpty;
    private ImageView mIvEmpty;
    private TextView mTvEmpty;
    private TextView mTvErrorTips;

    private PageParams mData;

    public NoticeStatusView(@NonNull Context context) {
        super(context);
        initView();
    }

    public NoticeStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NoticeStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.layout_notice_status, this);
    }



    /**
     * 初始化参数,设置参数前必调
     *
     * @return
     */
    public NoticeStatusView resetData() {
        mData = new PageParams(TYPE_EMPTY, "","", -1, null);
        hideAllView();
        return this;
    }

    /**
     * 设置页面显示类型，必调
     * @param type
     * @return
     */
    public NoticeStatusView pageType(@Type int type) {
        mData.type = type;
        return this;
    }

    public NoticeStatusView pageContent(String content) {
        mData.content = content;
        return this;
    }

    public NoticeStatusView pageErrorContent(String content) {
        mData.errorContent = content;
        return this;
    }

    public NoticeStatusView pagePicResId(int picResId) {
        mData.picResId = picResId;
        return this;
    }

    public NoticeStatusView pageListener(OnClickListener onClickListener) {
        mData.listener = onClickListener;
        return this;
    }

    /**
     * 应用参数设置，必调
     */
    public void apply() {
        if (mData == null) return;
        String content = mData.content;
        String errorContent = mData.errorContent;
        int picResId = mData.picResId;
        OnClickListener listener = mData.listener;
        switch (mData.type) {
            default:
            case TYPE_EMPTY:
                if (mRootEmpty == null) {
                    ViewStub emptyStub = findViewById(R.id.vs_status_empty);
                    emptyStub.setLayoutResource(R.layout.layout_notice_status_empty);
                    mRootEmpty = emptyStub.inflate();
                    mTvEmpty = findViewById(R.id.notice_empty_tv_content);
                    mIvEmpty = findViewById(R.id.notice_empty_iv_pic);

                } else {
                    mRootEmpty.setVisibility(VISIBLE);
                }
                if (!TextUtils.isEmpty(content)) {
                    mTvEmpty.setText(content);
                }
                if (picResId != -1) {
                    mIvEmpty.setImageResource(picResId);
                }
                break;
            case TYPE_LOADING_ALL:
                if (mRootLoadingAll == null) {
                    ViewStub loadingAllStub = findViewById(R.id.vs_status_loading_all);
                    loadingAllStub.setLayoutResource(R.layout.layout_notice_status_loading_all);
                    mRootLoadingAll = loadingAllStub.inflate();
                    mTvLoadingAllContent = findViewById(R.id.notice_loading_all_tv_content);
                } else {
                    mRootLoadingAll.setVisibility(VISIBLE);
                }
                if (!TextUtils.isEmpty(content)) {
                    mTvLoadingAllContent.setText(content);
                }
                break;
            case TYPE_LOADING_LOCAL:
                if (mViewLoadingLocal == null) {
                    ViewStub loadingLocalStub = findViewById(R.id.vs_status_loading_local);
                    loadingLocalStub.setLayoutResource(R.layout.layout_notice_status_loading_local_view);
                    loadingLocalStub.inflate();
                    mViewLoadingLocal = findViewById(R.id.notice_loading_local_view);
                } else {
                    mViewLoadingLocal.setVisibility(VISIBLE);
                }
                if (!TextUtils.isEmpty(content)) {
                    mViewLoadingLocal.setContent(content);
                }
                mViewLoadingLocal.startAnimator();
                break;
            case TYPE_ERROR:
                if (mRootError == null) {
                    ViewStub errorStub = findViewById(R.id.vs_status_error);
                    errorStub.setLayoutResource(R.layout.layout_notice_status_error);
                    mRootError = errorStub.inflate();
                    mTvErrorContent = findViewById(R.id.notice_error_tv_content);
                    mIvError = findViewById(R.id.notice_error_iv_pic);
                    mTvErrorTips = findViewById(R.id.tv_error_tips);
                    mTvErrorTips.setText(errorContent);
                    mTvErrorTips.setVisibility(TextUtils.isEmpty(errorContent)?GONE:VISIBLE);
                } else {
                    mRootError.setVisibility(VISIBLE);
                }
                if (!TextUtils.isEmpty(content)) {
                    mTvErrorContent.setText(content);
                }
                if (picResId != -1) {
                    mIvError.setImageResource(picResId);
                }
                if (listener != null) {
                    mTvErrorContent.setOnClickListener(listener);
                }
                break;
        }

    }


    /**
     * 设置为不可见时要将局部加载的动画关闭
     *
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        if (mData != null && mData.type == TYPE_LOADING_LOCAL && visibility == GONE && mViewLoadingLocal != null)
            mViewLoadingLocal.cancelAnimator();
        super.setVisibility(visibility);
    }

    /**
     * 隐藏所有状态页面
     */
    private void hideAllView() {
        if (mRootLoadingAll != null) {
            mRootLoadingAll.setVisibility(GONE);
        }

        if (mRootError != null) {
            mRootError.setVisibility(GONE);
        }
        if (mViewLoadingLocal != null) {
            mViewLoadingLocal.cancelAnimator();
            mViewLoadingLocal.setVisibility(GONE);
        }
        if (mRootEmpty != null) {
            mRootEmpty.setVisibility(GONE);
        }
    }

    public class PageParams {
        public @Type
        int type;
        public String content;
        public String errorContent;
        public int picResId;
        public OnClickListener listener;

        public PageParams(int type, String content, String errorContent, int picResId, OnClickListener listener) {
            this.type = type;
            this.content = content;
            this.errorContent = errorContent;
            this.picResId = picResId;
            this.listener = listener;
        }
    }
}
