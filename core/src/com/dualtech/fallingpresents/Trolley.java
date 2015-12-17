package com.dualtech.fallingpresents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by tunde_000 on 12/12/2015.
 */
public class Trolley {
    private Vector3 position;
    private Vector3 velocity;
    private Texture trolley;
    private Rectangle bounds;

    public Trolley(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        trolley = AssetLoader.trolley;
        bounds = new Rectangle(x, y, trolley.getWidth(), trolley.getHeight() - 40);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTrolley() {
        return trolley;
    }

    public void update(float dt){
        if(position.x > 0 || position.x < ((FallingPresentsGame.WIDTH/2) - trolley.getWidth()))
            position.add(velocity.x, 0, 0);
        if(position.x < 0)
            position.x = 0;
        if(position.x > (FallingPresentsGame.WIDTH / 2))
            position.x = FallingPresentsGame.WIDTH / 2 - trolley.getWidth();
        bounds.setPosition(position.x, position.y);
        velocity.scl(1 / dt);
    }

    public void move(float x){
        if(!AssetLoader.getMotionControl())
            velocity.x = (x / 5);
        else
            velocity.x = (x * 3);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean isCollide(Rectangle present){
        return present.overlaps(bounds);
    }

    public void dispose(){
        trolley.dispose();
    }
}
