package com.mygdx.game.novel1.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.LayoutOptions;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.ui.UILayout;
import static com.badlogic.gdx.Input.Keys.*;

public class InGame implements Screen {

    private final NovelOne game;
    private Texture monikaLoad;
    private Sprite monikaSprite;
    private Batch batch;
    private Stage stage;
    private UILayout uiHandler;

    public InGame(final NovelOne game){
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        this.uiHandler = new UILayout();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());


        batch.begin();
        this.monikaSprite.draw(batch);
        batch.draw(monikaLoad,0,-300);
        batch.end();


        this.stage.draw();

    }

    @Override
    public void show() {
        this.uiHandler.generateUI(this.stage, this.game, LayoutOptions.IN_GAME);
        this.monikaLoad = new Texture(Paths.IMAGE_PATH+"Mon2.png");
        this.monikaSprite = new Sprite(this.monikaLoad);
        //Gdx.input.setInputProcessor(this.stage);

        Integer numActors = this.stage.getActors().size;

        Gdx.app.log("Getting actors from stage", numActors.toString());


        Gdx.input.setInputProcessor(uiHandler.getMultiplexer());



    }

    @Override
    public void dispose() {

    }


    @Override
    public void resize(int width, int height) {
        this.game.viewport.update(width, height, true);
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
