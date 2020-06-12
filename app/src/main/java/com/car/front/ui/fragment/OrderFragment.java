package com.car.front.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.car.front.R;
import com.car.front.adapter.FragmentAdapter;
import com.car.front.base.BaseFragment;
import com.car.front.weight.SuperViewPager;
import java.util.ArrayList;
import java.util.List;
import crossoverone.statuslib.StatusUtil;

public class OrderFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private View rootView;
    private boolean isPrepared;
    private TextView text_top_name;
    private SuperViewPager superViewPager;
    private FragmentAdapter mFragmentAdapter;
    private TextView tv_publicMessageLine, tv_remindMessageLine, tv_publicMessage, tv_remindMessage, tv_tanb, tv_tanb_line;
    private LinearLayout rl_tab1, rl_tab2, rl_tab3;
    private List<Fragment> mFragmentList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_inventory_order, container, false);
            initView();
            isPrepared = true;
            lazyLoad();
        }
        return rootView;
    }

    private void initView() {
        rl_tab3 = getView(rootView, R.id.rl_tab3);
        tv_tanb_line = getView(rootView, R.id.tv_tanb_line);
        tv_tanb = getView(rootView, R.id.tv_tanb);
        superViewPager = getView(rootView, R.id.viewPager);
        text_top_name = getView(rootView, R.id.text_top_name);
        text_top_name.setText("清单");
        tv_publicMessageLine = getView(rootView, R.id.tv_publicMessageLine);
        tv_remindMessageLine = getView(rootView, R.id.tv_remindMessageLine);
        tv_publicMessage = getView(rootView, R.id.tv_publicMessage);
        tv_remindMessage = getView(rootView, R.id.tv_remindMessage);
        rl_tab1 = getView(rootView, R.id.rl_tab1);
        rl_tab2 = getView(rootView, R.id.rl_tab2);
        rl_tab1.setOnClickListener(new MyOnClickListener(0));
        rl_tab2.setOnClickListener(new MyOnClickListener(1));
        rl_tab3.setOnClickListener(new MyOnClickListener(2));
        mFragmentList.add(new StockFragment());
        mFragmentList.add(new InventoryFragment());
        mFragmentList.add(new OwnerFragment());
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mFragmentList);
        superViewPager.setAdapter(mFragmentAdapter);
        superViewPager.setCurrentItem(0);
        superViewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setView(position);
        superViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            setView(index);
            superViewPager.setCurrentItem(index);
        }
    }

    public void setView(int index) {
        clealView();
        switch (index) {
            case 0:
                tv_remindMessageLine.setVisibility(View.VISIBLE);
                tv_remindMessage.setTextColor(Color.parseColor("#3F80F4"));
                break;
            case 1:
                tv_publicMessageLine.setVisibility(View.VISIBLE);
                tv_publicMessage.setTextColor(Color.parseColor("#3F80F4"));
                break;
            case 2:
                tv_tanb_line.setVisibility(View.VISIBLE);
                tv_tanb.setTextColor(Color.parseColor("#3F80F4"));
                break;
        }
    }


    public void clealView() {
        tv_remindMessage.setTextColor(Color.parseColor("#5e5e5e"));
        tv_publicMessage.setTextColor(Color.parseColor("#5e5e5e"));
        tv_tanb.setTextColor(Color.parseColor("#5e5e5e"));
        tv_remindMessageLine.setVisibility(View.INVISIBLE);
        tv_publicMessageLine.setVisibility(View.INVISIBLE);
        tv_tanb_line.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        StatusUtil.setUseStatusBarColor(getActivity(), Color.parseColor("#FFFFFF"));
    }

}
