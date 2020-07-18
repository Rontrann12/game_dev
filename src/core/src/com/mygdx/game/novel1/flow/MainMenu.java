package com.mygdx.game.novel1.flow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.entities.buttons.LoadButton;
import com.mygdx.game.novel1.entities.buttons.StartButton;
import com.mygdx.game.novel1.entities.buttons.TestButton;

/**
 * TODO - make the menu look nicer, add some music. after that basic main menu is done
 */

public class MainMenu implements Screen {

    final NovelOne game;
    private Stage stage;
    private Texture background;
    private Texture buttonTexture;
    private Batch batch;
    private StartButton startButton;
    private LoadButton loadButton;
    private Group buttonGroup;
    private TestButton settingsButton;

    public MainMenu (final NovelOne game){

        this.game = game;
        this.stage = new Stage(this.game.viewport);
        this.buttonGroup = new Group();
        batch = this.stage.getBatch();
    }

    @Override
    public void show(){
        AssetManager assets = new AssetManager();
        assets.load("img/menu/button.png", Texture.class);
        assets.load("img/menu/title_page.png", Texture.class);
        assets.finishLoading();
        background = assets.get("img/menu/title_page.png");
        buttonTexture = assets.get("img/menu/button.png");

//        background = new Texture(Paths.IMAGE_PATH+"menu/title_page.png");
//        buttonTexture = new Texture(Paths.IMAGE_PATH +"menu/button.png");
        Gdx.input.setInputProcessor(stage);
        startButton = new StartButton(buttonTexture, game,  "start button");
        loadButton = new LoadButton(buttonTexture, game, "load button");

//        settingsButton = new TestButton("Settings");

        startButton.spritePos(Gdx.graphics.getWidth()/2- buttonTexture.getWidth()/2,200);
        loadButton.spritePos(Gdx.graphics.getWidth()/2 - buttonTexture.getWidth()/2, 100);
//        settingsButton.setCoordinates(Gdx.graphics.getWidth()/2 - buttonTexture.getWidth()/2, 0);

//        buttonGroup.addActor(settingsButton.getTextButton());
        buttonGroup.addActor(startButton);
        buttonGroup.addActor(loadButton);

        this.stage.addActor(this.buttonGroup);
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
