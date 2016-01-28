package com.adhamenaya.moviesapp.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by AENAYA on 7/10/2015.
 */
public class Util {

    public static final String imageBaseUrl = "http://image.tmdb.org/t/p/";
    public static final String imageSize = "w185";
    private static final String DIR = "udacity_movies";

    /**
     * Get the full path of the image
     */
    public static String getImagePath(String imageName) {
        return imageBaseUrl + imageSize + imageName + "/";
    }

    /**
     * Show toast message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Perform some animation on the given view
     */
    public static void showView(final View view, boolean show) {
        if (show) {
            view.animate().alpha(1.0f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            view.animate().alpha(0.0f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    /**
     * Check the orientation of the screen
     */
    public static boolean isLand(Context context) {

        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Format the long numbers
     */
    public static String prepareLongNumber(Long number) {
        if (number == null || number == 0) return "No data";
        else if (number >= 1000000) return String.valueOf(number / 10000000) + "M $";
        else if (number >= 1000) return String.valueOf(number / 1000) + "K $";
        else return String.valueOf(number) + " $";
    }

    public static void loadImage(Context context, final String imageName, Picasso picasso, ImageView image) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + DIR
                + imageName);

        if (imageName != null)
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                Log.d("image local", uri.toString());
                picasso.with(context).load(uri).into(image);
            } else {
                Log.d("image internet", getImagePath(imageName));
                picasso.with(context)
                        .load(getImagePath(imageName))
                        .into(image);
            }

    }

    public static void saveImage(final String imageName, final Context context, final Picasso picasso) {

        if (imageName == null) return;

        final Target target = new Target() {


            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        File dir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + DIR);
                        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + DIR
                                + imageName);
                        try {
                            if (!dir.exists())
                                dir.mkdir();

                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(CompressFormat.JPEG, 80, ostream);
                            ostream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                final RequestCreator requestCreator = picasso.with(context).load(getImagePath(imageName));

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        requestCreator.into(target);
                    }
                });
            }
        }).start();
    }
}
