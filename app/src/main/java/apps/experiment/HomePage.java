package apps.experiment

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class HomePage extends SurfaceView implements Runnable {


   private Thread th = null;
    SurfaceHolder sh1;
    Canvas canvas1 = null;
   private Thread animationFrame;

    private float y, x;
    private boolean runEnemy, runOnce = false;
    private boolean running;
    private long speedMS = 10;
    private Paint background;
    private Bitmap buttonStart;
    Rect play;


    public HomePage(Context context) {
        super(context);


        sh1 = getHolder();



        background = new Paint();
        background.setColor(Color.WHITE);
        buttonStart = BitmapFactory.decodeResource(getResources(), R.drawable.playbutton);

    }


    public void draw() {
        try {
            if (sh1.getSurface().isValid()) {

                canvas1 = sh1.lockCanvas();

                canvas1.drawColor(Color.rgb(255, 102, 102));
                if (!runOnce) {
                    buttonStart = Bitmap.createScaledBitmap(buttonStart, canvas1.getWidth() / 2, (int) ((double) (canvas1.getWidth() / 2) / 1.1946308724), false);
                    runOnce = true;
                }
                canvas1.drawBitmap(buttonStart, (canvas1.getWidth() / 2) - (buttonStart.getWidth() / 2), canvas1.getHeight() / 2, null);
                updatePlayRect();

            }
            sh1.unlockCanvasAndPost(canvas1);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    private void updatePlayRect() {
        play = new Rect((canvas1.getWidth() / 2) - (buttonStart.getWidth() / 2), (canvas1.getHeight() / 2), buttonStart.getWidth() + (canvas1.getWidth() / 2) - (buttonStart.getWidth() / 2), buttonStart.getHeight() + (canvas1.getHeight() / 2));

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if (play.contains((int) x, (int) y)) {
                 Intent i = new Intent(getContext(), GameActivity.class);
                   getContext().startActivity(i);

                }

                break;
            default:
                return false;
        }

        // invalidate();
        return true;

    }

    @Override
    public void run() {
        while (running) {
            draw();
        }
        try {
            //  System.out.println("slept");
            Thread.sleep(speedMS);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void resume() {
        running = true;
        th = new Thread(this);
        th.start();

    }

    public void pause() {
        running = false;
        try {
            th.join();

        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }
}
