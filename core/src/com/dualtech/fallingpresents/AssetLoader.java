package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class AssetLoader {
    public static Preferences prefs;
    //static ParticleEffect snowEffect;
    public static Sound coin;
    public static Texture background;
    public static Texture christmasPresent;
    public static Texture trolley;
    public static TextureAtlas buttonAtlas;
    public static Skin skin;
    public static Drawable textSkin;
    public static TextButton.TextButtonStyle normalTextButtonStyle;

    public static void load(){
        background = new Texture("bg3.jpg");
        prefs = Gdx.app.getPreferences("Falling Presents");
        christmasPresent = new Texture("present5.png");
        trolley = new Texture("trolley1.png");
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        skin = new Skin();
        skin.addRegions(buttonAtlas);
        textSkin = skin.getDrawable("ZSjxE");
        normalTextButtonStyle = new TextButton.TextButtonStyle();
        normalTextButtonStyle.font = new BitmapFont();;
        normalTextButtonStyle.up = AssetLoader.textSkin;
        normalTextButtonStyle.down = AssetLoader.textSkin;
        normalTextButtonStyle.checked = AssetLoader.textSkin;

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putLong("highScore", 0);
        }
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        /*snowEffect = new ParticleEffect();
        snowEffect.load(Gdx.files.internal("snow_effect.txt"), Gdx.files.internal("particle.png"));
        snowEffect.getEmitters().first().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()/2);*/
        //snowEffect.start();

    }

    public static void disposeChristmasPresent(){
        christmasPresent.dispose();
        //christmasPresent2.dispose();
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
}
