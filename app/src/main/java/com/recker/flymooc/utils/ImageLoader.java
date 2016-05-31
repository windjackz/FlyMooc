package com.recker.flymooc.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by recker on 16/5/31.
 */
public class ImageLoader {

    private static ImageLoader instance;

    private Picasso mImageLoader;

    private ImageLoader(){};

    private ImageLoader(Context context) {
        mImageLoader = Picasso.with(context);
    }

    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader(context);
                }
            }
        }
        return instance;
    }

    public void load(String url, ImageView iv) {
        mImageLoader.load(url).into(iv);

    }

}
