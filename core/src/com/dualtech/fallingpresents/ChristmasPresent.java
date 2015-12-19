package com.dualtech.fallingpresents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by tunde_000 on 12/12/2015.
 */
public class ChristmasPresent {
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private Texture christmasPresent;
    private Rectangle bounds;

    public ChristmasPresent(int x, int y){
        christmasPresent = AssetLoader.christmasPresent;
        if(x < FallingPresentsGame.WIDTH / 2 - christmasPresent.getWidth())
            position = new Vector3(x, y, 0);
        else{
            position = new Vector3(x - christmasPresent.getWidth(), y, 0);
        }
        velocity = new Vector3(0, 0, 0);
        bounds = new Rectangle(x, y, christmasPresent.getWidth(), christmasPresent.getHeight());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getChristmasPresent() {
        return christmasPresent;
    }

    public void update(float dt){
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(0, velocity.y, 0);
        bounds.setPosition(position.x, position.y);
        if(position.y < 0)
            position.y = 0;
        velocity.scl(1 / dt);
    }

    public boolean isHitGround(){
        if(position.y == 0)
            return true;
        return false;
    }

    public void dispose(){
        christmasPresent.dispose();
    }
}
