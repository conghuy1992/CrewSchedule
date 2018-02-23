package com.dazone.crewschedule.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.FaceDetector;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.dazone.crewschedule.CustomView.FaceOverlayView;
import com.dazone.crewschedule.CustomView.FaceOverlayViewForSizeOther;
import com.dazone.crewschedule.Customs.CircleTransform;
import com.dazone.crewschedule.Interfaces.DrawImageItem;
import com.dazone.crewschedule.Interfaces.MenuDrawItem;
import com.dazone.crewschedule.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by david on 12/25/15.
 */
public class ImageUtils {
    public static String TAG = "ImageUtils";

    @TargetApi(Build.VERSION_CODES.M)
    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static int viewHeight, viewWidth;
    public static FaceDetector myFaceDetect;
    public static FaceDetector.Face[] myFace;
    public static float myEyesDistance;
    public static InputStream response;
    public static URLConnection connection = null;
    public static URL url;
    public static void showImage_custom_other(DrawImageItem dto, final FaceOverlayViewForSizeOther view) {
        if (TextUtils.isEmpty(dto.getImageLink())) {

        } else {
            String getLink = new Prefs().getServerSite() + dto.getImageLink();
            new DownloadImageTask_other(view).execute(getLink);
        }
    }

    public static void showImage_custom(DrawImageItem dto, final FaceOverlayView view) {
        if (TextUtils.isEmpty(dto.getImageLink())) {

        } else {
            String getLink = new Prefs().getServerSite() + dto.getImageLink();
            new DownloadImageTask(view).execute(getLink);
        }
    }
    public static void showImage(DrawImageItem dto, final ImageView view) {
        if (TextUtils.isEmpty(dto.getImageLink())) {

        } else {
            showImage(dto.getImageLink(), view);
//            String getLink = new Prefs().getServerSite() + dto.getImageLink();
//            new DownloadImageTask(view).execute(getLink);
        }
    }

    public static void showRoundImage(DrawImageItem dto, ImageView view) {
        if (TextUtils.isEmpty(dto.getImageLink())) {
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate color based on a key (same key returns the same color), useful for list/grid views
            int iconColor = generator.getColor(DaZoneTextUtils.getFirstLetter(dto.getImageTitle()));
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(DaZoneTextUtils.getFirstLetter(dto.getImageTitle()), iconColor);
            drawable.setPadding(1, 1, 1, 1);
            view.setImageDrawable(drawable);
        } else {
            showRoundImage(dto.getImageLink(), view);
        }
    }

    //    public static void showBadgeImage(int count,ImageView view)
//    {
//            TextDrawable drawable = TextDrawable.builder()
//                    .buildRound(String.valueOf(count), ImageUtils.getColor(view.getContext(), R.color.badge_bg_color));
//            drawable.setPadding(1,1,1,1);
//            view.setImageDrawable(drawable);
//    }
    public static void showImage(MenuDrawItem dto, ImageView view) {
        if (TextUtils.isEmpty(dto.getMenuIconUrl())) {
            view.setImageResource(dto.getIconResID());
        } else {
            showImage(dto.getMenuIconUrl(), view);
        }
    }

    public static void showRoundImage(String url, ImageView view) {
        if (url.contains("content") || url.contains("storage")) {
            File f = new File(url);
            if (f.exists()) {
                Picasso.with(view.getContext()).load(f).transform(new CircleTransform()).into(view);
            } else {
                Picasso.with(view.getContext()).load(url).transform(new CircleTransform()).into(view);
            }
        } else {
            Picasso.with(view.getContext()).load(new Prefs().getServerSite() + url).transform(new CircleTransform()).into(view);
        }
    }

    public static void showImage(String url, ImageView view) {
        if (url.contains("content") || url.contains("storage")) {
            File f = new File(url);
            if (f.exists()) {
                Picasso.with(view.getContext()).load(f).into(view);
            } else {
                Picasso.with(view.getContext()).load(url).into(view);
            }
        } else {
            Picasso.with(view.getContext()).load(new Prefs().getServerSite() + url).into(view);
        }
    }

    public static Bitmap cycleBitmap(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;

    }

    //    public static void drawGroupAvatar(Context context,ImageView imv)
//    {
//        Bitmap square1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_white_24dp);
//        Bitmap square2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_white_24dp);
//        Bitmap square3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_white_24dp);
//        Bitmap square4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_white_24dp);
//
//        Bitmap big = Bitmap.createBitmap(square1.getWidth() * 2, square1.getHeight() * 2, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(big);
//        canvas.drawBitmap(square1, 0, 0, null);
//        canvas.drawBitmap(square2, square1.getWidth(), 0, null);
//        canvas.drawBitmap(square3, 0, square1.getHeight(), null);
//        canvas.drawBitmap(square4, square1.getWidth(), square1.getHeight(), null);
//
//        imv.setImageBitmap(cycleBitmap(big));
//    }
    public static InputStream input;

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        FaceOverlayView bmImage;
        Bitmap bitmapOrg;

        public DownloadImageTask(FaceOverlayView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                input = connection.getInputStream();
                bitmapOrg = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmapOrg;
        }

        protected void onPostExecute(final Bitmap result) {
            bmImage.setBitmap(result);
        }
    }
    public static class DownloadImageTask_other extends AsyncTask<String, Void, Bitmap> {
        FaceOverlayViewForSizeOther bmImage;
        Bitmap bitmapOrg;

        public DownloadImageTask_other(FaceOverlayViewForSizeOther bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                input = connection.getInputStream();
                bitmapOrg = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmapOrg;
        }

        protected void onPostExecute(final Bitmap result) {
            bmImage.setBitmap(result);
        }
    }
}
