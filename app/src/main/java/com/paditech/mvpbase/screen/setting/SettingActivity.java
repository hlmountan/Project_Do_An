package com.paditech.mvpbase.screen.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.service.NotifyService;

import butterknife.BindView;

/**
 * Created by hung on 5/1/2018.
 */

public class SettingActivity extends MVPActivity<SettingContact.PresenterViewOps> implements SettingContact.ViewOps, View.OnClickListener {

    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_apply)
    Button btn_apply;
    @BindView(R.id.notify)
    LinearLayout notify;
    @BindView(R.id.tv_notify_option)
    TextView tv_notify_option;
    @BindView(R.id.tv_notifi_state)
    TextView tv_notifi_state;
    @BindView(R.id.storage)
    LinearLayout storage;
    @BindView(R.id.view_our_product)
    LinearLayout view_our_product;
    @BindView(R.id.view_feedback)
    LinearLayout view_feedback;
    @BindView(R.id.tv_storage_state)
    TextView tv_storage_state;
    @BindView(R.id.btn_feedback)
    Button btn_feedback;
    @BindView(R.id.btn_about_us)
    Button btn_about_us;
    @BindView(R.id.btn_privacy)
    Button btn_privacy;

    @BindView(R.id.tv_legal_name)
    TextView tv_legal_name;
    @BindView(R.id.tv_legal_content)
    TextView tv_legal_content;

    @Override
    protected int getContentView() {
        return R.layout.act_setting;
    }

    @Override
    protected void initView() {
        btn_privacy.setSelected(true);
        btn_cancel.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        notify.setOnClickListener(this);
        tv_notify_option.setOnClickListener(this);
        storage.setOnClickListener(this);
        btn_privacy.setOnClickListener(this);
        btn_about_us.setOnClickListener(this);
        btn_feedback.setOnClickListener(this);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return SettingPresenter.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_apply:
                finish();
                break;
            case R.id.notify:
                if (tv_notifi_state.getText().equals(getString(R.string.allow))) {
                    tv_notifi_state.setText(R.string.deny);
                    tv_notify_option.setVisibility(View.GONE);
                    this.stopService(new Intent(this, NotifyService.class));

                } else {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null){
                        // Tạo ra một đối tượng Intent cho một dịch vụ (PlaySongService).
                        Intent myIntent = new Intent(this, NotifyService.class);
//         Gọi phương thức startService (Truyền vào đối tượng Intent)
                        this.startService(myIntent);
                    }
                    tv_notifi_state.setText(R.string.allow);
                    tv_notify_option.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_notify_option:
                if (tv_notify_option.getText().equals(getString(R.string.notification_op1)))
                    tv_notify_option.setText(R.string.notification_op2);
                else tv_notify_option.setText(R.string.notification_op1);
                break;
            case R.id.storage:
                if (tv_storage_state.getText().equals(getString(R.string.allow)))
                    tv_storage_state.setText(R.string.deny);
                else tv_storage_state.setText(R.string.allow);
                break;
            case R.id.btn_privacy:
                btn_privacy.setSelected(true);
                btn_about_us.setSelected(false);
                btn_feedback.setSelected(false);

                tv_legal_name.setText(R.string.privacy);
                tv_legal_content.setText(R.string.privacy_content);
                view_our_product.setVisibility(View.GONE);
                view_feedback.setVisibility(View.GONE);
                break;
            case R.id.btn_about_us:
                btn_privacy.setSelected(false);
                btn_about_us.setSelected(true);
                btn_feedback.setSelected(false);
                tv_legal_name.setText(R.string.lenam_store);
                tv_legal_content.setText(R.string.aboutus_content);
                view_our_product.setVisibility(View.VISIBLE);
                view_feedback.setVisibility(View.GONE);
                break;
            case R.id.btn_feedback:
                btn_privacy.setSelected(false);
                btn_about_us.setSelected(false);
                btn_feedback.setSelected(true);
                tv_legal_name.setText(R.string.feedback_title);
                tv_legal_content.setText(R.string.feedback_content);
                view_our_product.setVisibility(View.GONE);
                view_feedback.setVisibility(View.VISIBLE);
                break;
        }
    }
}
