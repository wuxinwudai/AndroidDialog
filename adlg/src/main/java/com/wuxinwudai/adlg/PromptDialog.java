package com.wuxinwudai.adlg;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * PromptDialog 类为提示输入对话框类
 * @author 吾心无待 于 2016年2月26日
 */
public final class PromptDialog extends ContainerDialog {

    //region 构造函数
    /**
     * 公有构造函数，初始化 PromptDialog 类的一个新实例
     * @param context 上下文对象
     */
    public PromptDialog(Context context) {
        super(context);
    }

    /**
     * 公有构造函数，初始化 PromptDialog 类的一个新实例
     * @param context 上下文对象
     * @param themeResId 主题资源 ID
     */
    public PromptDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    //endregion

    //region 私有成员
    private EditText metInput;//输入文本框
    private String inputText;//输入的文本
    private String inputHint;//输入提示文本
    //endregion

    //region 公有方法

    /**
     * 获取输入文本
     * @return 输入文本
     */
    public String getInputText(){
        return metInput != null ? metInput.getText().toString(): inputText;
    }

    /**
     * 设置输入文本
     * @param text 文本内容
     * @return 对话框本身
     */
    public PromptDialog setInputText(String text){
        inputText = text;
        if(metInput != null && text != null){
            metInput.setText(text);
        }
        return this;
    }

    /**
     * 获取输入提示文本
     * @return 输入提示文本
     */
    public String getInputHint(){
        return metInput != null ? metInput.getHint().toString() : inputHint;
    }

    /**
     * 设置输入提示文本
     * @param inputHint 输入提示文本
     * @return 对话框本身
     */
    public PromptDialog setInputHint(String inputHint){
        this.inputHint = inputHint;
        if (metInput != null){
            metInput.setHint(inputHint);
        }
        return this;
    }
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mflContainerView.removeAllViews();
        metInput = new EditText(getContext());
        setChildView(metInput);
        setInputText(inputText);
        setInputHint(inputHint);
        mflContainerView.setVisibility(View.VISIBLE);
    }

    /**
     * 工厂方法创建 PromptDialog 类的新实例
     * @param ctx 上下文对象
     * @return PromptDialog 对象
     */
    public static PromptDialog create(Context ctx){
        return new PromptDialog(ctx);
    }
}
