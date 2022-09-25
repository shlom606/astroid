package com.example.astroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    DrawSurfaceView ds;
    MediaPlayer mp;
    AudioManager am;
    Thread thread;
    FrameLayout frame;
    Button btnD;
    Button btnU;
    Button btnR;
    Button btnL;
    Button run;
    Button pause;
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnD = findViewById(R.id.btnD);
        btnU = findViewById(R.id.btnU);
        btnR = findViewById(R.id.btnR);
        btnL = findViewById(R.id.btnL);
        frame = findViewById(R.id.frame);
        ds = new DrawSurfaceView(this);
        mp=MediaPlayer.create(this,R.raw.songcoco);
        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,50,0);
        mp.start();
        count=findViewById(R.id.count);

        frame.addView(ds);
        thread = new Thread(ds);
        thread.start();
        //ds.moveD();
    }

    boolean OnlyPause=false;
    @Override
    protected void onPause(){
        super.onPause();
        mp.release();
    }
    public void pauseResume(View view)
    {
        OnlyPause=true;
        ds.SetIsRunning(false);
        mp.pause();
    }

    public void playResume(View view)
    {
        OnlyPause=false;
        ds.SetIsRunning(true);
        mp.start();

    }
    public void move(View view)
    {
       btnD.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ds.moveD();
           }
       });
       btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //countClicks++;
                ds.moveU();
                try {
                    Thread.sleep(1000-fasterby);


                } catch (InterruptedException e) {

                }
                ds.moveD();
                fasterby+=50;
                countClicks++;
                count.setText(Integer.toString(countClicks));



            }
       });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.moveR();
            }
        });
        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent myIntent = new Intent(GameActivity.this, losescreen.class);
              startActivity(myIntent);


            }
        });

    }
    int countClicks =0,fasterby=0;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean flag = false;
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            {
                if(y> ds.GetX()&&flag == false)
                {
                    ds.moveD();
                    flag = true;
                }

            }
            case MotionEvent.ACTION_MOVE:
            {

                 if(x<ds.GetX())
                 {
                     ds.moveL();
                     flag = true;
                 }
                 if(x>ds.GetX())
                 {
                     ds.moveR();
                     flag = true;
                 }
            }
            case MotionEvent.ACTION_UP:
            {

               if(y< ds.GetY())
               {

                   ds.moveU();
                   try {
                       Thread.sleep(1000-fasterby);

                   } catch (InterruptedException e) {

                   }
                   ds.moveD();
                   fasterby+=70;
                   countClicks++;
                   count.setText(Integer.toString(countClicks));
                   if(countClicks==15|| fasterby>=990){
                       Intent myIntent = new Intent(GameActivity.this, winscreen.class);
                       startActivity(myIntent);
                   }
                   if(!ds.checkIfStop()&&!OnlyPause){
                       Intent myIntent = new Intent(GameActivity.this, losescreen.class);
                       startActivity(myIntent);

                   }



               }
            }
        }
        return false;
    }
}
