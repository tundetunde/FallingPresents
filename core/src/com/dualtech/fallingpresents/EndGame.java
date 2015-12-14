package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class EndGame extends State {
    private Texture background;
    private ChristmasPresent christmasPresent;
    private Trolley trolley;
    private BitmapFont font;
    private BitmapFont shadow;
    TextButton.TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    private TextButton playButton;
    private TextButton leaderBoardButton;
    int cameraWidth = Game.WIDTH / 2;
    int cameraHeight = Game.HEIGHT / 2;
    BitmapFont fontButton;
    Stage stage;

    protected EndGame(GameStateManager gcm, Vector3 presentPosition, Vector3 trolleyPosition) {
        super(gcm);
        christmasPresent = new ChristmasPresent((int)presentPosition.x, (int)presentPosition.y);
        trolley = new Trolley((int)trolleyPosition.x, (int)trolleyPosition.y);
        background = new Texture("bgting1.png");
        camera.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.45f, .45f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.45f, .45f);
        skin = new Skin();
        stage = new Stage();
        fontButton = new BitmapFont();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = fontButton;
        textButtonStyle.up = skin.getDrawable("ZSjxE");
        textButtonStyle.down = skin.getDrawable("ZSjxE");
        textButtonStyle.checked = skin.getDrawable("ZSjxE");
        initializeButtons();
        stage.addActor(playButton);
        stage.addActor(leaderBoardButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void initializeButtons(){
        playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setPosition(cameraWidth / 6, 80);
        playButton.setSize(100, 30);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Button Clicked");
                gcm.set(new PlayGame(gcm));
            }
        });

        leaderBoardButton = new TextButton("Leaderboard", textButtonStyle);
        leaderBoardButton.setPosition((cameraWidth / 6) * 3 + 20, 80);
        leaderBoardButton.setSize(100, 30);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("LeaderBoard: Button Clicked");
                gcm.set(new PlayGame(gcm));
            }
        });
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(christmasPresent.getChristmasPresent(), christmasPresent.getPosition().x, christmasPresent.getPosition().y);
        sb.draw(trolley.getTrolley(), trolley.getPosition().x, trolley.getPosition().y);
        String over = "GAME OVER";
        shadow.draw(sb, over, (Game.WIDTH / 4) - (over.length() * 10), (Game.HEIGHT / 8) * 3);
        font.draw(sb, over, (Game.WIDTH / 4) - (over.length() * 10), (Game.HEIGHT / 8) * 3);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        shadow.dispose();
        stage.dispose();
    }
}
