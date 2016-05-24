package com.recker.flymooc.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.recker.flymooc.R;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/24.
 */
public class Loading {

    private static Loading instance;

    private Dialog mDialog;

    private View dialogView;

    private LayoutInflater inflater;

    private ImageView ivLoading;

    private Loading() {

    }

    private Loading(Context context) {
        inflater = LayoutInflater.from(context);
        mDialog = new Dialog(context, R.style.DialogProgress);
        dialogView = inflater.inflate(R.layout.dialog_loading, null);
        mDialog.setContentView(dialogView);
        ivLoading = ButterKnife.findById(dialogView, R.id.iv_loading);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.main_loading_anim);
        ivLoading.startAnimation(anim);
    }

    public static Loading getInstance(Context context) {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new Loading(context);
                }
            }
        }
        return instance;
    }

    public void show() {
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }

}
