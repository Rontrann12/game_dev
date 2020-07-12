package com.mygdx.game.novel1.flow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Constants;

public class InGame implements Screen {

    final NovelOne game;
    private Texture monikaLoad;

    public InGame(final NovelOne game){
        this.game = game;
        monikaLoad = new Texture(Constants.IMAGE_PATH+"Mon2.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.batch.draw(monikaLoad,0,-300, 500, 500);
        game.batch.draw(monikaLoad,0,-300);
        game.batch.end();
    }

    @Override
    public void show() {

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
