package com.wuxinwudai.adlg;

import android.view.View;

/**
 * @author 吾心无待 于2016年04月07日
 */
public class ItemInfo {
    private String mTitle;
    private View.OnClickListener mOnClickListener;

    /**
     * 获取标题
     * @return 标题
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * 获取单击事件监听器
     * @return 监听器
     */
    public View.OnClickListener getListener() {
        return mOnClickListener;
    }

    /**
     * 设置单击事件监听器
     * @param listener 监听器
     */
    public void setOnClickListener(View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    /**
     * 构造函数，初始化 ItemInfo 类的一个新实例
     * @param title 标题
     * @param listener 单击事件监听器
     */
    public ItemInfo(String title, View.OnClickListener listener) {
        this.mTitle = title;
        this.mOnClickListener = listener;
    }
}
