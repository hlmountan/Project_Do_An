package com.paditech.mvpbase.screen.user;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

/**
 * Created by hung on 4/30/2018.
 */

public interface UserContact {
    interface ViewOps extends ActivityViewOps {
        void googleSuccess();
        void googleAuthenFalse();
        void fbSuccess();
        void fbAuthenFalse();

    }

    interface PresenterViewOps extends ActivityPresenterViewOps {
        void handleFacebookAccessToken(AccessToken token);

        void setupGoogle();

        void firebaseAuthWithGoogle(GoogleSignInAccount acct);

        void createNewUser(UserProfile user);
    }

}
