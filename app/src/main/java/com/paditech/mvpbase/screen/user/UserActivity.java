package com.paditech.mvpbase.screen.user;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.screen.profile.ProfileActivity;
import com.paditech.mvpbase.screen.setting.SettingActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by hung on 4/30/2018.
 */

public class UserActivity extends MVPActivity<UserContact.PresenterViewOps> implements UserContact.ViewOps, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "TAG";
    @BindView(R.id.btn_return)
    Button btn_return;
    @BindView(R.id.btn_setting)
    Button btn_setting;
    @BindView(R.id.btn_login_fb)
    Button btn_login_fb;
    @BindView(R.id.btn_login_g)
    Button btn_login_g;

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    String screen;


    @Override
    protected int getContentView() {
        return R.layout.act_user;
    }

    Intent intent;
    private FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;
    private final int RC_SIGN_IN = 100;

    @Override
    protected void initView() {
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        setupGoogle();

        btn_login_fb.setOnClickListener(this);
        btn_login_g.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        intent = getIntent();
        setScreen(intent.getStringExtra("SCREEN"));

    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return UserPresenter.class;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                getPresenter().firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                finish();
                break;
            case R.id.btn_setting:
                this.startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.btn_login_fb:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getPresenter().handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        System.out.println(" cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        System.out.println("error" + error);
                    }
                });
                break;
            case R.id.btn_login_g:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }


    private void setupGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.paditech.mvpbase",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void googleSuccess() {
        if (screen.equals("HOME")) {
            startActivity(new Intent(UserActivity.this, ProfileActivity.class));
        }
        finish();
    }

    @Override
    public void googleAuthenFalse() {

        Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void fbSuccess() {
        if (screen.equals("HOME")) {
            startActivity(new Intent(UserActivity.this, ProfileActivity.class));
        }
        finish();
    }

    @Override
    public void fbAuthenFalse() {
        Toast.makeText(UserActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }
}
