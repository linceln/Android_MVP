package com.xyz.php.config;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.xyz.php.R;

public class LoadingFragment extends DialogFragment {

    public LoadingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ThreeBounce wave = new ThreeBounce();
        wave.setColor(getContext().getResources().getColor(R.color.colorPrimary));

        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminateDrawable(wave);
        return progressBar;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setDimAmount(0);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoadingDialog);
        super.onViewCreated(view, savedInstanceState);
    }
}