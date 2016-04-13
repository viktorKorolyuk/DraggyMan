package apps.tek.artemis.touchfollow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;


public class Spikes {
    private Bitmap spike;

    private float spikeWidth,spikeHeight,x = 100,xp = 1,y = 500,yp = 1,canvasH,canvasW;
    Random r = new Random();
    private boolean ran = false;


    public Spikes(Bitmap res){
        this.spike = res;


    }
    public void update(){

        /**
         * Deal with the spike location
         */

        if(x >= canvasW - spikeWidth){
        int temp = (r.nextInt(5 + 1) + 1);
              xp = temp - (temp * 2);
        }if(x <= 0){
            xp = r.nextInt(5 - 1) + 1;
        }

        if(y >= canvasH - spikeHeight){
            int temp = (r.nextInt(5 + 1) + 1); //negative the integer
            yp = temp - (temp * 2);
        }if(y <= 0){
            yp = r.nextInt(5 - 1) + 1;
        }

        y += yp;
        x += xp;



    }

    public void draw(Canvas canvas){
        canvasH = canvas.getHeight();
        canvasW = canvas.getWidth();
        firstRun();

        canvas.drawBitmap(spike,x,y,null);
       // System.out.println("X: " + x + " Y: " + y);


    }

    private void firstRun() {
        if(!ran){
        spike = Bitmap.createScaledBitmap(spike, (int) canvasW / 5, (int) canvasW / 5, false);
        spikeWidth = spike.getWidth();
        spikeHeight = spike.getHeight();
            ran = true;

        }
    }
    public Rect returnBounds(){

        return new Rect((int) x,(int) y, (int) x+((int) spikeWidth),(int) y + ((int) spikeHeight));
    }

}
