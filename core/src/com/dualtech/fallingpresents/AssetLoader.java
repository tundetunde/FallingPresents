package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class AssetLoader {
    public static Preferences prefs;
    public static Sound coin;
    public static BitmapFont font, scoreFont, instructFont;
    public static Texture background,trolley, splash;
    public static Texture p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
    public static Skin menuSkin;
    public static ImageButton.ImageButtonStyle playStyle, rateStyle, scoreStyle, muteStyle, soundStyle, shareStyle;

    public static void load(){
        prefs = Gdx.app.getPreferences("Falling Presents");
        font = new BitmapFont(Gdx.files.internal("menu_font.fnt"));
        scoreFont = new BitmapFont(Gdx.files.internal("hscore_font.fnt"));
        instructFont = new BitmapFont(Gdx.files.internal("instruct_font.fnt"));
        background = new Texture("valbg.png");
        splash = new Texture("splash.png");
        trolley = new Texture("trolley.png");

        p1 = new Texture("item1.png");
        p2 = new Texture("item2.png");
        p3 = new Texture("item3.png");
        p4 = new Texture("item4.png");
        p5 = new Texture("item5.png");
        p6 = new Texture("item6.png");
        p7 = new Texture("item7.png");
        p8 = new Texture("item8.png");
        p9 = new Texture("item9.png");
        p10 = new Texture("item10.png");

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
        shareStyle = new ImageButton.ImageButtonStyle();
        shareStyle.imageUp = menuSkin.getDrawable("share");
        shareStyle.imageDown = menuSkin.getDrawable("shareR");

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
        return prefs.getBoolean("soundControl",true);
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setMotionControl(boolean val) {
        prefs.putBoolean("motionControl", val);
        prefs.flush();
    }

    public static void dispose(){
        background.dispose();
        trolley.dispose();
        font.dispose();
        scoreFont.dispose();
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
