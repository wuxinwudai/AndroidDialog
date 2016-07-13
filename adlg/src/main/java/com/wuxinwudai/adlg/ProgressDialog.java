package com.wuxinwudai.adlg;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ProgressDialog 类为进度条对话框类
 * @author 吾心无待 于 2016年6月20日
 */
public class ProgressDialog extends Dialog {
    private int mPadding = 30;//左右两侧空白
    private String mProgressText = "数据加载中...";
    private TextView mTitleView;
    private FrameLayout mflProgress;
    private FrameLayout mflSuccess;
    private FrameLayout mflError;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mSuccessBowAnim;
    private Animation mErrorInAnim;
    private AnimationSet mErrorXInAnim;
    private ImageView mivError;
    private SuccessTickView mSuccessTickView;
    private void initView(){
        setContentView(R.layout.layout_progress_dialog);
        setPadding(mPadding);
        setProgressText(mProgressText);
        mTitleView = (TextView)findViewById(R.id.tv_title);
        mflProgress = (FrameLayout)findViewById(R.id.fl_progress);
        mflSuccess = (FrameLayout)findViewById(R.id.fl_success);
        mSuccessLeftMask = mflSuccess.findViewById(R.id.mask_left);
        mSuccessRightMask = mflSuccess.findViewById(R.id.mask_right);
        mSuccessTickView = (SuccessTickView)findViewById(R.id.stv);
        mflError = (FrameLayout)findViewById(R.id.fl_error);
        mivError = (ImageView)mflError.findViewById(R.id.iv_error);
        mSuccessLayoutAnimSet = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.success_mask_layout);
        mSuccessBowAnim = AnimationUtils.loadAnimation(getContext(),R.anim.success_bow_roate);
        mErrorInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet)AnimationUtils.loadAnimation(getContext(), R.anim.error_x_in);
        this.setCancelable(false);
    }

    //region 构造函数
    /**
     * 构造函数，初始化 ProgressDialog 类的一个新实例
     * @param context 上下文对象
     */
    public ProgressDialog(Context context) {
        this(context,R.style.adlg_progress_dialog);
    }

    /**
     * 构造函数，初始化 ProgressDialog 类的一个新实例
     * @param context 上下文对象
     * @param themeResId 主题id
     */
    public ProgressDialog(Context context,@StyleRes int themeResId) {
        super(context, themeResId);
    }
    //endregion

    //region 公有方法
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
    public ProgressDialog setPadding(int padding){
        this.mPadding = padding;
        Window window = getWindow();
        if (window!=null){
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            window.getAttributes().width = (int) (displayMetrics.widthPixels - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding*2, displayMetrics));
        }
        return this;
    }

    /**
     * 获取进度文本
     * @return 进度文本
     */
    public String getProgressText() {
        return mProgressText;
    }

    /**
     * 设置进度文本，并重置 ProgressDialog 的状态
     * @param progressText 进度文本
     * @return 进度文本
     */
    public ProgressDialog setProgressText(String progressText) {
        mProgressText = progressText;
        return this;
    }
    //endregion


    /**
     * 显示进度成功
     * @param successText 进度加载成功显示的提示
     */
    public void success(String successText){
        mTitleView.setText(successText);
        mflProgress.setVisibility(View.GONE);
        mflError.setVisibility(View.GONE);
        mflSuccess.setVisibility(View.VISIBLE);
        mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
        mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
        mSuccessTickView.startTickAnim();
        mSuccessRightMask.startAnimation(mSuccessBowAnim);
        setCancelable(true);
    }

    /**
     * 显示数据加载成功
     */
    public void success(){
        success("数据加载成功！");
    }
    /**
     * 显示进度加载失败
     * @param errorText 进度加载失败显示的文本
     */
    public void error(String errorText){
        mTitleView.setText(errorText);
        mflError.setVisibility(View.VISIBLE);
        mflProgress.setVisibility(View.GONE);
        mflSuccess.setVisibility(View.GONE);
        mflError.startAnimation(mErrorInAnim);
        mivError.startAnimation(mErrorXInAnim);
        setCancelable(true);
    }

    /**
     * 显示数据加载成功
     */
    public void error(){
        error("数据加载失败！");
    }

    @Override
    public void show() {
        super.show();
        mTitleView.setText(mProgressText);
        mflProgress.setVisibility(View.VISIBLE);
        mflSuccess.setVisibility(View.GONE);
        mflError.clearAnimation();
        mivError.clearAnimation();
        mSuccessTickView.clearAnimation();
        mSuccessLeftMask.clearAnimation();
        mSuccessRightMask.clearAnimation();
        setCancelable(false);
    }




    @Override
    protected void onStart() {
        getWindow().getDecorView().getBackground().setAlpha(255);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 工厂方法创建 ProgressDialog 类的一个新实例
     * @param context 上下文对象
     * @return ProgressDialog
     */
    public static ProgressDialog create(Context context){
        return new ProgressDialog(context);
    }
}
