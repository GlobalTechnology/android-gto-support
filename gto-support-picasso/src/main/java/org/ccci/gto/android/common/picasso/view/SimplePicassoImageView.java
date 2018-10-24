package org.ccci.gto.android.common.picasso.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class SimplePicassoImageView extends ImageView implements PicassoImageView {
    protected final Helper mHelper;

    public SimplePicassoImageView(@NonNull final Context context) {
        this(context, null);
    }

    public SimplePicassoImageView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimplePicassoImageView(@NonNull final Context context, @Nullable final AttributeSet attrs,
                                  final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = createHelper(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimplePicassoImageView(@NonNull final Context context, @Nullable final AttributeSet attrs,
                                  final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mHelper = createHelper(attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    protected Helper createHelper(@Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        return new Helper(this, attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    @Override
    public ImageView asImageView() {
        return mHelper.asImageView();
    }

    @Override
    public final void setPicassoFile(@Nullable final File file) {
        mHelper.setPicassoFile(file);
    }

    @Override
    public final void setPicassoUri(@Nullable final Uri uri) {
        mHelper.setPicassoUri(uri);
    }

    @Override
    public final void setPlaceholder(@DrawableRes final int placeholder) {
        mHelper.setPlaceholder(placeholder);
    }

    @Override
    public void setPlaceholder(@Nullable final Drawable placeholder) {
        mHelper.setPlaceholder(placeholder);
    }

    @Override
    public void addTransform(@NonNull final Transformation transform) {
        mHelper.addTransform(transform);
    }

    @Override
    public void setTransforms(@Nullable List<? extends Transformation> transforms) {
        mHelper.setTransforms(transforms);
    }

    @Override
    public void toggleBatchUpdates(final boolean enable) {
        mHelper.toggleBatchUpdates(enable);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHelper != null) {
            mHelper.onAttachedToWindow();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHelper != null) {
            mHelper.onDetachedFromWindow();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void setScaleType(@NonNull final ScaleType scaleType) {
        super.setScaleType(scaleType);
        if (mHelper != null) {
            mHelper.setScaleType(scaleType);
        }
    }
}
