package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.ConfigKeys;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.dto.AssetsDTO;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.AssetReader;
import com.mygdx.game.novel1.utils.ConfigReader;
import com.mygdx.game.novel1.screen.etc.Character;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.util.*;

import static com.badlogic.gdx.Input.Keys.SPACE;


public class InGame implements Screen {

    private final NovelOne game;
    private final Batch batch;
    private final Stage stage;
    private final InGameUI uiHandler;
    private final String path = Paths.TEST_CONFIG_PATH;
    private LinkedHashMap<String, Character> charactersInScene;
    private HashMap<String,Character> charactersOnScreen;
    private ArrayDeque<String> script;
    private Table table;
    private Group characterRenderGroup;

    public InGame(final NovelOne game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        charactersInScene = new LinkedHashMap<>();
        configure();
        this.uiHandler = new InGameUI(stage, game, processScriptLine());
        this.table = new Table();
        characterRenderGroup = new Group();


        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean keyDown(int keyCode) {
                        if (keyCode == SPACE) {
                            Gdx.app.log("in game", "space bar button down detected");
                            try{
                                uiHandler.nextLine(processScriptLine());
                            }catch(EmptyStackException e) {
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
     * This may occur when setting new onscreen sprite followed by animation of another sprite
     *
     * @return
     */
    private String processScriptLine() throws EmptyStackException {

        int limit = 10;
        for (int index = 0; index < limit; index++){

            String line = this.script.pop();
            if (line.contains(Separators.KEYVALUE)) {
                String targetCharacter = StringUtilities.getCharacterName(line);
                String action = StringUtilities.getAction(line);
                processAction(targetCharacter, action);
                Gdx.app.log("InGame::processScriptLine", "target Character: " + targetCharacter + " action: " + action);
            }
            else if (StringUtilities.isDialogue(line)) {
                return line;
            }
        }
        return null;

    }

    private void processAction(String character, String action){

        Character target = charactersInScene.get(character);
        try{

            if(!action.equals("Exit")) {
                Gdx.app.log("InGame::processAction", "adding " + character + " from screen");
                target.setExpression(action);
                characterRenderGroup.addActor(target);
            }
            else{
                Gdx.app.log("InGame::processAction", "removing " + character + " from screen");
                target.remove();
            }
        }catch(NullPointerException e ) {
            Gdx.app.log("InGame::processAction", "no action found, skipping");
        }

    }

    private void configure() {

        ArrayDeque<String> cast = ConfigReader.readCastList(path);
        //ArrayDeque<String> backgrounds = ConfigReader.readBackgroundList(path);
        AssetsDTO assets = AssetReader.getAllAssets(cast);

        this.script = assets.getScript();
        HashMap<String, Texture> textures = assets.getTextures();

        for (Map.Entry<String, Texture> stringTextureEntry : textures.entrySet()) {
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

        //Iterator iterator = onScreenCharacters.entrySet().iterator();

        batch.begin();
//        while (iterator.hasNext()) {
//            Map.Entry pair = (Map.Entry) iterator.next();
//            //Gdx.app.log("InGame::render", "sprite to be drawn on screen: " + pair.getKey());
//            batch.draw(onScreenCharacters.get(pair.getKey()), 0, -50);
//        }
        batch.end();

        this.stage.draw();

    }

    @Override
    public void show() {
        this.stage.addActor(this.characterRenderGroup);
        this.uiHandler.generateUI();

        Integer numActors = this.stage.getActors().size;

        Gdx.app.log("InGame::show", "Getting actors from stage: " + numActors.toString());


    }

    @Override
    public void dispose() {

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
