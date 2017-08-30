package com.xxx.blogx.adapter;

import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.blogx.R;
import com.xxx.blogx.model.BlogModel;
import com.xxx.utils.DateUtil;

//import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class BlogPullToRefreshAdapter extends BaseQuickAdapter<BlogModel.ListBean, BaseViewHolder> {
    public BlogPullToRefreshAdapter() {
        super( R.layout.layout_animation, null); // 第二个参数是数据
    }

    @Override
    protected void convert(BaseViewHolder helper, BlogModel.ListBean item) {
//        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetText).addOnClickListener(R.id.tweetName);
//
        helper.setText(R.id.blogTitle,item.getTitle());
        helper.setText(R.id.blogCatalog,item.getCatalog());
        helper.setText(R.id.blogDate,   DateUtil.formatTime(item.getPublishTime(), "yyyy-MM-dd HH:mm"));

//        helper.setText(R.id.blogText, (item.getSummary()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // API Level 23或之前的设备可以用过时的方法，API Level 24或以上的设备则使用2个参数的方法即可。
            helper.setText(R.id.blogText, Html.fromHtml(item.getSummary(), Html.FROM_HTML_MODE_COMPACT));
//            其中的flags表示：
//            FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
//            FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
        } else {
            helper.setText(R.id.blogText, Html.fromHtml(item.getSummary()));
        }
//        String msg="\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
//        ( (TextView)helper.getView(R.id.tweetText)).setText(SpannableStringUtils.getBuilder(msg).append("landscapes and nedes").setClickSpan(clickableSpan).create());
//        ( (TextView)helper.getView(R.id.tweetText)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
//            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);
        }
    };


}
