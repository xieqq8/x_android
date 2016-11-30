package com.kuaxue.demo.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kuaxue.demo.adapter.Adapter000FgMainViewPager;
import com.kuaxue.demo.fragment.Fg100MainAsk;
import com.kuaxue.demo.fragment.Fg200MainFindTeacher;
import com.kuaxue.demo.fragment.Fg300MainMsg;
import com.kuaxue.demo.fragment.Fg400MainMe;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xxx.fudao.R;
import com.xxx.xbase.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页   继承FragmentActivity
 */
public class Act000Main extends BaseFragmentActivity {
    private List<Fragment> listFrgment = null;
    private ViewPager mViewPager = null;
    private RadioGroup mGroup = null;

    // 双击返回键退出用
    private long exitTime = 0;

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        // 可以使得状态栏和导航栏变为透明
//        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION

        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }

        final int bitsBottom = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bitsBottom;
        } else {
            winParams.flags &= ~bitsBottom;
        }
        win.setAttributes(winParams);
    }
    SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置通知栏通明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT 以上实现沉浸式状态栏
//            http://www.jianshu.com/p/f8374d6267ef KITKAT 以上实现沉浸式状态栏
            setTranslucentStatus(true);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(0xff3fdab4);// 通知栏所需颜色
            // 00 表示完全透明,ff 表示完全不透明
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintColor(0xff3fdab4); // 导航栏
        }

        setContentView(R.layout.activity_main);

        // 初始化滑动 viewPager 处理
        initViewPager();

        AddRadioGroup();
        // 默认选中提问
        mGroup.check(R.id.rb_tab_ask);

        // 检查更新
//        UpdateManager updateManager = new UpdateManager(Act000Main.this);
//        updateManager.checkUpdate(false);
    }

    /**
     * 初始化滑动 viewPager 处理
     */
    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_mainpager);

        // 切换处理
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                logx.d("onPageScrolled " + position);
            }

            @Override
            public void onPageSelected(int position) {
                logx.d("Pager select " + position);

                switch (position) {
                    case 0:
// mGroup.check(R.id.rb_tab_ask);
// 用上面的OnCheckedChangeListener会执行三次 不能做切换的判断
                        ((RadioButton) findViewById(R.id.rb_tab_ask)).setChecked(true);
                        break;
                    case 1:
                        ((RadioButton) findViewById(R.id.rb_tab_find_teacher)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton) findViewById(R.id.rb_tab_msg)).setChecked(true);
                        break;
                    case 3:
                        ((RadioButton) findViewById(R.id.rb_tab_me)).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                logx.d("onPageScrollStateChanged " + state);
//                state=0的时候表示什么都没做，就是停在那
//                state=1的时候表示正在滑动
//                state==2的时候表示滑动完毕了
            }
        });
        // 内容填充
        listFrgment = new ArrayList<Fragment>();
        Fg100MainAsk ask = new Fg100MainAsk();
        Fg200MainFindTeacher ask1 = new Fg200MainFindTeacher();
        Fg300MainMsg ask23 = new Fg300MainMsg();
        Fg400MainMe ask3 = new Fg400MainMe();
        listFrgment.add(ask);
        listFrgment.add(ask1);
        listFrgment.add(ask23);
        listFrgment.add(ask3);
//
        mViewPager.setAdapter(new Adapter000FgMainViewPager(getSupportFragmentManager(), listFrgment));
        mViewPager.setCurrentItem(0);
    }

    /**
     *
     */
    private void AddRadioGroup() {
        mGroup = (RadioGroup) findViewById(R.id.rg_main_tab);

        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mViewPager == null) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_tab_ask:
                        logx.d("radio group check 0");
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_tab_find_teacher:
                        logx.d("radio group check 1");
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_tab_msg:
                        mViewPager.setCurrentItem(2);
                        logx.d("radio group check 2");
                        break;
                    case R.id.rb_tab_me:
                        logx.d("radio group check 3");
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    /**
     * 再按一次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), mContext.getString(R.string.actmain_exit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
//                ActivityManager.getAppManager().AppExit(mContext);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
