package com.wuxinwudai.adlg.sample;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxinwudai.adlg.ContainerDialog;
import com.wuxinwudai.adlg.ItemInfo;
import com.wuxinwudai.adlg.ProgressDialog;
import com.wuxinwudai.adlg.PromptDialog;
import com.wuxinwudai.adlg.QQStyleDialog;



import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv1,tv2,tv3,tv4,tv5;
    private int i = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        tv4 = (TextView)findViewById(R.id.tv4);
        tv5 = (TextView)findViewById(R.id.tv5);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv1:{
                ContainerDialog.create(this)
                        .setTitle("欢迎使用 ContainerDialog！！")
                        .setMessage("请使用 setChildView 方法而非使用 setContentView 方法")
                        .setOnConfirmClickListener(new ContainerDialog.OnConfirmClickListener() {
                            @Override
                            public void confirm(ContainerDialog containerDialog) {
                                Toast.makeText(MainActivity.this,"Confirm",Toast.LENGTH_LONG).show();
                            }
                        }).setOnCancelClickListener(new ContainerDialog.OnCancelClickListener() {
                            @Override
                            public void cancel(ContainerDialog containerDialog) {
                                Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;
            }
            case R.id.tv2:{
                PromptDialog.create(MainActivity.this)
                        .setInputHint("请输入您的姓名")
                        .setOnConfirmClickListener(new ContainerDialog.OnConfirmClickListener() {
                            @Override
                            public void confirm(ContainerDialog containerDialog) {
                                Toast.makeText(MainActivity.this,"您的姓名是："+((PromptDialog)containerDialog).getInputText(),Toast.LENGTH_LONG).show();
                                containerDialog.dismiss();//让对话框消失
                            }
                        }).setTitle("欢迎使用 PromptDialog！！！")
                        .show();//注意调用setTitle等ContainerDialog父类方法应放最后
                break;
            }
            case R.id.tv3:{
                ArrayList<ItemInfo> list = new ArrayList<ItemInfo>();
                list.add(new ItemInfo("版本更新", new ItemInfoListener("版本更新")));
                list.add(new ItemInfo("反馈", new ItemInfoListener("反馈")));
                list.add(new ItemInfo("退出QQ", new ItemInfoListener("退出QQ")));
                QQStyleDialog.create(MainActivity.this,list).show();
                break;
            }
            case R.id.tv4:{
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.show();
                new CountDownTimer(800 * 4, 800) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        dialog.error();
                    }
                }.start();
                break;
            }
            case R.id.tv5:{
                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("Loading");
                pDialog.show();
                pDialog.setCancelable(false);
                new CountDownTimer(800 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++;
                        switch (i){
                            case 0:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    public void onFinish() {
                        i = -1;
                        pDialog.setTitleText("Success!")
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
                break;
            }
        }
    }

    class ItemInfoListener implements View.OnClickListener{
        private String text;
        public ItemInfoListener(String text){
            this.text = text;
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();
        }
    }
}
