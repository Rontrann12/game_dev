package com.mygdx.game.novel1.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.LayoutOptions;
import com.mygdx.game.novel1.entities.UILayout;
import com.mygdx.game.novel1.entities.buttons.LoadButton;
import com.mygdx.game.novel1.entities.buttons.StartButton;

/**
 * Main menu screen. Allows the player to start a new game, load existing game, or change settings
 *
 */
public class MainMenu implements Screen {

    private final NovelOne game;
    private final Stage stage;
    private Texture background;
    private final Batch batch;
    private final Group buttonGroup;
    private AssetManager assets;

    private Sprite sprite;
    private UILayout uiHandler;

    public MainMenu(final NovelOne game) {

        this.game = game;
        this.stage = new Stage(this.game.viewport);
        this.buttonGroup = new Group();
        batch = this.stage.getBatch();
        //this.batch = new SpriteBatch();
        this.assets = new AssetManager();
        uiHandler = new UILayout();

    }

    /**
     * TODO - I may want to move some of the buttons to the UILayout class
     */

    @Override
    public void show() {

        uiHandler.generateUI(this.stage, this.game, LayoutOptions.MENU);
        assets.load("img/menu/title_page.png", Texture.class);
        assets.finishLoading();
        this.background = assets.get("img/menu/title_page.png");
        this.sprite = new Sprite(this.background);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();
        this.sprite.draw(batch);
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
