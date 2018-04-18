package com.omvp.components;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

public class BottomBarView extends BaseComponentView implements BottomBarItemView.OnCheckedChangeListener {

    private LinearLayout mRootView;

    private int[] iconsArray;

    private ViewPager mViewPager;

    private SparseArray<BottomBarItemView> mItemsArray;

    public BottomBarView(Context context) {
        super(context);
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void loadAttributes(Context context, AttributeSet attrs) {

    }

    @Override
    protected void bindViews() {
        mRootView = findViewById(R.id.root);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(getResources().getDimensionPixelSize(R.dimen.elevation));
            setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }
    }

    @Override
    protected void loadData() {
        mItemsArray = new SparseArray<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bottom_bar_view;
    }

    public void setViewPager(ViewPager viewPager, int[] iconsArray) {
        mViewPager = viewPager;
        this.iconsArray = iconsArray;
        addItems();
    }

    private void addItems() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.weight = 1;

        int numItems = mViewPager.getAdapter().getCount();
        for (int i = 0; i < numItems; i++) {
            BottomBarItemView itemView = new BottomBarItemView(getContext());
/*
                    .icon(getIcon(i))
                    .animateCounter(true)
                    .params(params)
                    .tag(i)
                    .checked(i == 0)
                    .checkedChangeListener(this)
                    .counterColor(ContextCompat.getColor(getContext(), android.R.color.white))
                    .build();
*/
            itemView.setIcon(getIcon(i));
            itemView.setLayoutParams(params);
            itemView.setAnimateCounter(true);
            itemView.setTag(i);
            itemView.setChecked(i == 0);
            itemView.setOnCheckedChangeListener(this);
            itemView.setCounterColor(ContextCompat.getColor(getContext(), android.R.color.white));
            itemView.setCounterBackground(R.drawable.bottom_bar_dot_bg);
            mRootView.addView(itemView);

            mItemsArray.put(i, itemView);
        }
    }

    private int getIcon(int position) {
        if (iconsArray == null || position >= iconsArray.length) {
            return R.drawable.bottom_navigation_item_home;
        }

        return iconsArray[position];
    }

    @Override
    public void onCheckedChanged(BottomBarItemView itemView, boolean isChecked) {
        if (isChecked) {
            for (int i = 0; i < mItemsArray.size(); i++) {
                if ((Integer) itemView.getTag() != i) {
                    BottomBarItemView item = mItemsArray.valueAt(i);
                    item.setChecked(false);
                }
            }

            mViewPager.setCurrentItem((Integer) itemView.getTag(), false);
        }
    }

    public void incrementCounterAt(int position) {
        mItemsArray.valueAt(position).setCounter(1);
    }
}
