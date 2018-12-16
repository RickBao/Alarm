package com.demo.nick.alarm.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;


/**
 * Utility helper functions for time and date pickers
 * Created by nick on 18-12-16.
 */

public class TimePickerUtils {


    /**
     * Return a tinted drawable from the given drawable resource, if {@code tintList != null}.
     * Otherwise, no tint will be applied.
     */
    public static Drawable getTintedDrawable(@NonNull Context context,
                                             @DrawableRes int drawableRes,
                                             @Nullable ColorStateList tintList) {
        Drawable d = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableRes).mutate());
        DrawableCompat.setTintList(d, tintList);
        return d;
    }
}
