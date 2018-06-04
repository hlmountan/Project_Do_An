package com.paditech.mvpbase.common.dialog;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hung on 5/24/2018.
 */

public class JoinDeveloperDialogue extends BaseDialog  {
    @BindView(R.id.tv_message)
    TextView messConfirm;
    @BindView(R.id.btn_ok)
    TextView mBtnOk;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_confirm)
    TextView btn_confirm;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.tv_title)
    TextView enter_email;
    @BindView(R.id.view_confirm)
    LinearLayout view_confirm;
    @BindView(R.id.tv_email)
    TextView email;

    private OnPositiveClickListener mOnPositiveClickListener;
    private OnNegativeClickListener mOnNegativeClickListener;
    private String message, okText, cancelText;
    private boolean hasTitle;


    public static JoinDeveloperDialogue newInstance() {
        JoinDeveloperDialogue joinDeveloperDialogue = new JoinDeveloperDialogue();
        return joinDeveloperDialogue;
    }



    @Override
    protected int getContentView() {
        return R.layout.dialogue_joindev;
    }

    @Override
    protected void initView(View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email.setHint(user.getEmail());
//        et_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // email sent
                                    Boolean requesst = true;
                                    FirebaseDatabase.getInstance().getReference().child("user").
                                            child(user.getUid()).child("requestDev").setValue(requesst);
                                    // after email is sent just logout the user and finish this activity
                                    Toast.makeText(getContext(),"sent verify mail success",Toast.LENGTH_LONG);
                                    view_confirm.setVisibility(View.GONE);
                                    messConfirm.setText(getString(R.string.confirm_email));
                                    mBtnOk.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    // email not sent, so display message and restart the activity or do whatever you wish to do
                                    Toast.makeText(getContext(),task.getException().toString(),Toast.LENGTH_LONG);


                                }
                            }
                        });
            }
        });
    }


    @OnClick(R.id.btn_ok)
    public void onPositive() {
        dismiss();
        if (mOnPositiveClickListener != null) mOnPositiveClickListener.onPositiveClick();
    }

    @OnClick(R.id.btn_cancel)
    public void onNegative() {
        dismiss();
        if (mOnNegativeClickListener != null) mOnNegativeClickListener.onNegativeClick();
    }


}
