package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.TintableBackgroundView;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@SuppressLint("RestrictedApi")
public class AppCompatTabLayout extends TabLayout implements TintableBackgroundView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper;

    public AppCompatTabLayout(final Context context) {
        this(context, null);
    }

    public AppCompatTabLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppCompatTabLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(TintContextWrapper.wrap(context), attrs, defStyleAttr);

        mBackgroundTintHelper = new AppCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundDrawable(background);
        }
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#setBackgroundTintList(android.view.View, ColorStateList)}
     *
     * @hide
     */
    @RestrictTo(LIBRARY)
    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.setSupportBackgroundTintList(tint);
        }
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#getBackgroundTintList(android.view.View)}
     *
     * @hide
     */
    @RestrictTo(LIBRARY)
    @Override
    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        return mBackgroundTintHelper != null ? mBackgroundTintHelper.getSupportBackgroundTintList() : null;
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#setBackgroundTintMode(android.view.View, PorterDuff.Mode)}
     *
     * @hide
     */
    @RestrictTo(LIBRARY)
    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#getBackgroundTintMode(android.view.View)}
     *
     * @hide
     */
    @RestrictTo(LIBRARY)
    @Override
    @Nullable
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return mBackgroundTintHelper != null ? mBackgroundTintHelper.getSupportBackgroundTintMode() : null;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }
}
