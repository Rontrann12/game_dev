package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.ui.layouts.SaveUI;

public class Save implements Screen {


    private NovelOne game;
    private Stage stage;
    private SaveUI uiHandler;
    private Screen previousScreen;
    private Batch batch;
    private Sprite titlePage;
    private final AssetManager assets;
    private InputMultiplexer multiplexer;

    public Save(final NovelOne game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.uiHandler = new SaveUI(stage, game, this);
        this.batch = stage.getBatch();
        this.assets = new AssetManager();
        multiplexer = new InputMultiplexer();
    }

    //TODO - might want to make this method as part of a base Screen
    public Screen getPreviousScreen() {
        return this.previousScreen;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());// Important line!! nothing will animate or update without it

        batch.begin();
        this.titlePage.draw(batch);
        batch.end();

        this.stage.draw();
    }

    @Override
    public void show() {

        this.uiHandler.generateUI();
        this.assets.load("img/test_titlepage.png", Texture.class);
        this.assets.finishLoading();
        this.titlePage = new Sprite((Texture) this.assets.get("img/test_titlepage.png"));

        Integer numActors = this.stage.getActors().size;
        Gdx.app.log("checking number of actors in stage (menu)", numActors.toString());
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
