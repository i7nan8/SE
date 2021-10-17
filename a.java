package androidx.cardview.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.cardview.widget.g;

class a extends c {
    a() {
    }

    @Override // androidx.cardview.widget.c, androidx.cardview.widget.e
    public void a() {
        g.a = new g.a() {
            /* class androidx.cardview.widget.a.AnonymousClass1 */

            @Override // androidx.cardview.widget.g.a
            public void a(Canvas canvas, RectF rectF, float f, Paint paint) {
                canvas.drawRoundRect(rectF, f, f, paint);
            }
        };
    }
}
