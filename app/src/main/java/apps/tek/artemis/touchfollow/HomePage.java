package apps.tek.artemis.touchfollow;

import android.content.Context;
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


    Thread th = null;
    SurfaceHolder sh;
    Canvas canvas = null;
    Thread animationFrame;

    private float y, x;

    private boolean runEnemy, runOnce = false;

    private boolean running, anim;
    private int stage = 0;
    private long speedMS = 10;

    private Paint background;

    private boolean turningOff = true;
    private int mode = 1;
    private Bitmap buttonStart;
    Rect play;


    public HomePage(Context context) {
        super(context);


        background = new Paint();
        background.setColor(Color.WHITE);
        buttonStart = BitmapFactory.decodeResource(getResources(), R.drawable.playbutton);

    }


    public void draw() {
        if (sh.getSurface().isValid()) {


            //start
            canvas.drawColor(Color.rgb(255, 102, 102));
            if (!runOnce) {
                buttonStart = Bitmap.createScaledBitmap(buttonStart, canvas.getWidth() / 2, (int) ((double) (canvas.getWidth() / 2) / 1.1946308724), false);
                runOnce = true;
            }
            canvas.drawBitmap(buttonStart, (canvas.getWidth() / 2) - (buttonStart.getWidth() / 2), canvas.getHeight() / 2, null);
            updatePlayRect();

        }
        sh.unlockCanvasAndPost(canvas);


    }

    private void updatePlayRect() {
        play = new Rect((canvas.getWidth() / 2) - (buttonStart.getWidth() / 2), (canvas.getHeight() / 2), buttonStart.getWidth() + (canvas.getWidth() / 2) - (buttonStart.getWidth() / 2), buttonStart.getHeight() + (canvas.getHeight() / 2));

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
                    System.out.println("YES");
                    mode = 0;
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
            if (mode == 0) {
                if (turningOff) {

                }


            }
            try {
                //  System.out.println("slept");
                Thread.sleep(speedMS);

            } catch (Exception e) {
                e.printStackTrace();
            }

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
            animationFrame.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }
}
