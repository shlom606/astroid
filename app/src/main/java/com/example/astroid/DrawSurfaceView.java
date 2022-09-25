package com.example.astroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;



public class DrawSurfaceView extends SurfaceView implements Runnable {
    int interval=10;//try to change it
    float dx=10;
    float dy=10;
    Context context;
    SurfaceHolder holder;
    boolean threadRunning = true;
    boolean isRunning = true;
    Bitmap bitmap,bitmap1;
    float x =100;
    float y =100;
    Button pause = findViewById(R.id.btnpause);
    Button run = findViewById(R.id.btnpause);

    int co = 0;

    public DrawSurfaceView(Context context)
    {
        super(context);
        this.context = context;
        holder = getHolder();
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.coconut);
        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.beach);

    }
    public void SetIsRunning(boolean flag)
    {
        this.isRunning=flag;
    }
    public float GetX()
    {
        return this.x;
    }
    public float GetY()
    {
        return this.y;
    }

    @Override
    public void run() {
        while (threadRunning)
        {
            if(isRunning)
            {
               if(!holder.getSurface().isValid())
                   continue;

                Canvas c = null;
                try
                {

                    c = this.getHolder().lockCanvas();//what with line meaning?
                    synchronized (this.getHolder()){

                        c.drawRGB(255,155,0);//Try pushing this line into a remark. what happened? you can change the color.
                        c.drawBitmap(bitmap1,0,0,null);
                        c.drawBitmap(bitmap,x,y,null);

                        automaticMove();




                    }
                    Thread.sleep(interval);
                }
                catch (Exception e)
                {

                }
                /*what is finally?
                */
                finally {
                    if(c!=null)
                    {
                        this.getHolder().unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }

    /*
    how it works?
     */
    public void automaticMove()
    {
        if(y==2200){
            SetIsRunning(false);

            checkIfLose();
        }
        x = x + dx;
        y = y + dy;

        if(x < 0 || x > this.getWidth())
            dx = -dx;
        if(y < 0 || y > this.getHeight())
            dy = -dy;

    }

    public boolean checkIfLose()
    {
        if(y==1300){
            SetIsRunning(false);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkIfStop(){
        if(isRunning){
            return true;
        }
        else {
            return false;
        }
    }
    public void moveD()
    {

       dy = Math.abs(dy);

    }
    public void moveU()
    {
        dy = -Math.abs(dy);
    }
    public void moveR()
    {
        dx = Math.abs(dx);
    }
    public void moveL()
    {
        dx = -Math.abs(dx);
    }
}
