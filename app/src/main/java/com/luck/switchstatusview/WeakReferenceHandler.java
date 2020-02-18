package com.luck.switchstatusview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2019-12-10 17:30
 * 描 述 : 弱引用的handler，实现类必须为static，重写handleMessage(T reference, Message msg)方法即可
 * ============================================================
 **/
public abstract class WeakReferenceHandler<T> extends Handler {
    private WeakReference<T> mReference;

    public WeakReferenceHandler(T reference) {
        super();
        mReference = new WeakReference<T>(reference);
    }

    public WeakReferenceHandler(T reference, Looper looper) {
        super(looper);
        mReference = new WeakReference<T>(reference);
    }

    @Override
    public final void handleMessage(Message msg) {
        if (mReference.get() == null)
            return;
        try {
            handleMessage(mReference.get(), msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void handleMessage(T reference, Message msg);
}
