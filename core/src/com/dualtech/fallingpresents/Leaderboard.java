package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by tunde_000 on 23/12/2015.
 */
public class Leaderboard extends State {
    private Texture background;
    Stage stage;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    private BitmapFont fontTitle, shadow;
    static ArrayList<HashMap<String, Integer>> leaderboardList;
    Label label;

    protected Leaderboard(GameStateManager gcm) {
        super(gcm);
        //List<HashMap<String, Integer>> hi = new List(AssetLoader.listStyle);
        //leaderboardList = FallingPresentsGame.activityMethods.postLeaderboard();
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        background = AssetLoader.background;
        fontTitle = AssetLoader.font;
        fontTitle.getData().setScale(1.2f, 1.2f);
        shadow = AssetLoader.shadow;
        shadow.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        //initializeButtons();
        label = new Label("NAME", AssetLoader.labelStyle);
        Label l2 = new Label("SCORE", AssetLoader.labelStyle);
        final Table scrollTable = new Table();
        scrollTable.add(label);
        scrollTable.add(l2);
        scrollTable.row();
        for(int i = 0; i < leaderboardList.size(); i++){
            Set set = leaderboardList.get(i).entrySet();
            Iterator iterator = set.iterator();
            Map.Entry mentry = (Map.Entry)iterator.next();
            Label l = new Label(mentry.getKey().toString(), AssetLoader.labelStyle);
            Label m = new Label(mentry.getValue().toString(), AssetLoader.labelStyle);
            scrollTable.add(l);
            scrollTable.add(m);
            scrollTable.row();
        }

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();
        //table.setPosition(cameraWidth / 2, cameraHeight - 20);
        this.stage.addActor(table);
        //stage.addActor(label);
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
