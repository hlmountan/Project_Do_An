package com.paditech.mvpbase.screen.notification;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseDialog;
import com.paditech.mvpbase.common.event.NewNotificationEvent;
import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.NotificationRecycleViewAdapter;
import com.paditech.mvpbase.screen.login.LoginActivity;
import com.paditech.mvpbase.screen.main.MainActivity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Created by hung on 5/15/2018.
 */

public class NotificationActivity extends MVPFragment<NotificationContact.PresenterViewOps> implements
        NotificationContact.ViewOps, View.OnClickListener {


    @BindView(R.id.recycler_view_notification)
    RecyclerView recycler_view_notification;
    @BindView(R.id.btn_filter)
    Button filter;
    NotificationRecycleViewAdapter mNotificationRecycleViewAdapter;
    List<Notification> listNotify;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    public static NotificationActivity newInstance() {
        return new NotificationActivity();
    }

    @Override
    protected int getContentView() {
        return R.layout.act_notification;
    }

    @Override
    protected void initView(View view) {
        getPresenter().getListNotify();
        filter.setOnClickListener(this);
        mNotificationRecycleViewAdapter = new NotificationRecycleViewAdapter();
        recycler_view_notification.addItemDecoration(new SimpleDividerItemDecoration(getActivityContext(), 100, 20));
        recycler_view_notification.setLayoutManager(new LinearLayoutManager(getActivityContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view_notification.setAdapter(mNotificationRecycleViewAdapter);
    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return NotificationPresenter.class;
    }

    @Override
    public void onClick(View view) {
        if (filter.getText().toString().equals(R.string.notify_filter_all))
            filter.setText(R.string.notify_filter_New);
        else filter.setText(R.string.notify_filter_all);
    }

    @Override
    public void setListNotify(List<Notification> listNotify) {
        if (listNotify != null && mNotificationRecycleViewAdapter != null )
            mNotificationRecycleViewAdapter.setListNotify(listNotify);
        else showConfirmDialog(getString(R.string.not_login), new BaseDialog.OnPositiveClickListener() {
            @Override
            public void onPositiveClick() {
                 startActivity(new Intent(getActivityContext(), LoginActivity.class));
            }
        }, null);
    }

    @Override
    public void hasNew() {
        ((MainActivity) Objects.requireNonNull(getActivity())).onNewNotification(new NewNotificationEvent());
    }
}
