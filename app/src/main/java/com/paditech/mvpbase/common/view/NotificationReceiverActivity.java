package com.paditech.mvpbase.common.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.paditech.mvpbase.R;

import butterknife.BindView;

/**
 * Created by hung on 5/28/2018.
 */

public class NotificationReceiverActivity extends Activity {
    @BindView(R.id.tv_cmt_content)
    TextView tv_cmt_content;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.notify_result);
    }





}
