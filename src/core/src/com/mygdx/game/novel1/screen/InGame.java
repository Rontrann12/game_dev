package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.typ.AssetsDTO;
import com.mygdx.game.novel1.typ.SnapShot;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.*;
import com.mygdx.game.novel1.screen.etc.Character;

import java.util.*;

import static com.badlogic.gdx.Input.Keys.SPACE;


public class InGame implements Screen {

    private final NovelOne game;
    private final Batch batch;
    private final Stage stage;
    private final InGameUI uiHandler;
    private final String path = Paths.TEST_CONFIG_PATH;
    private LinkedHashMap<String, Character> charactersInScene;
    private ArrayDeque<String> script;
    private Group characterRenderGroup;
    private HashMap<String, Texture> backgrounds;
    private ScriptTracker tracker;

    public InGame(final NovelOne game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        charactersInScene = new LinkedHashMap<>();
        configure();
        this.uiHandler = new InGameUI(stage, game, stepForward());//processScriptLine());
        characterRenderGroup = new Group();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        try {
                            Gdx.app.log("InGame::InGame", "mouse down detected");
                            //uiHandler.nextLine(processScriptLine());
                            uiHandler.nextLine(stepForward());
                        } catch (EmptyStackException e) {
                            Gdx.app.log("InGame::InGame", e.getMessage());
                        }

                        return true;
                    }

                    @Override
                    public boolean keyDown(int keyCode) {
                        if (keyCode == SPACE || keyCode == Input.Buttons.LEFT) {
                            Gdx.app.log("InGame::InGame", "space bar button down detected");
                            try {
                                //uiHandler.nextLine(processScriptLine());
                                uiHandler.nextLine(stepForward());
                            } catch (EmptyStackException e) {
                                Gdx.app.log("InGame::InGame", e.getMessage());
                            }
                        }
                        return true;
                    }
                }
        );

        Gdx.input.setInputProcessor(multiplexer);
    }


    /**
     * progresses the game and the script forward
     *
     * @return
     */
    private String stepForward() {
        SnapShot snapshot = tracker.getNextLine();

        handleCastOnStage(snapshot.getCharacter(), snapshot.getAction());
        AudioHandler.handleMusicCommand(snapshot.getBGMCommand());
        AudioHandler.playSound(snapshot.getSound());
        return snapshot.getDialogue();
    }

    private void handleCastOnStage(String character, String action) {

        Character target = charactersInScene.get(character);
        Gdx.app.log("InGame::setAction", "received new action " + action + " for character " + character);
        try {

            if (!action.equals("Exit")) {
                Gdx.app.log("InGame::processAction", "adding " + character + " from screen");
                target.setExpression(action);
                characterRenderGroup.addActor(target);
            } else {
                Gdx.app.log("InGame::processAction", "removing " + character + " from screen");
                target.remove();
            }
        } catch (NullPointerException e) {
            Gdx.app.log("InGame::processAction", "no action found, skipping");
        }
    }

    private void configure() {
        ConfigReader.configNewScene(path);
        ArrayDeque<String> cast = ConfigReader.getCastList();
        ArrayDeque<String> backgroundsList = ConfigReader.getBackgrounds();
        ArrayDeque<String> bgmList = ConfigReader.getMusicList();
        ArrayDeque<String> sfxList = ConfigReader.getSoundList();
        AssetsDTO assets = AssetReader.getAllAssets(Paths.TEST_SCRIPT_PATH, cast, backgroundsList, bgmList, sfxList);

        //this.script = assets.getScript();
        tracker = new ScriptTracker(assets.getScript());
        this.backgrounds = assets.getBackgroundTextures();
        AudioHandler.addSound(assets.getSounds());
        AudioHandler.addMusic(assets.getTracks());
        HashMap<String, Texture> characters = assets.getCharacterTextures();


        for (Map.Entry<String, Texture> stringTextureEntry : characters.entrySet()) {
            Map.Entry<String, Texture> pair = stringTextureEntry;
            Gdx.app.log("InGame::configure", "adding character texture: " + pair.getKey());
            charactersInScene.put(pair.getKey(), new Character(pair.getKey(), pair.getValue()));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());

        // TODO - backgrounds need to be cued in by the script
        Sprite backgroundSprite = new Sprite(backgrounds.get("hallway"));
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        this.stage.draw();
    }

    @Override
    public void show() {
        this.stage.addActor(this.characterRenderGroup);
        this.uiHandler.generateUI();
        int numActors = this.stage.getActors().size;

        Gdx.app.log("InGame::show", "Getting actors from stage: " + numActors);
    }

    @Override
    public void dispose() {
        AudioHandler.clearMusic();
        AudioHandler.clearSounds();
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
