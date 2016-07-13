package com.wuxinwudai.adlg;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * ContainerDialog 类为容器对话框类
 * @author 吾心无待 于 2016年2月26日
 */
public class ContainerDialog extends Dialog {


    //region 构造函数

    /**
     * 公有构造函数，初始化 ContainerDialog 类的一个新实例
     * @param context 上下文对象
     */
    public ContainerDialog(Context context) {
        this(context,R.style.da_ContainerDialog);
    }

    /**
     * 公有构造函数，初始化 ContainerDialog 类的一个新实例
     * @param context 上下文对象
     * @param themeResId 应用主题ID
     */
    public ContainerDialog(Context context,@StyleRes int themeResId) {
        super(context,themeResId);
    }

    //endregion

    //region 私有成员
    protected OnConfirmClickListener mConfirmClickListener;//确定按钮单击事件监听器
    protected OnCancelClickListener mCancelClickListener;//取消按钮单击事件监听器
    protected View mDialogView;//对话框 View
    protected TextView mtvTitle;//标题 View
    protected TextView mtvMessage;//消息 TextView
    protected TextView mtvCancel;//取消 TextView
    protected TextView mtvConfirm;//确定 TextView
    protected String mConfirmText = "确定";//确定文本
    protected String mCancelText = "取消";//取消文本
    protected String mTitle;//标题文本
    protected String mMessage;//消息文本
    private AnimationSet mModalInAnim;//进入动画
    private AnimationSet mModalOutAnim;//退出动画
    protected int mPadding = 15;//左右两侧留空白
    protected int mContainerHeight = LinearLayout.LayoutParams.WRAP_CONTENT;//容器高度,默认自适应
    /**
     * 初始化视图控件
     */
    private void initView(){
        setContentView(R.layout.layout_container_dialog);
        setPadding(mPadding);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mtvTitle = (TextView)findViewById(R.id.tv_title);
        mtvCancel = (TextView)findViewById(R.id.tv_cancel);
        mtvConfirm = (TextView)findViewById(R.id.tv_confirm);
        mtvMessage = (TextView)findViewById(R.id.tv_message);
        mflContainerView = (LinearLayout)findViewById(R.id.fl_container);
        setChildView(mChildView);
        setTitle(mTitle);
        setMessage(mMessage);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        mtvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelClickListener != null){
                    mCancelClickListener.cancel(ContainerDialog.this);
                }
                dismiss();
            }
        });
        mtvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmClickListener != null){
                    mConfirmClickListener.confirm(ContainerDialog.this);
                }else {
                    dismiss();
                }
            }
        });
        mModalInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        ContainerDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置 TextView 文本
     * @param tv TextView
     * @param txt 要设置的文本
     */
    protected void setText(TextView tv,String txt){
        if(tv != null && txt != null){
            tv.setText(txt);
        }
    }
    //endregion

    //region 受保护成员和方法
    protected LinearLayout mflContainerView;//容器视图
    protected View mChildView;//子视图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onStart() {
        getWindow().getDecorView().getBackground().setAlpha(255);
        mDialogView.startAnimation(mModalInAnim);
        super.onStart();
    }


    //endregion

    //region 公有方法

    @Override
    public void dismiss() {
        mDialogView.startAnimation(mModalOutAnim);
    }

    /**
     * 设置确定按钮单击事件监听
     * @param mConfirmClickListener 确定事件监听器
     * @return 返回对话框本身
     */
    public ContainerDialog setOnConfirmClickListener(OnConfirmClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    /**
     * 设置取消按钮单击事件监听
     * @param mCancelClickListener 取消事件监听器
     * @return 返回对话框本身
     */
    public ContainerDialog setOnCancelClickListener(OnCancelClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
        return this;
    }

    /**
     * 设置确定按钮文本
     * @param confirmText 确定按钮文本
     * @return 对话框
     */
    public ContainerDialog setConfirmText(String confirmText){
        this.mConfirmText = confirmText;
        setText(mtvConfirm,confirmText);
        return this;
    }

    /**
     * 获取确定按钮文本
     * @return 确定按钮文本
     */
    public String getConfirmText(){
        return mConfirmText;
    }

    /**
     * 设置取消按钮文本
     * @param cancelText 取消按钮文本
     * @return 对话框
     */
    public ContainerDialog setCancelText(String cancelText){
        this.mCancelText = cancelText;
        setText(mtvCancel,cancelText);
        return this;
    }

    /**
     * 获取取消按钮文本
     * @return 取消按钮文本
     */
    public String getCancelText(){
        return mCancelText;
    }

    /**
     * 设置标题文本
     * @param title 标题文本
     * @return 对话框
     */
    public ContainerDialog setTitle(String title){
        this.mTitle = title;
        setText(mtvTitle,title);
        return this;
    }

    /**
     * 获取标题文本
     * @return 标题文本
     */
    public String getTitle(){
        return mTitle;
    }

    /**
     * 获取消息文本
     * @return 消息文本
     */
    public String getMessage(){
        return mMessage;
    }

    /**
     * 设置消息文本
     * @param message 消息文本
     * @return 对话框
     */
    public ContainerDialog setMessage(String message){
        this.mMessage = message;
        if (mtvMessage !=null){
            if (message == null || message == ""){
                mtvMessage.setVisibility(View.GONE);
            }else {
                mtvMessage.setVisibility(View.VISIBLE);
                mtvMessage.setText(message);
            }
        }
        return this;
    }

    /**
     * 获取对话框边距
     * @return 两边边距
     */
    public int getPadding(){
        return mPadding;
    }

    /**
     * 设置对话框两侧边距
     * @param padding 对话框两边间距
     * @return 对话框本身
     */
    public ContainerDialog setPadding(int padding){
        this.mPadding = padding;
        Window window = getWindow();
        if (window!=null){
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            window.getAttributes().width = (int) (displayMetrics.widthPixels - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding*2, displayMetrics));
        }
        return this;
    }

    /**
     * 为视图的容器视图挺添加视图
     * @param childView 设置容器要添加的 View
     * @return 对话框本身
     */
    public ContainerDialog setChildView(View childView){
        mChildView = childView;
        if (mChildView != null && mflContainerView != null){
            mflContainerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContainerHeight));
            mflContainerView.addView(mChildView);
            mflContainerView.setVisibility(View.VISIBLE);
        }else if(mflContainerView != null){
            mflContainerView.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 设置容器的高度
     * @param height 容器高度
     * @return 对话框本身
     */
    public ContainerDialog setContainerHeight(int height){
        this.mContainerHeight = height;
        if (mflContainerView!=null){
            mflContainerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContainerHeight));
        }
        return this;
    }

    /**
     * 工厂方法创建 ContainerDialog 类的新实例
     * @param ctx 上下文对象
     * @return 容器对话框
     */
    public static ContainerDialog create(Context ctx){
        return new ContainerDialog(ctx);
    }

    //endregion

    //region 内部接口
    /**
     * OnConfirmClickListener 接口为提示对话框确定按钮单击事件监听器
     */
    public interface OnConfirmClickListener{
        /**
         * 单击确定按钮
         * @param containerDialog ContainerDialog 对话框
         */
        void confirm(ContainerDialog containerDialog);
    }

    /**
     * OnCancelClickListener 接口为提示对话框取消按钮单击事件监听器
     */
    public interface OnCancelClickListener{
        /**
         * 单击取消按钮
         * @param containerDialog ContainerDialog 对话框
         */
        void cancel(ContainerDialog containerDialog);
    }
    //endregion
}
