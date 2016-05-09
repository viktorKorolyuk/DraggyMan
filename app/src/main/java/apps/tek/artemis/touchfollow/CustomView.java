package apps.tek.artemis.touchfollow;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class CustomView extends SurfaceView implements Runnable {


    private Thread th = null;
    private SurfaceHolder sh;
    private Canvas canvas = null;
    private Thread animationFrame;
    private Thread addEnemy;
    boolean enemyAdd = false;
    private float y, x;
    private Player pl;
    private Background bg;
    private boolean runEnemy, runOnce = false;
    private ArrayList<Spikes> spikes;
    private int enemy = 1;
    private boolean running, anim;
    private int stage = 0;
    private long speedMS = 10;
    private long frames;
    private long WIDTH, HEIGHT;
    private Paint background;
    Rect[] spikeRect;
    private int score;
    private boolean turningOff = true;
    private int mode = 1;





    public CustomView(Context context) {
        super(context);

        sh = getHolder();
        pl = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.bobbyv1po1));

        initialiseEnemy(enemy);

        background = new Paint();
        background.setColor(Color.WHITE);


    }

    private void initialiseEnemy(int enemyNum) {
        if (spikes == null) {
            spikes = new ArrayList<>(enemyNum);
        }
        if (spikes != null && spikes.size() <= 20) {
            spikes.add(new Spikes(BitmapFactory.decodeResource(getResources(), R.drawable.spikesv1)));
        }
    }



    public void draw() {
        if (sh.getSurface().isValid()) {

            canvas = sh.lockCanvas();
            WIDTH = canvas.getWidth();
            HEIGHT = canvas.getHeight();

            if (mode == 0) {

                canvas.drawPaint(background); //code to clean page
                bg.update();
                bg.draw(canvas);

                pl.draw(canvas);

                for (int i = 0; i < spikes.size(); i++) {
                    if (spikes.get(i) != null) {
                        spikes.get(i).update();
                        spikes.get(i).draw(canvas);
                    }
                }


            }
            sh.unlockCanvasAndPost(canvas);

        }

    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                anim = true;
                pl.update(x, y + 15);
                //    System.out.println("" + x + " " + y);
                break;
            case MotionEvent.ACTION_MOVE:
                pl.update(x, y + 15);
                //     System.out.println("" + x + " " + y);
                break;
            case MotionEvent.ACTION_UP:
                anim = false;
                pl.update(x, y + 15);
                //     System.out.println("" + x + " " + y);
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
                    checkCollision();
                }
                frames++;
                //   System.out.println(frames);

            }
            try {
                //  System.out.println("slept");
                Thread.sleep(speedMS);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void checkCollision() {
        Rect player = pl.returnBounds();

        //initialise the rectangles


        spikeRect = new Rect[spikes.size()];
        if (spikes != null) {
            for (int s = 0; s < spikes.size(); s++) {
                if (spikes.get(s) != null) {
                    spikeRect[s] = spikes.get(s).returnBounds();
                }
            }


            if (frames > 10) {

                for (int i = 0; i < spikeRect.length; i++) {

                    if (spikeRect[i] != null && player != null) {
                        System.out.println("Checking Rectangle #" + i);
                        if (Rect.intersects(player, spikeRect[i])) {
                            stopGame();

                            frames = 0;

                        } else {
                            addEnemyThread();

                        }
                    }
                }

            }
        }

    }

    private void addEnemyThread() {

        /**
         * Im fairly sure there is a better way of doing this but who gives a shit.
         * I mean its my program and I can decide how to write it.
         * Also... WHY THE FUCK ARN'T THERE TUTORIALS ON THIS SHIT
         * I mean, really. I have looked all over on the internet looking for game programing,
         * then finally i give up and create my own method.
         *      -Viktor Korolyuk Age 15 (Thu,11:01pm,April,7,2016)
         */

        if (!enemyAdd) {
            runEnemy = true;
            addEnemy = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (runEnemy) {
                        try {
                            Thread.sleep((long) 6000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        enemy++;
                    //qu    initialiseEnemy(enemy);

                    }
                }

            });
            addEnemy.start();
            enemyAdd = true;
        }

    }



    private void stopGame() {
        pl = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.bobbyv1po1));
        spikes = null;
        enemy = 0;
        initialiseEnemy(enemy);
        runEnemy = false;
        enemyAdd = false;
        try {
            addEnemy.interrupt(); //thanks to my dad for suggesting this code, it really helped :)
            addEnemy.join();


        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
        turningOff = true;

    }


    public void resume() {
        running = true;
        th = new Thread(this);
        th.start();
        animationFrame = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    if (anim) {
                        if (stage == 0) {
                            pl.changeState(BitmapFactory.decodeResource(getResources(), R.drawable.bobbyv1po1));
                            stage = 1;
                        } else if (stage == 1) {
                            pl.changeState(BitmapFactory.decodeResource(getResources(), R.drawable.bobbyv1po2));
                            draw();
                            stage = 0;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (!anim) {
                        pl.changeState(BitmapFactory.decodeResource(getResources(), R.drawable.bobbyv1po1));
                    }
                }
            }
        });
        animationFrame.start();
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
