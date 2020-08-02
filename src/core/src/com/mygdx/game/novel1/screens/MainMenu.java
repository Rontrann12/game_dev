package com.mygdx.game.novel1.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.LayoutOptions;
import com.mygdx.game.novel1.ui.UILayout;


/**
 * Main menu screen. Allows the player to start a new game, load existing game, or change settings
 *
 */
public class MainMenu implements Screen {

    private final NovelOne game;
    private final Stage stage;
    private Texture background;
    private final Batch batch;
    private final AssetManager assets;

    private Sprite titleSprite;
    private Sprite backgroundSprite;
    private UILayout uiHandler;

    public MainMenu(final NovelOne game) {

        this.game = game;
        this.stage = new Stage(this.game.viewport);
        batch = this.stage.getBatch();
        this.assets = new AssetManager();
        uiHandler = new UILayout();

    }

    /**
     * TODO - I may want to move some of the buttons to the UILayout class
     */

    @Override
    public void show() {

        uiHandler.generateUI(this.stage, this.game, LayoutOptions.MENU);
        assets.load("img/test_titlepage.png", Texture.class);
        assets.load("img/title.png", Texture.class);
        assets.finishLoading();
        Texture title = assets.get("img/title.png");
        this.background = assets.get("img/test_titlepage.png");
        this.backgroundSprite = new Sprite(this.background);
        this.titleSprite = new Sprite(title);
        this.titleSprite.setX(500);
        this.titleSprite.setY(700);
        Gdx.input.setInputProcessor(stage);

        Integer numActors = this.stage.getActors().size;
        Gdx.app.log("checking number of actors in stage (menu)", numActors.toString());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();
        this.backgroundSprite.draw(batch);
        this.titleSprite.draw(batch);
        batch.end();

        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        this.game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.stage.dispose();
    }


}
