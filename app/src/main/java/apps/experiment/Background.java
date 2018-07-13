package apps.experiment

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

        if(y >= vv.getHeight()){
            y = 0;
        }

    }
    public void draw(Canvas canvas){
        if (!runOnce) {
            bg = Bitmap.createScaledBitmap(bg, canvas.getWidth(), canvas.getHeight(), false);
            runOnce = true;
        }


        canvas.drawBitmap(bg,x,y,null);

        if(y >= 0){
            canvas.drawBitmap(bg,x,y - vv.getHeight(),null); //fill in blank space
        }


    }
}
