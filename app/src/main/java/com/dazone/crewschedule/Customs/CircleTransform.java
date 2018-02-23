package com.dazone.crewschedule.Customs;

import android.graphics.Bitmap;

import com.dazone.crewschedule.Utils.ImageUtils;
import com.squareup.picasso.Transformation;

/**
 * Created by david on 12/25/15.
 */
public class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        return ImageUtils.cycleBitmap(source);
    }

    @Override
    public String key() {
        return "circle";
    }
}