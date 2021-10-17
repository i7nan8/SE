package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.cardview.R;

public class CardView extends FrameLayout {
    private static final int[] COLOR_BACKGROUND_ATTR = {16842801};
    private static final e IMPL;
    private final d mCardViewDelegate;
    private boolean mCompatPadding;
    final Rect mContentPadding;
    private boolean mPreventCornerOverlap;
    final Rect mShadowBounds;
    int mUserSetMinHeight;
    int mUserSetMinWidth;

    public void setPadding(int i, int i2, int i3, int i4) {
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new b();
        } else if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new a();
        } else {
            IMPL = new c();
        }
        IMPL.a();
    }

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.cardViewStyle);
    }

    public CardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ColorStateList colorStateList;
        int i2;
        this.mContentPadding = new Rect();
        this.mShadowBounds = new Rect();
        this.mCardViewDelegate = new d() {
            /* class androidx.cardview.widget.CardView.AnonymousClass1 */
            private Drawable b;

            @Override // androidx.cardview.widget.d
            public void a(Drawable drawable) {
                this.b = drawable;
                CardView.this.setBackgroundDrawable(drawable);
            }

            @Override // androidx.cardview.widget.d
            public boolean a() {
                return CardView.this.getUseCompatPadding();
            }

            @Override // androidx.cardview.widget.d
            public boolean b() {
                return CardView.this.getPreventCornerOverlap();
            }

            @Override // androidx.cardview.widget.d
            public void a(int i, int i2, int i3, int i4) {
                CardView.this.mShadowBounds.set(i, i2, i3, i4);
                CardView cardView = CardView.this;
                CardView.super.setPadding(i + cardView.mContentPadding.left, i2 + CardView.this.mContentPadding.top, i3 + CardView.this.mContentPadding.right, i4 + CardView.this.mContentPadding.bottom);
            }

            @Override // androidx.cardview.widget.d
            public void a(int i, int i2) {
                if (i > CardView.this.mUserSetMinWidth) {
                    CardView.super.setMinimumWidth(i);
                }
                if (i2 > CardView.this.mUserSetMinHeight) {
                    CardView.super.setMinimumHeight(i2);
                }
            }

            @Override // androidx.cardview.widget.d
            public Drawable c() {
                return this.b;
            }

            @Override // androidx.cardview.widget.d
            public View d() {
                return CardView.this;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CardView, i, R.style.CardView);
        if (obtainStyledAttributes.hasValue(R.styleable.CardView_cardBackgroundColor)) {
            colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.CardView_cardBackgroundColor);
        } else {
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
            int color = obtainStyledAttributes2.getColor(0, 0);
            obtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            if (fArr[2] > 0.5f) {
                i2 = getResources().getColor(R.color.cardview_light_background);
            } else {
                i2 = getResources().getColor(R.color.cardview_dark_background);
            }
            colorStateList = ColorStateList.valueOf(i2);
        }
        float dimension = obtainStyledAttributes.getDimension(R.styleable.CardView_cardCornerRadius, 0.0f);
        float dimension2 = obtainStyledAttributes.getDimension(R.styleable.CardView_cardElevation, 0.0f);
        float dimension3 = obtainStyledAttributes.getDimension(R.styleable.CardView_cardMaxElevation, 0.0f);
        this.mCompatPadding = obtainStyledAttributes.getBoolean(R.styleable.CardView_cardUseCompatPadding, false);
        this.mPreventCornerOverlap = obtainStyledAttributes.getBoolean(R.styleable.CardView_cardPreventCornerOverlap, true);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_contentPadding, 0);
        this.mContentPadding.left = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_contentPaddingLeft, dimensionPixelSize);
        this.mContentPadding.top = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_contentPaddingTop, dimensionPixelSize);
        this.mContentPadding.right = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_contentPaddingRight, dimensionPixelSize);
        this.mContentPadding.bottom = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_contentPaddingBottom, dimensionPixelSize);
        float f = dimension2 > dimension3 ? dimension2 : dimension3;
        this.mUserSetMinWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_android_minWidth, 0);
        this.mUserSetMinHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_android_minHeight, 0);
        obtainStyledAttributes.recycle();
        IMPL.a(this.mCardViewDelegate, context, colorStateList, dimension, dimension2, f);
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void setUseCompatPadding(boolean z) {
        if (this.mCompatPadding != z) {
            this.mCompatPadding = z;
            IMPL.g(this.mCardViewDelegate);
        }
    }

    public void setContentPadding(int i, int i2, int i3, int i4) {
        this.mContentPadding.set(i, i2, i3, i4);
        IMPL.f(this.mCardViewDelegate);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (!(IMPL instanceof b)) {
            int mode = View.MeasureSpec.getMode(i);
            if (mode == Integer.MIN_VALUE || mode == 1073741824) {
                i = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) IMPL.b(this.mCardViewDelegate)), View.MeasureSpec.getSize(i)), mode);
            }
            int mode2 = View.MeasureSpec.getMode(i2);
            if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
                i2 = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) IMPL.c(this.mCardViewDelegate)), View.MeasureSpec.getSize(i2)), mode2);
            }
            super.onMeasure(i, i2);
            return;
        }
        super.onMeasure(i, i2);
    }

    public void setMinimumWidth(int i) {
        this.mUserSetMinWidth = i;
        super.setMinimumWidth(i);
    }

    public void setMinimumHeight(int i) {
        this.mUserSetMinHeight = i;
        super.setMinimumHeight(i);
    }

    public void setCardBackgroundColor(int i) {
        IMPL.a(this.mCardViewDelegate, ColorStateList.valueOf(i));
    }

    public void setCardBackgroundColor(ColorStateList colorStateList) {
        IMPL.a(this.mCardViewDelegate, colorStateList);
    }

    public ColorStateList getCardBackgroundColor() {
        return IMPL.i(this.mCardViewDelegate);
    }

    public int getContentPaddingLeft() {
        return this.mContentPadding.left;
    }

    public int getContentPaddingRight() {
        return this.mContentPadding.right;
    }

    public int getContentPaddingTop() {
        return this.mContentPadding.top;
    }

    public int getContentPaddingBottom() {
        return this.mContentPadding.bottom;
    }

    public void setRadius(float f) {
        IMPL.a(this.mCardViewDelegate, f);
    }

    public float getRadius() {
        return IMPL.d(this.mCardViewDelegate);
    }

    public void setCardElevation(float f) {
        IMPL.c(this.mCardViewDelegate, f);
    }

    public float getCardElevation() {
        return IMPL.e(this.mCardViewDelegate);
    }

    public void setMaxCardElevation(float f) {
        IMPL.b(this.mCardViewDelegate, f);
    }

    public float getMaxCardElevation() {
        return IMPL.a(this.mCardViewDelegate);
    }

    public boolean getPreventCornerOverlap() {
        return this.mPreventCornerOverlap;
    }

    public void setPreventCornerOverlap(boolean z) {
        if (z != this.mPreventCornerOverlap) {
            this.mPreventCornerOverlap = z;
            IMPL.h(this.mCardViewDelegate);
        }
    }
}
