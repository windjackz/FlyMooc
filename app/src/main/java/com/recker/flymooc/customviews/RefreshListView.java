package com.recker.flymooc.customviews;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.recker.flymooc.R;

/**
 * Created by recker on 16/5/13.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

    private final int NONE = 0;//正常状态
    private final int PULL = 1;//提示下拉刷新状态
    private final int RELESE = 2;//提示释放状态
    private final int REFLASHING = 3;//正在刷新状态
    private final int RATIO = 3;//比值

    private View headerView;//顶部刷新视图
    private int headerViewHeight;//顶部布局文件的高度
    private int firstVisibleItem;//当前第一个可见的item的位置

    private boolean isEnd;//是否结束刷新
    private boolean isRefreable;//是否可以刷新
    private boolean isRemark;//标记，当前是在ListView是否是在第一个
    private float startY;
    private float offsetY;
    private int state;//当前的状态

    private TextView tip;
    private ImageView img;
    private AnimationDrawable drawableAnim;

    private int totalItemCount;// 总数量
    private int lastVisibleItem;// 最后一个可见的item
    private View footerView;

    public RefreshListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        footerView = LayoutInflater.from(context).inflate(R.layout.layout_load_more, null);
        addFooterView(footerView);

        headerView = LayoutInflater.from(context).inflate(R.layout.layout_refresh, null);
        addHeaderView(headerView);
        measureView(headerView);
        headerViewHeight = headerView.getMeasuredHeight();
        topPadding(-headerViewHeight);

        //添加动画
        tip = (TextView) headerView.findViewById(R.id.tip);
        img = (ImageView) headerView.findViewById(R.id.img);
        img.setBackgroundResource(R.drawable.refresh_anim);
        drawableAnim = (AnimationDrawable) img.getBackground();

        //关闭view的OverScroll
        setOverScrollMode(OVER_SCROLL_NEVER);
        setOnScrollListener(this);
        state = NONE;
        isEnd = true;
        isRefreable = false;

    }

    /**
     * 通知父布局，占用的宽，高
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    private void topPadding(int topPadding) {
        headerView.setPadding(headerView.getPaddingLeft(), topPadding,
                headerView.getPaddingRight(), headerView.getPaddingBottom());
        headerView.invalidate();
    }



    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (totalItemCount == lastVisibleItem
                && scrollState == SCROLL_STATE_IDLE) {
            if (onRefreshListener != null) {
                onRefreshListener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isEnd) {//如果现在时结束的状态，即刷新完毕了，可以再次刷新了，在refreshComplete中设置
            if (isRefreable) {//如果现在是可刷新状态   在setOnRefreshListener中设置为true
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (firstVisibleItem == 0 && !isRemark) {
                            isRemark = true;
                            startY = ev.getY();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        onMove(ev);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (state == RELESE) {
                            state = REFLASHING;
                            //加载最新数据
                            refreshViewByState();
                            onRefreshListener.onRefresh();
                        } else if (state == PULL) {
                            state = NONE;
                            refreshViewByState();
                            setSelection(0);
                        }
                        isRemark = false;
                        break;
                }
            }
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 判断移动过程操作
     * @param ev
     */
    private void onMove(MotionEvent ev) {


        //再次得到y坐标，用来和startY相减来计算offsetY位移值
        float tempY = ev.getY();
        //再起判断一下是否为listview顶部并且没有记录y坐标
        if (firstVisibleItem == 0 && !isRemark) {
            isRemark = true;
            startY = tempY;
        }

        if (state != REFLASHING && isRemark) {
            //计算y的偏移量
            offsetY = tempY - startY;
            //计算当前滑动的高度
            float currentHeight = (-headerViewHeight+offsetY/3);

            //如果当前的状态是释放刷新，并且已经记录y坐标
            if (state == RELESE && isRemark) {
                setSelection(0);
                //如果当前滑动的距离小于headerView的总高度
                if (-headerViewHeight+offsetY/RATIO<0) {
                    //状态改为下拉刷新
                    state = PULL;
                    refreshViewByState();
                } else if (offsetY <= 0) {//如果当前y的位移值小于0，即为headerView隐藏了
                    //状态改为正常状态
                    state = NONE;
                    refreshViewByState();
                }
            }
            //如果当前状态为下拉刷新并且已经记录y坐标
            if (state == PULL && isRemark) {
                setSelection(0);
                //如果下拉距离大于等于headerView的总高度
                if (-headerViewHeight+offsetY/RATIO>=0) {
                    //状态改为释放刷新
                    state = RELESE;
                    refreshViewByState();
                } else if (offsetY <= 0) {//如果当前y的位移值小于0，即为headerView隐藏了
                    //状态改为正常状态
                    state = NONE;
                    refreshViewByState();
                }
            }
            //如果当前状态为正常并且已经记录y坐标
            if (state == NONE && isRemark) {
                //如果位移值大于0
                if (offsetY>=0) {
                    //将状态改为释放刷新状态
                    state = PULL;
                    refreshViewByState();
                }
            }
            //如果为下拉刷新状态
            if (state == PULL) {
                topPadding((int)(-headerViewHeight+offsetY/RATIO));
            }
            //如果为释放刷新状态
            if (state == RELESE) {
                topPadding((int)(-headerViewHeight+offsetY/RATIO));
            }

        }
    }

    /**
     * 根据当前状态，改变界面显示
     */
    private void refreshViewByState() {
        switch (state) {
            case NONE:
                topPadding(-headerViewHeight);
                drawableAnim.stop();
                break;
            case PULL:
                drawableAnim.stop();
                tip.setText("下拉刷新");
                break;
            case RELESE:
                drawableAnim.stop();
                tip.setText("释放刷新");
                break;
            case REFLASHING:
                drawableAnim.start();
                tip.setText("正在刷新");
                break;
        }
    }

    /**
     * 获取完数据
     */
    public void refreshComplete() {
        isEnd = true;
        state = NONE;

        refreshViewByState();
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
        isRefreable = true;
    }

    public interface OnRefreshListener {
        void onRefresh();
        void onLoadMore();
    }

    private void debug(String str) {
        Log.d(RefreshListView.class.getSimpleName(), str);
    }
}
