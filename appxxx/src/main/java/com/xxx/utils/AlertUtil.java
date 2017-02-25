package com.xxx.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.appxxx.R;

public class AlertUtil {

    public static final void alertDialog(Context ctx, String msg) {
        new AlertDialog.Builder(ctx)
                .setMessage(msg)
                .setNegativeButton("确定", null)
                .create().show();
    }

    public static final void showToast(Context context, String msg) {
        if(TextUtils.isEmpty(msg))
            return;
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_black, null);

        TextView message = (TextView) toastRoot.findViewById(R.id.toast_text);
        message.setText(msg);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    public static ProgressDialog showLoadingDialog(Context context, String msg){
        ProgressDialog pbDialog = new ProgressDialog(context);
        pbDialog.setMessage(msg);
        pbDialog.setIndeterminate(true);
        pbDialog.setCancelable(false);
        pbDialog.show();
        return pbDialog;
    }

    public static TitleProgressBar showProgressBar(View v, Activity ac, String text){
        int[] location = new int[2];
        v.getLocationInWindow(location);

        TitleProgressBar tpb = new TitleProgressBar(ac.getBaseContext());
        tpb.setLocation(location);
        tpb.updateTxt(text);
        ((ViewGroup)ac.getWindow().getDecorView()).addView(tpb);
        return tpb;
    }

