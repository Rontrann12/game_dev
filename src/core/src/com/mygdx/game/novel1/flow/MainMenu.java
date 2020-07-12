package com.mygdx.game.novel1.flow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Constants;
import com.mygdx.game.novel1.entities.Button;

public class MainMenu implements Screen {

    final NovelOne game;
    private OrthographicCamera camera;
    private Texture background;
    private Button start;
    private Button load;
    private Button options;


    //implement menu as stage instead of using sprite batch on it own
    public MainMenu (final NovelOne game){
        background = new Texture(Constants.IMAGE_PATH+"menu/title_page.png");

        start = new Button(Constants.IMAGE_PATH+"menu/button.png", "Start");
        load = new Button(Constants.IMAGE_PATH+"menu/button.png", "Load");
        options = new Button(Constants.IMAGE_PATH+"menu/button.png", "Options");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background,0,0,camera.viewportWidth, camera.viewportHeight);
        game.font.draw(game.batch, "Hubby Simulator", 100,150);
        game.font.draw(game.batch, "Click anywhere to begin!", 100, 100);

        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new InGame(game));
        }
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

    }
    @Override
    public void show() {

    }

    @Override
    public void dispose() {

    }
}
