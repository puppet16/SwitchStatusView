package com.luck.switchstatusview.statusview;

import android.widget.RelativeLayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020/2/18
 * 描 述 :
 * ============================================================
 **/
public interface IPageStatusView {

    RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS =
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
    int TYPE_EMPTY = 1;
    int TYPE_ERROR = 2;
    int TYPE_LOADING = 3;

    /**
     * 显示页面的类型
     */
    @IntDef({TYPE_LOADING, TYPE_ERROR, TYPE_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    /**
     * 设置显示页面参数
     * @param params
     * @return
     */
    void setPageStatusParams(PageStatusParams params);

}
