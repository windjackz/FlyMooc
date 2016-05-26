package com.recker.flymooc.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.recker.flymooc.R;

/**
 * Created by recker on 16/5/21.
 */
public class FlyTabLayout extends HorizontalScrollView{

    public final static int MODE_FIXED = 100;//为均分模式
    public final static int MODE_SCROLLABLE = 200;//为联动模式

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mTabsContainer;//tab容器
    private List<String> mTitles;//tab的标题

    private int mTabCount = 1;//tab的数量
    private float mOffsetX = 0f;//指示器偏移量
    private boolean mIsRepaint = false;//是否发生重绘
    private int mTabWidth = 0;//tab的默认宽度
    private Paint mIndicatorPaint;//指示器画笔
    private int mIndicatorWidth;//指示器宽度

    private int mIndicatorHeight = 8;//指示器高度
    private int mIndicatorColor = Color.RED;//指示器颜色
    private int mTextSize = 14;//字体大小
    private int mTextUnSelectedColor = 0xFFC7C7C7;//TextView未选择颜色
    private int mTextSelectedColor = Color.RED;//TextView选择颜色
    private int mTabMode = MODE_FIXED;//TAB栏模式
    private int mMaxVisibleTabCount = 5;//屏幕最大可见tab数量

    public FlyTabLayout(Context context) {
        this(context, null);
    }

    public FlyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initParameter(attrs);
        init();
    }

    /**
     * 初始化参数
     * @param attrs
     */
    private void initParameter(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.FlyTabLayout);

        mIndicatorHeight = (int) a.getDimension(R.styleable.FlyTabLayout_tab_indicator_height, mIndicatorHeight);
        mIndicatorColor = a.getColor(R.styleable.FlyTabLayout_tab_indicator_color, mIndicatorColor);
        mTextSize = a.getInt(R.styleable.FlyTabLayout_tab_text_size, mTextSize);
        mTextUnSelectedColor = a.getColor(R.styleable.FlyTabLayout_tab_text_unselected_color, mTextUnSelectedColor);
        mTextSelectedColor = a.getColor(R.styleable.FlyTabLayout_tab_text_selected_color, mTextSelectedColor);
        mTabMode = a.getInt(R.styleable.FlyTabLayout_tab_mode, mTabMode);
        mMaxVisibleTabCount = a.getInt(R.styleable.FlyTabLayout_tab_visible_count, mMaxVisibleTabCount);

        a.recycle();
    }

    private void init() {
        mTabsContainer = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabsContainer.setLayoutParams(params);
        //设置规则为水平
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mTabsContainer);
        //初始化画笔
        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setStyle(Paint.Style.FILL);
        //如果为联动模式，则获取屏幕宽度
        if (mTabMode == MODE_SCROLLABLE) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            mTabWidth = metrics.widthPixels / mMaxVisibleTabCount;
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        //获取tab的数量，根据tab数量来判断
        mTabCount = viewPager.getAdapter().getCount();
        mTitles = new ArrayList<>();
        //获取标题
        for (int i = 0; i < mTabCount; i++) {
            mTitles.add((String) viewPager.getAdapter().getPageTitle(i));
        }

        addChildTabs();//添加子tab
        setupPagerChangeListener();//设置Page监听
    }

    /**
     * 添加子tab
     */
    private void addChildTabs() {
        for (int i = 0; i < mTitles.size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setText(mTitles.get(i));
            tv.setTextSize(mTextSize);
            //设置颜色，第一个为选中颜色
            if (i == 0) {
                tv.setTextColor(mTextSelectedColor);
            } else {
                tv.setTextColor(mTextUnSelectedColor);
            }
            //设置布局规则
            if (mTabMode == MODE_FIXED) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.weight = 1;
                tv.setLayoutParams(lp);
            } else {
                LayoutParams lp = new LayoutParams(mTabWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(lp);
            }
            tv.setGravity(Gravity.CENTER);
            mTabsContainer.addView(tv);
            //设置监听事件
            final int page = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(page);
                }
            });

        }
    }

    private void setupPagerChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                scrollToIndicator(position,
                        positionOffset * mTabsContainer.getChildAt(position).getWidth());
            }

            @Override
            public void onPageSelected(int position) {
                clearTextViewColor();
                TextView tv = (TextView) mTabsContainer.getChildAt(position);
                tv.setTextColor(mTextSelectedColor);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    scrollToIndicator(mViewPager.getCurrentItem(), 0);
                }
            }
        });
    }

    private void scrollToIndicator(int position, float offset) {

        if (mIndicatorWidth <= 0) {
            mIndicatorWidth = getWidth()/mTabCount;
        }
        mOffsetX = mIndicatorWidth*position + offset;

        //移动容器
        if (mMaxVisibleTabCount > 1 && mTabMode == MODE_SCROLLABLE) {
            if (position >= mMaxVisibleTabCount - 2 && offset > 0
                    && mTabsContainer.getChildCount() > mMaxVisibleTabCount
                    && position < mTabsContainer.getChildCount() - 2) {
//                scrollTo((int) ((position - mMaxVisibleTabCount + 2) * mTabWidth + offset), 0);
                int tabPosition = position - mMaxVisibleTabCount + 2;
                scrollTo(mTabsContainer.getChildAt(tabPosition).getLeft()+(int)offset, 0);
            }

        } else {
            scrollTo((int) (position * mTabWidth + offset), 0);
        }


        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsRepaint) {
            mIndicatorPaint.setColor(mIndicatorColor);
            if (mTabMode == MODE_FIXED) {
                mIndicatorWidth = getWidth() / mTabCount;
            } else {
                mIndicatorWidth = mTabWidth;
            }
        }

        int height = getHeight();
        canvas.drawRect(mOffsetX, height-mIndicatorHeight, mOffsetX+mIndicatorWidth, height, mIndicatorPaint);
    }


    private void clearTextViewColor() {
        int count = mTabsContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView tv = (TextView) mTabsContainer.getChildAt(i);
            tv.setTextColor(mTextUnSelectedColor);
        }
    }


    private void debug(String str) {
        Log.d(FlyTabLayout.class.getSimpleName(), str);
    }

}
