package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.dto.AssetsDTO;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.AssetReader;
import com.mygdx.game.novel1.utils.ConfigReader;
import com.mygdx.game.novel1.utils.StringUtilities;
import com.mygdx.game.novel1.screen.etc.Character;

import java.util.*;

import static com.badlogic.gdx.Input.Keys.SPACE;


public class InGame implements Screen {

    private final NovelOne game;
    private final LinkedHashMap<String, Sprite> onScreenCharacters;
    private final Batch batch;
    private final Stage stage;
    private final InGameUI uiHandler;
    private final String path = Paths.TEST_CONFIG_PATH;
    private ArrayDeque<Character> charactersInScene;
    private ArrayDeque<String> script;

    public InGame(final NovelOne game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        this.onScreenCharacters = new LinkedHashMap();
        charactersInScene = new ArrayDeque<>();
        configure();
        this.uiHandler = new InGameUI(stage, game, processScriptLine());


        Character temp = charactersInScene.pop();
        temp.setExpression("Monika_smile1");
        temp.spritePos(300,0);
        this.stage.addActor(temp);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean keyDown(int keyCode) {
                        if (keyCode == SPACE) {
                            Gdx.app.log("in game", "space bar button down detected");
                            uiHandler.nextLine(processScriptLine());
                        }

                        return true;
                    }
                }
        );

        Gdx.input.setInputProcessor(multiplexer);

    }


    /**
     * TODO - consider cases where the key value separator exists in consecutive lines.
     * This may occur when setting new onscreen sprite followed by animation of another sprite
     *
     * @return
     */
    private String processScriptLine() {

        String line = this.script.pop();

        if (line.contains(Separators.KEYVALUE)) {

            //removeDuplicateCharacters(StringUtilities.getCharacterName(line));
            //String sprite = line.substring(0, line.indexOf(Separators.KEYVALUE));
            line = this.script.pop();
        }


        return line;
    }

    private void removeDuplicateCharacters(String name) {
        Gdx.app.log("InGame::removeDuplicateCharacter", "inside method with name: " + name);
        Iterator iterator = onScreenCharacters.entrySet().iterator();
        String target = null;

        while (iterator.hasNext()) {
            Map.Entry<String, TextureRegion> next = (Map.Entry) iterator.next();
            String key = next.getKey();
            if (key.contains(name)) {
                target = key;
                break;
            }
        }

        if (target != null) {
            Gdx.app.log("InGame::removeDuplicateCharacter", "removing duplicate: " + target);
            this.onScreenCharacters.remove(target);
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

    private void configure() {

        ArrayDeque<String> cast = ConfigReader.readCastList(path);
        AssetsDTO assets = AssetReader.getAllAssets(cast);

        this.script = assets.getScript();
        HashMap<String, Texture> textures = assets.getTextures();

        for (Map.Entry<String, Texture> stringTextureEntry : textures.entrySet()) {
            Map.Entry<String, Texture> pair = stringTextureEntry;
            Gdx.app.log("InGame::configure", "adding character texture: " + pair.getKey());
            charactersInScene.add(new Character(pair.getKey(), pair.getValue()));
        }

    }
}
