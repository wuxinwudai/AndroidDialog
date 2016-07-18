package com.wuxinwudai.adlg;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;



/**
 * QQStyleDialog 类为QQ风格底部弹出对话框
 *
 * @author 吾心无待 于2016年03月12日
 */
public final class QQStyleDialog extends Dialog {

    private ListView mListView;//列表,用于存放按钮
    private Button mbtnCancel = null;//取消按钮
    private ListAdapter mAdapter;//数据适配器
    private int mPadding = 8;//左右边距
    private int mTextColor = 0xFF007AFF ;//文本颜色

    /**
     * 公有构造函数，初始化 QQStyleDialog 类的一个新实例
     *
     * @param context 上下文对象
     * @param list 显示的列表和对应的OnClick事件监听器
     */
    public QQStyleDialog(Context context,ArrayList<ItemInfo> list) {
        super(context, R.style.qqstyle_dialog);
        this.mAdapter = this.new ListAdapter(getContext(),list);
    }


    /**
     * 工厂方法创建 QQStyleDialog 类的一个新实例
     * @param ctx 上下文对象
     * @param list 显示的列表和对应的OnClick事件监听器
     * @return 对话框
     */
    public static QQStyleDialog create(Context ctx,ArrayList<ItemInfo> list){
        return new QQStyleDialog(ctx,list);
    }

    private static int getColorInt(int c){
        return Color.parseColor("#"+Integer.toHexString(c));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM);
        setContentView(R.layout.layout_qq_style_dialog);
        setCanceledOnTouchOutside(true);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setPadding(mPadding);
        mListView = (ListView) findViewById(R.id.lv_btns);
        mListView.setAdapter(this.mAdapter);
        mbtnCancel = (Button) findViewById(R.id.btn_dialog_cancel);
        mbtnCancel.setTextColor(getColorInt(mTextColor));
        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QQStyleDialog.this.dismiss();
            }
        });
        setCanceledOnTouchOutside(true);
        this.mAdapter.notifyDataSetChanged();
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
    public QQStyleDialog setPadding(int padding){
        this.mPadding = padding;
        Window window = getWindow();
        if (window!=null){
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            window.getAttributes().width = (int) (displayMetrics.widthPixels - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding*2, displayMetrics));
        }
        return this;
    }

    /**
     * 获取字体文本颜色
     * @return 字体颜色值
     */
    public int getTextColor(){
        return mTextColor;
    }

    /**
     * 设置字体颜色，
     * @return QQStyleDialog 本身
     */
    public QQStyleDialog setTextColor(@ColorInt int color){
        this.mTextColor = color;
        if (mbtnCancel != null){
            mbtnCancel.setTextColor(getColorInt(mTextColor));
        }
        return this;
    }

    /**
     * ListAdapter 类为 QQStyleDialog 提供数据
     */
    public class ListAdapter extends BaseAdapter {
        private ArrayList<ItemInfo> mList;
        private Context mContext;
        private LayoutInflater mInflater;

        public ListAdapter(Context context, ArrayList<ItemInfo> list) {
            this.mContext = context;
            this.mList = list;
            if (list == null) {
                mList = new ArrayList<>(10);
            }
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemInfo item = mList.get(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_lv_item_qqstyle_dialog, null);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvTitle.setTextColor(getColorInt(mTextColor));
                holder.listener = item.getListener();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTitle.setText(item.getTitle());
            holder.tvTitle.setOnClickListener(item.getListener());
            if (position == 0 && mList.size() > 1){//设置首项背景
                holder.tvTitle.setBackgroundResource(R.drawable.selector_btn_top_qqstyle_dialog);
            }else if (position == mList.size()-1 && position > 1){
                holder.tvTitle.setBackgroundResource(R.drawable.selector_btn_bottom_qqstyle_dialog);

            }
            Log.i("TEST","DONE"+position);
            return convertView;
        }

        class ViewHolder {
            public TextView tvTitle;
            public View.OnClickListener listener;
        }
    }

    @Override
    public void show() {
        super.show();
        mAdapter.notifyDataSetChanged();
    }
}