package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tunde_000 on 23/12/2015.
 */
public class Leaderboard extends State {
    private Texture background;
    Stage stage;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    private BitmapFont fontTitle, shadow;
    ArrayList<HashMap<String, Integer>> leaderboardList;

    protected Leaderboard(GameStateManager gcm) {
        super(gcm);
        //List<HashMap<String, Integer>> hi = new List(AssetLoader.listStyle);
        List.ListStyle y = AssetLoader.listStyle;
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        List<String> x = new List<String>(uiSkin);
        x.setPosition(200, 200);
        leaderboardList = FallingPresentsGame.activityMethods.postLeaderboard();
        Array hello = new Array();
        hello.addAll(leaderboardList.toArray());
        x.setItems(hello);
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        background = AssetLoader.background;
        fontTitle = AssetLoader.font;
        fontTitle.getData().setScale(1.2f, 1.2f);
        shadow = AssetLoader.shadow;
        shadow.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        //initializeButtons();
        stage.addActor(x);
        Gdx.input.setInputProcessor(stage);
    }

    public void initializeButtons(){

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        String title = "LEADERBOARD";
        shadow.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 24), cameraHeight - 30);
        fontTitle.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 24), cameraHeight - 30);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
