package com.calculator.heroicsandwich.sheep_crush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback
{
    Bitmap mybitmap;

    public BoardView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Canvas c = holder.lockCanvas();
        this.onDraw(c);
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    protected void onDraw(Canvas c)
    {
        c.drawColor(Color.CYAN);
        Rect dst = new Rect();
        dst.set(500, 1500, 1000, 2000);
        c.drawBitmap(mybitmap, null, dst, null);
    }
}
