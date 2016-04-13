package apps.tek.artemis.touchfollow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by viktor on 16-03-29.
 */
public class Player {
    private Bitmap player;
    private float x, y, playerWidth, playerHeight;
    private boolean alive,ran = true;


    public Player(Bitmap res) {
        this.player = res;

        alive = true;
    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;

    }

    public void draw(Canvas canvas) {
        if (alive) {
            player = Bitmap.createScaledBitmap(player, canvas.getWidth() / 4, (int) ((double) (canvas.getWidth() / 4) / 0.86), false);
            getScale();

            canvas.drawBitmap(player, x - (playerWidth / 2), y, null);
        }


    }

    private void getScale() {
        if(ran){
            playerWidth = player.getWidth();
            playerHeight = player.getHeight();
            ran = false;
        }
    }

    public void changeState(Bitmap b) {

        this.player = b;
    }

    public Rect returnBounds() {

        return new Rect((int) x, (int) y, (int) x + ((int) (playerWidth / 2)), (int) y + ((int) playerHeight));
    }


}
