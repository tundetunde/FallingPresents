package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class AssetLoader {
    public static Preferences prefs;
    public static Sound coin;
    public static Texture background, christmasPresent,trolley;
    public static TextureAtlas buttonAtlas, arrowButtonAtlas;
    public static Skin skin,leftSkin, rightSkin, menuSkin;
    public static Label scoreBoard;
    public static ImageButton.ImageButtonStyle leftarrowButtonStyle, rightArrowButtonStyle, playStyle, rateStyle, scoreStyle;

    public static void load(){
        background = new Texture("bg3.jpg");
        prefs = Gdx.app.getPreferences("Falling Presents");
        christmasPresent = new Texture("present5.png");
        trolley = new Texture("trolley1.png");
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        arrowButtonAtlas = new TextureAtlas(Gdx.files.internal("arrow/arrowButtons.pack"));
        skin = new Skin();
        leftSkin = new Skin();
        rightSkin = new Skin();
        skin.addRegions(buttonAtlas);
        leftSkin.addRegions(arrowButtonAtlas);
        rightSkin.addRegions(arrowButtonAtlas);

        menuSkin= new Skin();
        menuSkin.addRegions(new TextureAtlas(Gdx.files.internal("menuButtons.pack")));
        playStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = menuSkin.getDrawable("play");
        rateStyle = new ImageButton.ImageButtonStyle();
        rateStyle.imageUp = menuSkin.getDrawable("rate");
        scoreStyle = new ImageButton.ImageButtonStyle();
        scoreStyle.imageUp = menuSkin.getDrawable("score");

        leftarrowButtonStyle = new ImageButton.ImageButtonStyle();  //Instaciate
        leftarrowButtonStyle.up = leftSkin.getDrawable("left arrow");  //Set image for not pressed button
        leftarrowButtonStyle.down = leftSkin.getDrawable("left button clicked");  //Set image for pressed
        leftarrowButtonStyle.over = leftSkin.getDrawable("left arrow");  //set image for mouse over
        leftarrowButtonStyle.pressedOffsetX = 1;
        leftarrowButtonStyle.pressedOffsetY = -1;

        rightArrowButtonStyle = new ImageButton.ImageButtonStyle();  //Instaciate
        rightArrowButtonStyle.up = leftSkin.getDrawable("right arrow");  //Set image for not pressed button
        rightArrowButtonStyle.down = leftSkin.getDrawable("right button clicked");  //Set image for pressed
        rightArrowButtonStyle.over = leftSkin.getDrawable("right arrow");  //set image for mouse over
        rightArrowButtonStyle.pressedOffsetX = 1;
        rightArrowButtonStyle.pressedOffsetY = -1;

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putLong("highScore", 0);
        }

        if (!prefs.contains("motionControl")) {
            prefs.putBoolean("motionControl", true);
        }
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
    }

    public static void disposeChristmasPresent(){
        christmasPresent.dispose();
    }

    public Texture getBackground(){
        return background;
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(long val) {
        prefs.putLong("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static long getHighScore() {
        return prefs.getLong("highScore");
    }

    public static boolean getMotionControl() {
        return prefs.getBoolean("motionControl");
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setMotionControl(boolean val) {
        prefs.putBoolean("motionControl", val);
        prefs.flush();
    }
}
