package com.paditech.mvpbase.screen.cmt;

import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.List;

/**
 * Created by hung on 4/28/2018.
 */

public interface CommentContact {
    interface ViewOps extends ActivityViewOps{
        void setCmt(List<Cmt> cmtList);
    }
    interface PresenterViewOps extends ActivityPresenterViewOps{
        void getCmt(String appid);
    }
}
