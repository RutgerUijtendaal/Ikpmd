package com.rutger.uijtendaal.ikpmd.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.rutger.uijtendaal.ikpmd.R;
import com.rutger.uijtendaal.ikpmd.di.GlideApp;

/**
 * BindingAdapter for binding Glide to the xml.
 *
 * Converts a url to an image that gets loaded into an ImageView with Glide
 */
public final class ImageBinder {

    private ImageBinder() {

    }

    @BindingAdapter("posterUrl")
    public static void setPosterUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideApp.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(imageView);
    }

    @BindingAdapter("posterHeaderUrl")
    public static void setPosterHeaderUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideApp.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(imageView);
    }
}
