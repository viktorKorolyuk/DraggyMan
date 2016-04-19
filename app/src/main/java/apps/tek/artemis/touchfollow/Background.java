package apps.tek.artemis.touchfollow;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Background {

    private Bitmap bg;
    private int x,y,dx,dy = 5;
    private boolean runOnce;

    public Background(Bitmap res){
        this.bg = res;

    }
    public void update(){
      //  y+=dy;

    }
    public void draw(Canvas canvas){
        if (!runOnce) {
            bg = Bitmap.createScaledBitmap(bg, canvas.getWidth(), canvas.getHeight(), false);
            runOnce = true;
        }
        canvas.drawBitmap(bg,x,y,null);
       // if(y < 5){
       //     y = 0;
      //      canvas.drawBitmap(bg,x,y+canvas.getHeight(),null);
       // }

    }
}
