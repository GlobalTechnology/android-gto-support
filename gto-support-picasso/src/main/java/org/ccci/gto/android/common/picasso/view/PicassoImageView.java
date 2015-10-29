package org.ccci.gto.android.common.picasso.view;

import static org.ccci.gto.android.common.Constants.INVALID_DRAWABLE_RES;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.ccci.gto.android.common.model.Dimension;
import org.ccci.gto.android.common.picasso.R;
import org.ccci.gto.android.common.picasso.ScaleTransformation;

public interface PicassoImageView {
    final class Helper {
        @NonNull
        private final ImageView mView;

        @Nullable
        private Uri mPicassoUri;
        @NonNull
        private Dimension mSize = new Dimension(0, 0);
        @DrawableRes
        private int mPlaceholderResId = INVALID_DRAWABLE_RES;

        public Helper(@NonNull final ImageView view) {
            mView = view;
            init(mView.getContext(), null, 0, 0);
        }

        public Helper(@NonNull final ImageView view, @Nullable final AttributeSet attrs, final int defStyleAttr,
                      final int defStyleRes) {
            mView = view;
            init(mView.getContext(), attrs, defStyleAttr, defStyleRes);
        }

        private void init(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr,
                          final int defStyleRes) {
            final TypedArray a =
                    context.obtainStyledAttributes(attrs, R.styleable.PicassoImageView, defStyleAttr, defStyleRes);
            mPlaceholderResId = a.getResourceId(R.styleable.PicassoImageView_placeholder, INVALID_DRAWABLE_RES);
            a.recycle();
        }

        public void setPicassoUri(@Nullable final Uri uri) {
            mPicassoUri = uri;
            triggerUpdate();
        }

        public void setPlaceholder(@DrawableRes final int placeholder) {
            mPlaceholderResId = placeholder;
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (oldw != w || oldh != h) {
                mSize = new Dimension(w, h);
                triggerUpdate();
            }
        }

        public void setScaleType(final ScaleType type) {
            triggerUpdate();
        }

        private void triggerUpdate() {
            final RequestCreator update = Picasso.with(mView.getContext()).load(mPicassoUri);
            if (mPlaceholderResId != INVALID_DRAWABLE_RES) {
                update.placeholder(mPlaceholderResId);
            }
            if (mSize.width > 0 || mSize.height > 0) {
                switch (mView.getScaleType()) {
                    case CENTER_CROP:
                        update.resize(mSize.width, mSize.height);
                        update.onlyScaleDown();
                        update.centerCrop();
                        break;
                    case CENTER_INSIDE:
                        update.resize(mSize.width, mSize.height);
                        update.onlyScaleDown();
                        update.centerInside();
                        break;
                    default:
                        update.transform(new ScaleTransformation(mSize.width, mSize.height));
                        break;
                }
            }

            // fetch or load based on the target size
            if (mSize.width > 0 || mSize.height > 0) {
                update.into(mView);
            } else {
                update.fetch();
            }
        }
    }

    void setPicassoUri(@Nullable Uri uri);

    void setPlaceholder(@DrawableRes int placeholder);
}
