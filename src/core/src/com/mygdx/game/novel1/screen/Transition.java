package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.novel1.constants.Paths;

public class Transition implements Screen {

    private Screen nextScreen;
    private Screen currentScreen;
    private NovelOne game;
    private float alpha;
    private boolean fadeOut;
    private Stage stage;
    private Batch batch;
    private ShapeRenderer fadeRenderer;

    public Transition(NovelOne game) {
//        this.nextScreen = next;
//        this.currentScreen = current;
        this.nextScreen = new InGame(game, Paths.CONFIGS_PATH + "script3");
        this.game = game;
        alpha = 1;
        fadeOut = true;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        game.setScreen(this.nextScreen);
        fadeRenderer = new ShapeRenderer(8);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.app.log("Transistion::render", "transitioning screens");
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setContinuousRendering(true);

        game.setScreen(nextScreen);


        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        fadeRenderer.setColor(0, 0, 0, alpha);
        fadeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        fadeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fadeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if (alpha >= 1) {
            fadeOut = false;
        } else if (alpha <= 0 && fadeOut == false) {
            game.setScreen(nextScreen);
        }
        alpha += fadeOut == true ? 0.01 : -0.01;
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
