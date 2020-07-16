package com.mygdx.game.novel1.flow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;

public class InGame implements Screen {

    private final NovelOne game;
    private Texture monikaLoad;
    private Batch batch;
    private Stage stage;

    public InGame(final NovelOne game){
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(monikaLoad,0,-300, 500, 500);
        batch.draw(monikaLoad,0,-300);
        batch.end();
    }

    @Override
    public void show() {
        this.monikaLoad = new Texture(Paths.IMAGE_PATH+"Mon2.png");
        Gdx.input.setInputProcessor(this.stage);

    }

    @Override
    public void dispose() {

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
