package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.effects.ScreenFade;
import com.mygdx.game.novel1.typ.AssetsDTO;
import com.mygdx.game.novel1.typ.SnapShot;
import com.mygdx.game.novel1.typ.SpeakerMap;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.*;
import com.mygdx.game.novel1.typ.Character;

import java.lang.reflect.Array;
import java.util.*;

import static com.badlogic.gdx.Input.Keys.SPACE;



public class InGame implements Screen {

    private final NovelOne game;
    private final Batch batch;
    private final Stage stage;
    private final InGameUI uiHandler;
    private String configPath = Paths.TEST_CONFIG_PATH;
    private HashMap<String, Character> charactersInScene;
    private ArrayDeque<String> script;
    private Group characterRenderGroup;
    private Texture background;
    private ScriptTracker tracker;
    private LinkedHashMap<String, String> visibleCharacters;
    private boolean disableControls = false;
    private InputMultiplexer multiplexer;
    private String newScriptName;
    private ScreenFade screenFade;
    private Sprite backgroundSprite;
    private String saveData;

    public InGame(final NovelOne game, final String configPath) {
        Gdx.app.log("InGame::Constructor", "creating new InGame screen");
        this.configPath = configPath;
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        charactersInScene = new HashMap<>();
        configure();
        this.uiHandler = new InGameUI(stage, game, new SpeakerMap(), this);
        characterRenderGroup = new Group();
        newScriptName = "";
        this.saveData ="InGame screen returning save data";

        this.multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                        manageInput();
                        return true;
                    }

                    @Override
                    public boolean keyDown(int keyCode) {
                        if (keyCode == SPACE || keyCode == Input.Buttons.LEFT) {
                            manageInput();
                        }
                        return true;
                    }
                }
        );

        //Gdx.input.setInputProcessor(multiplexer);
    }

    private void manageInput() {
        try {
            Gdx.app.log("InGame::InGame", "input detected");
            if (!disableControls) {
                if (uiHandler.textBoxReady()) {
                    uiHandler.nextLine(stepForward());
                } else {
                    uiHandler.textBoxFastForward();
                }
            }
        } catch (EmptyStackException e) {
            Gdx.app.log("InGame::InGame", e.getMessage());
        }
    }

    /**
     * progresses the game and the script forward
     *
     * @return
     */
    private SpeakerMap stepForward() {

        SnapShot snapshot = tracker.getNextLine();
        newScriptName = tracker.getNewScriptName();
        String[] options = tracker.getChoices();

        Gdx.app.log("InGame::stepForward", "choices:" + options);
        if (options != null) {
            this.disableControls = true;
            uiHandler.presentChoices(options);
        }

        Gdx.app.log("InGame::stepForward", "newScriptName: " + newScriptName);
        if (!newScriptName.equals("")) {
            this.disableControls = true;
            this.screenFade = new ScreenFade(false);
            this.stage.addActor(screenFade);
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
        if (!disableControls) {
            SnapShot previous = tracker.traceScriptBackwards();
            uiHandler.nextLine(previous.getDialogue());
            visibleCharacters = previous.getAction();
            AudioHandler.handleMusicCommand(previous.getBGMCommand());
            AudioHandler.playSound(previous.getSound());
        }
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

    public String getSaveData() {
        return this.saveData;
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
        HashMap<String, Texture[]> characters = assets.getCharacterTextures();


        for (Map.Entry<String, Texture[]> stringTextureEntry : characters.entrySet()) {
            Map.Entry<String, Texture[]> pair = stringTextureEntry;
            Gdx.app.log("InGame::configure", "adding character texture: " + pair.getKey());
            charactersInScene.put(pair.getKey(), new Character(pair.getKey(), pair.getValue()));
        }
    }

    private void removeExitedCharacters() {
        for (Actor entry : characterRenderGroup.getChildren()) {
            Character character = (Character) entry;
            if (!visibleCharacters.containsKey(character.getName())) {
                if(character.isPresent()){
                    Gdx.app.log("InGame::render", "adding character");
                    character.setFadeOut();
                    character.setIsPresent(false);
                    Gdx.app.log("InGame::removeExitedCharacters", "removing " +character.getName() + " from screen");
                }
            }
        }
    }

    private void updateVisibleCharacters() {

        if (visibleCharacters != null) {
            for (Map.Entry<String, String> entry : visibleCharacters.entrySet()) {

                Character targetCharacter = charactersInScene.get(entry.getKey());
                targetCharacter.setExpression(entry.getValue());

                if (!targetCharacter.isPresent()) {
                    characterRenderGroup.addActor(targetCharacter);
                    targetCharacter.setFadeIn();
                    targetCharacter.setIsPresent(true);
                }
            }
            uiHandler.positionCharacterSprites(visibleCharacters, charactersInScene);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());

        Gdx.graphics.setContinuousRendering(true);

        removeExitedCharacters();
        updateVisibleCharacters();

        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        this.stage.draw();

        /*
            Removes the character from the render group after animations are done
         */
        Iterator<Actor> it = characterRenderGroup.getChildren().iterator();
        while(it.hasNext()) {
            Character temp = (Character) it.next();
            if(!temp.isPresent() && temp.animationComplete()){
                temp.remove();
            }
        }

        if (!newScriptName.equals("") && this.screenFade.isComplete()) {
            dispose();
        }
    }

    @Override
    public void show() {

        this.stage.addActor(this.characterRenderGroup);
        this.uiHandler.generateUI();
        int numActors = this.stage.getActors().size;
        manageInput();
        Gdx.app.log("InGame::show", "Getting actors from stage: " + numActors);
        Gdx.app.log("InGame::show", "Is controls disabled: " + disableControls);


        this.backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.app.log("InGame::show", "before fade");
        ScreenFade screenFade = new ScreenFade();
        stage.addActor(screenFade);
        Gdx.app.log("InGame::show", "after fade");
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void dispose() {
        AudioHandler.clearMusic();
        AudioHandler.clearSounds();
        if(!newScriptName.isEmpty()){
            game.setScreen(new InGame(game, Paths.CONFIGS_PATH + newScriptName));
        }
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
        Gdx.app.log("InGame::hide", "no longer current screen");
    }


}
