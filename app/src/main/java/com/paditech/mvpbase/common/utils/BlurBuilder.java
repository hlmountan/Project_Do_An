package com.paditech.mvpbase.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

/**
 * Created by Nha Nha on 3/3/2018.
 */

public class BlurBuilder {

    private static final float BITMAP_SCALE = 0.2f;
    private static final float BLUR_RADIUS = 24f;

    @SuppressLint("StaticFieldLeak")
    public static void blur(final Context context, final String url, final GetBlurBitmapListener listener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Bitmap bitmap = Glide.with(context).asBitmap().load(url).submit().get();
                    int width = Math.round(bitmap.getWidth() * BITMAP_SCALE);
                    int height = Math.round(bitmap.getHeight() * BITMAP_SCALE);

                    Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

                    RenderScript rs = RenderScript.create(context);
                    ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                    Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
                    Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
                    theIntrinsic.setRadius(BLUR_RADIUS);
                    theIntrinsic.setInput(tmpIn);
                    theIntrinsic.forEach(tmpOut);
                    tmpOut.copyTo(outputBitmap);

                    if (outputBitmap != null) listener.setBitmap(outputBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();


    }

    public interface GetBlurBitmapListener {
        void setBitmap(Bitmap bitmap);
    }
}
