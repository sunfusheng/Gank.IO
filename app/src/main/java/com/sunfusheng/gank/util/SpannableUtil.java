package com.sunfusheng.gank.util;

import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * @author sunfusheng on 2018/6/8.
 */
public class SpannableUtil {

    public static SpannableString getSpannableString(String wholeText, String targetText, @ColorInt int targetTextColor) {
        if (TextUtils.isEmpty(wholeText)) {
            return new SpannableString("");
        }

        if (TextUtils.isEmpty(targetText)) {
            return new SpannableString(wholeText);
        }

        SpannableString spannableString = new SpannableString(wholeText);
        int startIndex = wholeText.indexOf(targetText);
        if (startIndex != -1) {
            int endIndex = startIndex + targetText.length();
            spannableString.setSpan(new ForegroundColorSpan(targetTextColor), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

}
