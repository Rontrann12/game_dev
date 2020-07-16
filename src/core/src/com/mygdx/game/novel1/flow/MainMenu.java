package com.mygdx.game.novel1.flow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.entities.Button;

public class MainMenu implements Screen {

    final NovelOne game;
    private Stage stage;
    private Texture background;
    private Texture buttonSkin;
    private Batch batch;
    private Button startButton;

    //implement menu as stage instead of using sprite batch on it own
    public MainMenu (final NovelOne game){

        this.game = game;
        this.stage = new Stage(this.game.viewport);

    }

    @Override
    public void show(){

        this.background = new Texture(Paths.IMAGE_PATH+"menu/title_page.png");
        this.batch = this.stage.getBatch();
        this.buttonSkin = new Texture(Paths.IMAGE_PATH +"menu/button.png");
        Gdx.input.setInputProcessor(stage);
        this.startButton = new Button(buttonSkin, this.game,  "start button");
        this.startButton.spritePos(200,200);

        this.stage.addActor(this.startButton);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());


        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.draw();

//        camera.update();
//        game.batch.setProjectionMatrix(camera.combined);
//
//        game.batch.begin();
//        game.batch.draw(background,0,0,camera.viewportWidth, camera.viewportHeight);
//        game.font.draw(game.batch, "Hubby Simulator", 100,150);
//        game.font.draw(game.batch, "Click anywhere to begin!", 100, 100);
//
//        game.batch.end();
//
//        if(Gdx.input.isTouched()){
//            game.setScreen(new InGame(game));
//        }
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
        this.stage.dispose();
    }
}
