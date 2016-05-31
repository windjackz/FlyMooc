package com.recker.flymooc.customviews;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by recker on 16/5/30.
 */
public class FlowLayout extends ViewGroup {

    private FlowAdapter mAdapter;
    private OnItemClickListener mListener;
    private MotionEvent mMotionEvent;

    private int i;

    public void setAdapter(FlowAdapter adapter) {
        this.mAdapter = adapter;
        addViews();
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 添加子视图
     */
    private void addViews() {
        removeAllViews();
        if (mAdapter != null) {
            for (i = 0; i < mAdapter.getViews().size(); i++) {
                final View child = mAdapter.getViews().get(i);

                if (child.getLayoutParams() == null) {
                    MarginLayoutParams lp = new MarginLayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    child.setLayoutParams(lp);
                }
                addView(child);
            }
        }
    }


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        //记录每一行的宽度与高度
        int lineWidth = 0;
        int lineHeight = 0;

        //得到元素的个数
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //测量子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayouParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth) {//换行
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {//未换行
                //叠加行宽
                lineWidth += childWidth;
                //得到当前行最大高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一个控件
            if (i == count-1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }

        }

        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }


    //存储所有的View
    private List<List<View>> mAllViews = new ArrayList<>();
    //每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        //当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeigth = child.getMeasuredHeight();

            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                //记录lineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的Views
                mAllViews.add(lineViews);

                //重置行宽和行高
                lineWidth = 0;
                lineHeight  = childHeigth + lp.topMargin + lp.bottomMargin;

                //重置View的集合
                lineViews = new ArrayList<>();
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, child.getHeight()+lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }

        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //设置子View的位置
        int left = 0;
        int top = 0;

        //行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            //当前行的所有的View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                //判断child的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                //为子View进行布局
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            left = 0;
            top += lineHeight;
        }

    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            mMotionEvent = MotionEvent.obtain(event);
//        }
//
//        return super.dispatchTouchEvent(event);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            mMotionEvent = MotionEvent.obtain(event);
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {

        if (mMotionEvent == null) return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        View child = findChild(x, y);
        int position = findChildPosition(child);

        Log.d(FlowLayout.class.getSimpleName(), "x-->"+x);
        Log.d(FlowLayout.class.getSimpleName(), "y-->"+y);

        if (child == null) {
            Log.d(FlowLayout.class.getSimpleName(), "视图为空");
        }
        Log.d(FlowLayout.class.getSimpleName(), "position-->"+position);

        if (position != -1 && mListener != null) {
            mListener.onItemClick(position, child);
        }

        return true;
    }

    private View findChild(int x, int y) {
        int count = getChildCount();
        for (i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            child.getHitRect(outRect);
            if (outRect.contains(x, y))
            {
                return child;
            }
        }
        return null;
    }

    private int findChildPosition(View child) {
        int count = getChildCount();
        for (i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (v == child) return i;
        }

        return -1;
    }

    public interface FlowAdapter {
        List<View> getViews();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
