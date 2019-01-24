package com.jenking.xiaoyunhui.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.jenking.xiaoyunhui.R;

public class FMMineHeaderBg extends View {
    private int w;
    private int h;
    private Paint paint;

    private int bgColor = Color.parseColor("#004475");
    private int otherColor = Color.parseColor("#013B65");
    public FMMineHeaderBg(Context context) {
        this(context,null);
    }

    public FMMineHeaderBg(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FMMineHeaderBg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(bgColor);
        Path path = new Path();
        path.moveTo(w,0);
        path.lineTo(w/5*4,0);
        path.lineTo(w/2,h);
        path.lineTo(w,h);
        path.close();
        path.setFillType(Path.FillType.EVEN_ODD);

        canvas.drawPath(path,paint);
    }

    void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(otherColor);
    }


}
