package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import java.nio.file.Paths;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.AssetPaths;
import com.mygdx.game.novel1.typ.SaveDataCollection;
import com.mygdx.game.novel1.ui.layouts.SaveLoadUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;

public class Load implements Screen {

    private NovelOne game;
    private Stage stage;
    private Batch batch;
    private AssetManager manager;
    private InputMultiplexer multiplexer;
    private Screen inGameScreen;
    private SaveLoadUI uiHandler;
    private Sprite background;

    public Load (final NovelOne game ){

        this.game = game;
        this.stage = new Stage(this.game.viewport);
        this.batch = stage.getBatch();
        this.manager = new AssetManager();
        this.multiplexer = new InputMultiplexer();


        try {
            this.inGameScreen = game.getScreen();
            this.uiHandler = new SaveLoadUI(stage, game, this, listSaveFiles(), this.inGameScreen);
        }catch(ClassCastException e){
            Gdx.app.log("Load::Load", e.getMessage());
        }
    }

    /**
     * Gathers a list of files with save data
     * TODO - both Save and Load share this same method, will want to refactor this
     */
    private ArrayDeque<String> listSaveFiles() {

        File savePath = new File(Gdx.files.getLocalStoragePath() + AssetPaths.SAVE_PATH);
        File[] allFiles = savePath.listFiles();
        ArrayDeque<String> allFileNames = new ArrayDeque<>();

        for (int i = 0; i < allFiles.length; i ++) {
            allFileNames.add(allFiles[i].getName());
        }

        return allFileNames;
    }

    /**
     * Get saved data stored in specific file
     */
    public SaveDataCollection retrieveSavedData(String saveFileName) {
        Gdx.app.log("Load::retrieveSaveData", "checking file path and name: " + saveFileName);
        Json json = new Json();
        try{
            String jsonFileContents = new String(Files.readAllBytes(Paths.get(saveFileName)));
            return json.fromJson(SaveDataCollection.class, jsonFileContents);
        }catch(IOException e) {
            Gdx.app.log("Load::retrieveSavedData", e.getMessage());
        }
        return null;
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();
        this.background.draw(batch);
        batch.end();
        this.stage.draw();
    }

    @Override
    public void show() {
        this.uiHandler.generateUI();
        this.manager.load("img/test_titlepage.png", Texture.class);
        this.manager.finishLoading();
        this.background = new Sprite((Texture) this.manager.get("img/test_titlepage.png"));
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height){

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
