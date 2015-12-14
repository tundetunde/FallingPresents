package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class AssetLoader {
    public static Preferences prefs;
    //static ParticleEffect snowEffect;
    public static Sound coin;
    public static void load(){
        prefs = Gdx.app.getPreferences("Falling Presents");

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
