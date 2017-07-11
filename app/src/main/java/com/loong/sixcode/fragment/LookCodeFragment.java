package com.loong.sixcode.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loong.sixcode.R;

/**
 * Created by lxl on 2017/6/10.
 */

public class LookCodeFragment extends Fragment implements View.OnClickListener{
    private LinearLayout menuViewSmall,otherView;
    private ImageView menu;
    private FragmentManager fm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_look,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        menuViewSmall= (LinearLayout) view.findViewById(R.id.menu_view_small);
        otherView= (LinearLayout) view.findViewById(R.id.other_view);
        menu= (ImageView) view.findViewById(R.id.menu);

        initListener(view);
    }

    private void initListener(View view) {
        view.findViewById(R.id.one).setOnClickListener(this);
        view.findViewById(R.id.two).setOnClickListener(this);
        view.findViewById(R.id.three).setOnClickListener(this);
        view.findViewById(R.id.four).setOnClickListener(this);
        view.findViewById(R.id.menu).setOnClickListener(this);
        initData();
    }

    private void initData() {
        fm=getFragmentManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                showMenu(new HistoryCodeFragment());
                break;
            case R.id.two:
                showMenu(new HistoryCodeFragment());
                break;
            case R.id.three:
                break;
            case R.id.four:
                break;
            case R.id.menu:
                hideMenu();
                break;

        }
    }


    private void showMenu(final Fragment showFragment){
        AnimatorSet set = new AnimatorSet() ;
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(menuViewSmall, "translationX", 0, 520);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(menuViewSmall, "translationY", 0, -620);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(menuViewSmall, "scaleX", 1f, 0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(menuViewSmall, "scaleY", 1f, 0f);
        animator4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                menu.setVisibility(View.VISIBLE);
                menuViewSmall.setVisibility(View.GONE);
                otherView.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction ft=fm.beginTransaction();
                        ft.replace(R.id.other_view,showFragment);
                        ft.commit();
                    }
                }).start();
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        set.setDuration(1000);
        set.play(animator1).with(animator2).with(animator3).with(animator4);
        set.start();
    }

    private void hideMenu(){
        AnimatorSet set1 = new AnimatorSet() ;
        ObjectAnimator animator11 = ObjectAnimator.ofFloat(menuViewSmall, "translationX", 500,0);
        ObjectAnimator animator22 = ObjectAnimator.ofFloat(menuViewSmall, "translationY", -620,0);
        ObjectAnimator animator33 = ObjectAnimator.ofFloat(menuViewSmall, "scaleX", 0f, 1f);
        ObjectAnimator animator44 = ObjectAnimator.ofFloat(menuViewSmall, "scaleY", 0f, 1f);
        animator44.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.setVisibility(View.GONE);
                menuViewSmall.setVisibility(View.VISIBLE);
                otherView.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        set1.setDuration(1000);
        set1.play(animator11).with(animator22).with(animator33).with(animator44);
        set1.start();
    }
}
