package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.BufferUtils;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.typ.AssetsDTO;
import com.mygdx.game.novel1.typ.SnapShot;
import com.mygdx.game.novel1.typ.SpeakerMap;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.*;
import com.mygdx.game.novel1.typ.Character;

import java.nio.IntBuffer;
import java.util.*;

import static com.badlogic.gdx.Input.Keys.SPACE;


/**
 * TODO - create new class and separate character sprite handling code
 * TODO - add to configs to also get the next scenes
 * TODO - add to configs to get contain script path information
 * TODO - script tracker needs to properly handle end of script and either end game for configure new scene
 * TODO - Find a way to differentiate between script files as they will be sharing the same directory
 * TODO - Need to handle options in script and reactions to choices
 */

public class InGame implements Screen {

    private final NovelOne game;
    private final Batch batch;
    private final Stage stage;
    private final InGameUI uiHandler;
    private String configPath = Paths.TEST_CONFIG_PATH;
    private LinkedHashMap<String, Character> charactersInScene;
    private ArrayDeque<String> script;
    private Group characterRenderGroup;
    private Texture background;
    private ScriptTracker tracker;
    private LinkedHashMap<String, String> visibleCharacters;
    private boolean disableControls = false;

    public InGame(final NovelOne game, final String configPath) {
        Gdx.app.log("InGame::Constructor", "creating new InGame screen");
        this.configPath = configPath;
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        charactersInScene = new LinkedHashMap<>();
        configure();
        this.uiHandler = new InGameUI(stage, game, stepForward(), this);
        characterRenderGroup = new Group();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        try {
                            Gdx.app.log("InGame::InGame", "mouse down detected");
                            if (!disableControls) {
                                uiHandler.nextLine(stepForward());
                            }
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
                                if (!disableControls) {
                                    uiHandler.nextLine(stepForward());
                                }
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
    private SpeakerMap stepForward() {

        SnapShot snapshot = tracker.getNextLine();
        String newScriptName = tracker.getNewScriptName();
        String[] options = tracker.getChoices();

        Gdx.app.log("InGame::stepForward", "choices:" + options);
        if(options != null) {
            this.disableControls = true;
            uiHandler.presentChoices(options);
        }

        Gdx.app.log("InGame::stepForward", "newScriptName: " + newScriptName);
        if (!newScriptName.equals("")) {
            game.getScreen().dispose();
            game.setScreen(new InGame(game, Paths.CONFIGS_PATH + newScriptName));
            return null;
        }
        visibleCharacters = snapshot.getAction();
        AudioHandler.handleMusicCommand(snapshot.getBGMCommand());
        AudioHandler.playSound(snapshot.getSound());

        return snapshot.getDialogue();
    }

    /**
     * Moves the game and the script back
     *
     * @return
     */
    public void stepBack() {
        SnapShot previous = tracker.traceScriptBackwards();
        uiHandler.nextLine(previous.getDialogue());
        visibleCharacters = previous.getAction();
        AudioHandler.handleMusicCommand(previous.getBGMCommand());
        AudioHandler.playSound(previous.getSound());
    }

    /**
     * This is to be called when the player has selected an option after being presented with a list of choices
     *
     * @param choice
     */
    public void handleChoiceSelection(String choice) {
        this.disableControls = false;
        tracker.handleScriptBranching(choice);
        uiHandler.removeChoices();
        uiHandler.nextLine(stepForward());
    }

    private void configure() {
        ConfigReader.readNewConfiguration(configPath);
        AssetsDTO assets = AssetReader.getAllAssets(
                ConfigReader.getScriptPath(),
                ConfigReader.getCastList(),
                ConfigReader.getBackground(),
                ConfigReader.getMusicList(),
                ConfigReader.getSoundList()
        );

        tracker = new ScriptTracker(assets.getScript());
        this.background = assets.getBackgroundTextures();
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

        for (Actor entry : characterRenderGroup.getChildren()) {
            Character character = (Character) entry;
            if (!visibleCharacters.containsKey(character.getName())) {
                character.remove();
            }
        }

        for (Map.Entry<String, String> entry : visibleCharacters.entrySet()) {
            Character targetCharacter = charactersInScene.get(entry.getKey());
            targetCharacter.setExpression(entry.getValue());
            characterRenderGroup.addActor(targetCharacter);
        }

        Sprite backgroundSprite = new Sprite(background);
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
