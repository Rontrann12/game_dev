package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.typ.SaveDataCollection;
import com.mygdx.game.novel1.ui.layouts.SaveLoadUI;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.zip.Deflater;

public class Save implements Screen {


    private NovelOne game;
    private Stage stage;
    private SaveLoadUI uiHandler;
    private Screen previousScreen;
    private Batch batch;
    private Sprite titlePage;
    private final AssetManager assets;
    private InputMultiplexer multiplexer;
    private Pixmap screenshot;
    private InGame inGameScreen;

    public Save(final NovelOne game, final Pixmap screenshot) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        this.assets = new AssetManager();
        this.multiplexer = new InputMultiplexer();
        this.screenshot = screenshot;

        try {
            this.inGameScreen = (InGame) game.getScreen();
            this.uiHandler = new SaveLoadUI(stage, game, this, listSaveFiles(), this.inGameScreen);

        } catch (ClassCastException e) {
            Gdx.app.log("Save::Save", e.getMessage());
        }

        try {
            FileHandle fileHandle = new FileHandle(StringUtilities.generateFileName(Gdx.files.getLocalStoragePath() + Paths.SCREENSHOTS_PATH,
                    "screenshot",
                    FileTypes.PNG));
            PixmapIO.writePNG(fileHandle,
                    screenshot,
                    Deflater.DEFAULT_COMPRESSION,
                    true);
            screenshot.dispose();
        } catch (Exception e) {
            Gdx.app.log("Save::Save", e.getMessage());
        }
    }

    /**
     * Gathers a list of files with save data
     */
    public ArrayDeque<String> listSaveFiles() {

        File savePath = new File(Gdx.files.getLocalStoragePath() + Paths.SAVE_PATH);
        File[] allFiles = savePath.listFiles();
        ArrayDeque<String> allFileNames = new ArrayDeque<>();

        for (int i = 0; i < allFiles.length; i ++) {
            allFileNames.push(allFiles[i].getName());
        }

        return allFileNames;
    }

    /**
     * Save the current state of the game
     */
    public void saveState(String fileName) {
        Gdx.app.log("Save::saveState", inGameScreen.getSaveData().toString());
        SaveDataCollection data = this.inGameScreen.getSaveData();


        Json json = new Json();
        String dataSerialized = json.toJson(data);
        Gdx.app.log("Save::saveState", "checking save data serialization: " + dataSerialized);
        Gdx.app.log("Save::saveState", "name of file: " + fileName);

        try{
            String filePath = StringUtilities.generateFileName(Gdx.files.getLocalStoragePath() + Paths.SAVE_PATH,
                    fileName,
                    FileTypes.SAVE);
            Gdx.app.log("Save::saveState", "path of file: " + filePath);


            File file = new File(filePath);

            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(dataSerialized);
            writer.close();
        }catch(IOException e) {
            Gdx.app.log("Save::saveState", e.getMessage());
        }

    }

    @Override
    public void render(float delta) {
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
        Gdx.app.log("Save::show", "checking number of actors in stage" + numActors.toString());
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
