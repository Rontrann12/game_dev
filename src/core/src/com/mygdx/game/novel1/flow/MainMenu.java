package com.mygdx.game.novel1.flow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.entities.buttons.LoadButton;
import com.mygdx.game.novel1.entities.buttons.StartButton;
/**
 * TODO - make the menu look nicer, add some music. after that basic main menu is done
 *
 * TODO - need to figure out how to implement text buttons and have them functionable
 *
 * TODO - do some reseach on atlas' and its concepts
 *
 * TODO - do some studying on images (SpriteBatch, TextureRegion, and Sprites)
 *
 * TODO - Background texture dimensions should be the same as the window aspect ratio (1.6)
 */

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
    private TextureRegion titleTexture;
    private Texture sheet;
    private AssetManager assets;

    public MainMenu (final NovelOne game){

        this.game = game;
        this.stage = new Stage(this.game.viewport);
        this.buttonGroup = new Group();
        batch = this.stage.getBatch();
        this.assets = new AssetManager();
    }

    @Override
    public void show(){

        loadAssets();

        TextureRegion idleTexture = new TextureRegion(sheet, 256, 0, 256, 64);
        TextureRegion hoverTexture = new TextureRegion(sheet, 0, 0, 256, 64);
        this.titleTexture = new TextureRegion(sheet, 512, 0, 256, 64);
        Gdx.input.setInputProcessor(stage);

        StartButton startButton = new StartButton(idleTexture, hoverTexture, game, "start button");
        LoadButton loadButton = new LoadButton(idleTexture, hoverTexture, game, "load button");

        positionEntities(startButton, loadButton);

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
        batch.draw(this.titleTexture, Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()*3)/4);
        batch.end();
        stage.draw();
    }

    @Override
    public void hide() {}

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void resize(int width, int height) {
        this.game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        this.sheet.dispose();
        this.background.dispose();
        this.stage.dispose();
    }

    private void loadAssets() {
        assets.load("img/menu/menu_better.png", Texture.class);
        assets.load("img/menu/title_page.png", Texture.class);
        assets.finishLoading();

        this.sheet = assets.get("img/menu/menu_better.png");
        this.background = assets.get("img/menu/title_page.png");
    }

    private void positionEntities(StartButton startButton, LoadButton loadButton) {
        startButton.spritePos(Gdx.graphics.getWidth()/2 - 128,200);
        loadButton.spritePos(Gdx.graphics.getWidth()/2 - 128, 100);
    }

}
