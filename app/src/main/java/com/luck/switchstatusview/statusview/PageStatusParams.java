package com.luck.switchstatusview.statusview;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020/2/18
 * 描 述 :
 * ============================================================
 **/
public class PageStatusParams {
    private @IPageStatusView.Type int type;//页面显示类型
    private String content;//页面显示文本
    private String btnContent;//页面按钮上的文本
    private int picResId;//页面图片资源ID
    private View view;//要显示的页面
    private RelativeLayout.LayoutParams layoutParams;//要显示的页面的布局参数
    private View.OnClickListener listener;//页面上按钮的点击事件


    private PageStatusParams(Builder builder) {
        this.type = builder.type;
        this.content = builder.content;
        this.btnContent = builder.btnContent;
        this.picResId = builder.picResId;
        this.listener = builder.listener;
        this.view = builder.view;
        this.layoutParams = builder.layoutParams;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getBtnContent() {
        return btnContent;
    }

    public int getPicResId() {
        return picResId;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public View getView() {
        return view;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    /**
     * 实现Builder构建类，用于创建PageStatusParams
     */
    public static class Builder {
        public @IPageStatusView.Type int type;
        public String content;
        public String btnContent;
        public int picResId;
        public View view;
        private RelativeLayout.LayoutParams layoutParams;
        public View.OnClickListener listener;

        public Builder() {
            type = IPageStatusView.TYPE_EMPTY;
            content = "";
            btnContent = "";
            picResId = -1;
            listener = null;
            view = null;
            layoutParams = null;
        }

        public PageStatusParams build() {
            return new PageStatusParams(this);
        }

        /**
         * 设置页面显示类型，必调
         *
         * @param type
         * @return
         */
        public Builder pageType(@IPageStatusView.Type int type) {
            this.type = type;
            return this;
        }

        public Builder pageContent(String content) {
            this.content = content;
            return this;
        }

        public Builder pageBtnContent(String content) {
            this.btnContent = content;
            return this;
        }

        public Builder pagePicResId(int picResId) {
            this.picResId = picResId;
            return this;
        }

        public Builder pageView(View view, RelativeLayout.LayoutParams params) {
            this.view = view;
            this.layoutParams = params;
            return this;
        }

        public Builder pageRetryListener(View.OnClickListener onClickListener) {
            this.listener = onClickListener;
            return this;
        }
    }
}