    public static void hideProgressBar(final TitleProgressBar v, final Activity ac, String text){
        if(TextUtils.isEmpty(text)){
            ((ViewGroup) ac.getWindow().getDecorView()).removeView(v);
        }else{
            v.complete(text);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ViewGroup) ac.getWindow().getDecorView()).removeView(v);
                }
            }, 1000);
        }
    }

    /**
     * 页面加载中
     */
    public static class TitleProgressBar extends LinearLayout {
        private TextView title;
        private View pb;
        private View layout;

        public TitleProgressBar(Context context) {
            super(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            setLayoutParams(lp);
            LayoutInflater inflater = LayoutInflater.from(context);
            layout = inflater.inflate(R.layout.horizontal_progress_layout,null);
            title = (TextView)layout.findViewById(R.id.tv_title);
            pb = layout.findViewById(R.id.pb_loading);
            addView(layout);
        }

        void setLocation(int[] location){
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp2.leftMargin = location[0];
            lp2.topMargin = location[1];
            layout.setLayoutParams(lp2);
        }

        public void updateTxt(String text){
            title.setCompoundDrawables(null,null,null,null);
            title.setText(text);
        }

        public void complete(String text){
            pb.setVisibility(View.GONE);
            title.setText(text);
            Drawable drawable = getResources().getDrawable(R.drawable.progress_ok);
            drawable.setBounds(0, 0, DensityUtil.dip2px(getContext(),15), DensityUtil.dip2px(getContext(),15));//必须设置图片大小，否则不显示
            title.setCompoundDrawablePadding(DensityUtil.dip2px(getContext(),5));
            title.setCompoundDrawables(drawable,null,null,null);
        }
    }

    public static ProgressWait showProgressWait(Activity ac){
        ProgressWait wpb = new ProgressWait(ac.getBaseContext());
        ((ViewGroup)ac.getWindow().getDecorView()).addView(wpb);
        return wpb;
    }

    @Deprecated
    public static void hideProgressWait(final ProgressWait v,Activity ac){
        hideProgressView(v,ac);
    }

    public static class ProgressWait extends LinearLayout {

        public ProgressWait(Context context) {
            super(context);
            float scale = context.getResources().getDisplayMetrics().density;

            LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            setLayoutParams(lp);
            setGravity(Gravity.CENTER);
            LinearLayout bg = new LinearLayout(context);
            bg.setLayoutParams(new LinearLayout.LayoutParams(
                    (int) (120 * scale + 0.5f),
                    (int) (120 * scale + 0.5f)));


            bg.setBackground(getDarkBg(context));
            bg.setGravity(Gravity.CENTER);
            ProgressBar progressBar = new ProgressBar(context);
            bg.addView(progressBar);
            addView(bg);
        }
    }

    private static Drawable getDarkBg(Context context){
        float scale = context.getResources().getDisplayMetrics().density;
        int corner = (int) (2 * scale + 0.5f);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{corner,corner,corner,corner,corner,corner,corner,corner},null,null));
        shapeDrawable.getPaint().setColor(Color.argb(132, 0, 0, 0));
        return shapeDrawable;
    }

    public static ProgressView showProgressView(Activity ac, Drawable logo, String title, String content){
        ProgressView vpb = new ProgressView(ac.getBaseContext(),logo,title,content);
        ((ViewGroup)ac.getWindow().getDecorView()).addView(vpb);
        return vpb;
    }

    public static class ProgressView extends LinearLayout {
        private TextView titleTv;
        private TextView contentTv;
        public ProgressView(Context context, Drawable logo, String title, String content) {
            super(context);
            float scale = context.getResources().getDisplayMetrics().density;

            LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            setLayoutParams(lp);
            setGravity(Gravity.CENTER);
            LinearLayout bg = new LinearLayout(context);
            bg.setOrientation(VERTICAL);
            bg.setBackground(getDarkBg(context));
            int pd =(int) (5 * scale + 0.5f);
            bg.setPadding(pd,pd,pd,pd);
            bg.setLayoutParams(TextUtils.isEmpty(title)?
                    new LinearLayout.LayoutParams((int) (120 * scale + 0.5f),(int) (120 * scale + 0.5f))
                    :new LayoutParams((int) (240 * scale + 0.5f), LayoutParams.WRAP_CONTENT));
            bg.setGravity(Gravity.CENTER);
            if(TextUtils.isEmpty(title)){
                if(logo == null){
                    ProgressBar progressBar = new ProgressBar(context);
                    bg.addView(progressBar);
                }else{
                    ImageView iv = new ImageView(context);
                    iv.setImageDrawable(logo);
                    iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    bg.addView(iv);
                }
            }else{
                titleTv = new TextView(context);
                titleTv.setText(title);
                titleTv.setTextColor(Color.WHITE);
                titleTv.setTextSize(14);
                titleTv.setSingleLine(true);
                titleTv.setEllipsize(TextUtils.TruncateAt.END);
                int padding =(int) (5 * scale + 0.5f);
                titleTv.setPadding(0,padding,0,padding);
                titleTv.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                bg.addView(titleTv);
            }

            if(!TextUtils.isEmpty(content)){
                contentTv = new TextView(context);
                contentTv.setText(content);
                contentTv.setTextColor(Color.WHITE);
                contentTv.setTextSize(14);
                contentTv.setSingleLine(true);
                contentTv.setEllipsize(TextUtils.TruncateAt.END);
                int padding =(int) (5 * scale + 0.5f);
                if(!TextUtils.isEmpty(title))
                    padding = padding*2;
                contentTv.setPadding(0, padding, 0, 0);
                contentTv.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                bg.addView(contentTv);
            }
            addView(bg);
        }

        public void updateTitle(String title){
            if(titleTv != null){
                titleTv.setText(title);
            }
        }

        public void updateContent(String content){
            if(contentTv != null){
                contentTv.setText(content);
            }
        }
    }

    public static RadiationProgress showRadiationProgressView(Activity ac){
        RadiationProgress vpb = new RadiationProgress(ac);
        ((ViewGroup) ac.getWindow().getDecorView()).addView(vpb);
        return vpb;
    }

    public static void hideProgressView(View v, Activity ac){
        ((ViewGroup) ac.getWindow().getDecorView()).removeView(v);
    }

    public static class RadiationProgress extends LinearLayout {
        private RadiationView radiationView;

        public RadiationProgress(Context context) {
            super(context);
            float scale = context.getResources().getDisplayMetrics().density;

            LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            setLayoutParams(lp);
            setGravity(Gravity.CENTER);
            radiationView = new RadiationView(context);
            radiationView.setLayoutParams(new LayoutParams((int) (48 * scale + 0.5f),(int) (48 * scale + 0.5f)));
            addView(radiationView);
        }

        public void progress(float p){
            radiationView.progress(p);
        }
    }

    public static class RadiationView extends View {
        private Paint mPaint;
        private final RectF oval = new RectF();
        private float progress = 0;
        private int MAX = 360;

        public void progress(float p){
            if(p > 1 && p < 0) return;
            progress = MAX * p;
            invalidate();
        }

        public RadiationView(Context context) {
            super(context);
            init();
        }

        public RadiationView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public RadiationView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init(){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.WHITE);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right,
                                int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            if(changed){
                int w = getWidth();
                int h = getHeight();
                oval.set(0, 0, w, h);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawArc(oval,0,progress,true,mPaint);
        }
    }
}
