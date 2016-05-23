package com.recker.flymooc.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.recker.flymooc.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 */
public class DownloadFragment extends Fragment {


    @Bind(R.id.tv_cons)
    TextView mTvCons;

    @Bind(R.id.tv_clear)
    TextView mTvClear;

    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download, container, false);
        ButterKnife.bind(this, view);

        getDiveceSize();

        return view;
    }

    /**
     * 获取设备容量
     */
    private void getDiveceSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());

        long blockSize;
        long totalBlocks;
        long availableBlocks;

        // 由于API18（Android4.3）以后getBlockSize过时并且改为了getBlockSizeLong
        // 因此这里需要根据版本号来使用那一套API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }

//        String totalText = formatSize(blockSize * totalBlocks);
        String occupyText = formatSize(blockSize * (totalBlocks-availableBlocks));
        String availableText = formatSize(blockSize * availableBlocks);

        String text = "已占用"+occupyText+"，剩余"+availableText+"可用";
        mTvCons.setText(text);

        mProgressBar.setMax((int) totalBlocks);
        mProgressBar.setProgress((int)(totalBlocks-availableBlocks));

    }

    private String formatSize(long size) {
        return Formatter.formatFileSize(getActivity(), size);
    }

}
