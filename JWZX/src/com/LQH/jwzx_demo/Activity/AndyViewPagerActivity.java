package com.LQH.jwzx_demo.Activity;

import java.util.ArrayList;
import java.util.List;

import com.LQH.jwzx_demo.UiAdapter.ViewPagerAdapter;
import com.zhy.zhy_slidemenu_demo02.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AndyViewPagerActivity extends Activity implements OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private Button button;

    // 引导图片资源
    private static final int[] pics = { R.drawable.guige2, R.drawable.guilde1,
            R.drawable.guige2, R.drawable.guilde1 };

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        initView();

        views = new ArrayList<View>();

        // 为引导图片提供布�?���?
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 初始化引导图片列�?
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(AndyViewPagerActivity.this, LoginActivity.class);
                AndyViewPagerActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        vp = (ViewPager) findViewById(R.id.viewpager);
        // 初始化底部小圆点
        initDots();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            //View android.view.ViewGroup.getChildAt(int index)�?
            //Returns the view at the specified position in the group.
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰�?
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
            //View中的setTag(Onbect)表示给View添加�?��格外的数据，以后可以用getTag()将这个数据取出来�?
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true);// 设置为红色，即选中中状态
    }

    /**
     * 改变当前引导小点颜色ؿ
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        System.out.println("positon="+positon);
        dots[positon].setEnabled(true);
        //此时的currentIndex指的是上一个圆点
        System.out.println("currentIndex="+currentIndex);
        dots[currentIndex].setEnabled(false);
        //现在的currentIndex指的是当前的小圆点
        currentIndex = positon;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    // 当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurDot(arg0);
        if (arg0 == 3) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }
}
