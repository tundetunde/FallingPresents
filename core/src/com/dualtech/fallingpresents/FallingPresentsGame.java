package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;

public class FallingPresentsGame extends Game {
	public static final int HEIGHT = 1440;
	public static final int WIDTH = 2400;
	private GameStateManager gsm;
	private SpriteBatch batch;

	public FallingPresentsGame() {
		super();
	}

	@Override
	public void create () {
		gsm = new GameStateManager();
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		new Thread(new Runnable() {
			@Override
			public void run() {
				AssetLoader.load();
			}
		}).start();
		gsm.push(new SplashScreen(gsm));
		setScreen(new SplashScreen1(gsm));
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						gsm.push(new Menu(gsm));
					}
				}, 3);
			}
		}).start();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
}
