package com.yt3.intouchapp.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.yt3.intouchapp.R;

public abstract class BaseDialog extends Dialog implements android.view.View.OnClickListener
{
    protected Activity activity;
    protected Dialog selfReference;
    private int layoutID;

    private float widthRatio = 1, heightRatio = 1;

    public BaseDialog(@NonNull Activity activity, int layoutID, float widthRatio, float heightRatio)
    {
        super(activity, R.style.MapDialogs);
        this.activity       = activity;
        this.layoutID       = layoutID;
        this.widthRatio     = widthRatio;
        this.heightRatio    = heightRatio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(this.layoutID);

        //Width and Height are 90% of parent activity width
        int width   = (int)(this.activity.getResources().getDisplayMetrics().widthPixels*this.widthRatio);
        int height  = (int)(this.activity.getResources().getDisplayMetrics().heightPixels*this.heightRatio);

        this.getWindow().setLayout(width, height);

        this.selfReference = this;
    }

    @Override
    public void onStart()
    {

    }

    @Override
    public abstract void onClick(View view);
}
