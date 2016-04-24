package apps.tek.artemis.touchfollow;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Background {

    private Bitmap bg;
    private CustomView vv;
    private int x,y,y2,y3,dx,dy = 5;
    private boolean runOnce;

    public Background(Bitmap res, CustomView v){
        this.bg = res;
        this.vv = v;

    }
    public void update(){
        y+=dy;

       // y2+= dy;
       // y3+= dy;

    }
    public void draw(Canvas canvas){
        if (!runOnce) {
            bg = Bitmap.createScaledBitmap(bg, canvas.getWidth(), canvas.getHeight(), false);
            runOnce = true;
        }
        /**
         *
         * I am writing NEW code to see if it works better. Even though I could probably solve the problem... it would take a while, and hopefully android has a system set that will allow me to do something different.
        canvas.drawBitmap(bg,x,y,null);
        if(y >= canvas.getHeight()){
            this.y = 0;
        }

        canvas.drawBitmap(bg,x,y2 - canvas.getHeight(),null);
        if(y2 >= canvas.getHeight() * 2){
            this.y2 = 0;
        }
        canvas.drawBitmap(bg,x,y3 - (canvas.getHeight() * 2),null);
        if(y3 >= (canvas.getHeight() * 3)){
            this.y3 = 0;
        }
//[1,2],[1,2],[1,2,3] (repeat)

         */

    }
}
