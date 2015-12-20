package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    public static BitmapFont font, shadow;
    public static Texture background,trolley, splash, christmasPresent;
    public static Texture p1, p2, p3, p4, p5;
    public static Skin menuSkin;
    public static Label scoreBoard;
    public static ImageButton.ImageButtonStyle playStyle, rateStyle, scoreStyle, muteStyle, soundStyle;

    public static void load(){
        prefs = Gdx.app.getPreferences("Falling Presents");

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        background = new Texture("bg3.jpg");
        splash = new Texture("splash.png");
        trolley = new Texture("trolley.png");

        p1 = new Texture("present1.png");
        p2 = new Texture("present2.png");
        p3 = new Texture("present3.png");
        p4 = new Texture("present4.png");
        p5 = new Texture("present5.png");

        christmasPresent = p5;

        menuSkin= new Skin();
        menuSkin.addRegions(new TextureAtlas(Gdx.files.internal("menuButtons.pack")));
        playStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = menuSkin.getDrawable("play");
        playStyle.imageDown = menuSkin.getDrawable("playR");
        rateStyle = new ImageButton.ImageButtonStyle();
        rateStyle.imageUp = menuSkin.getDrawable("rate");
        rateStyle.imageDown = menuSkin.getDrawable("rateR");
        scoreStyle = new ImageButton.ImageButtonStyle();
        scoreStyle.imageUp = menuSkin.getDrawable("score");
        scoreStyle.imageDown = menuSkin.getDrawable("scoreR");
        muteStyle = new ImageButton.ImageButtonStyle();
        muteStyle.imageUp = menuSkin.getDrawable("mute");
        soundStyle = new ImageButton.ImageButtonStyle();
        soundStyle.imageUp = menuSkin.getDrawable("sound");

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putLong("highScore", 0);
        }

        if (!prefs.contains("firstTime")) {
            prefs.putBoolean("firstTime", true);
        }

        if (!prefs.contains("motionControl")) {
            prefs.putBoolean("motionControl", true);
        }

        if (!prefs.contains("soundControl")) {
            prefs.putBoolean("soundControl", true);
        }
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
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

    public static void setFirstTime(boolean val) {
        prefs.putBoolean("firstTime", val);
        prefs.flush();
    }

    public static boolean isFirstTime() {
        return prefs.getBoolean("firstTime");
    }

    public static void toggleSound(boolean val) {
        prefs.putBoolean("soundControl", val);
        prefs.flush();
    }

    public static boolean isSoundOn() {
        return prefs.getBoolean("soundControl");
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setMotionControl(boolean val) {
        prefs.putBoolean("motionControl", val);
        prefs.flush();
    }

    public static void dispose(){
        background.dispose();
        trolley.dispose();
        christmasPresent.dispose();
        font.dispose();
        shadow.dispose();
        p1.dispose();
        p2.dispose();
        p3.dispose();
        p4.dispose();
        p5.dispose();
        splash.dispose();
        menuSkin.dispose();
        coin.dispose();
    }
}
