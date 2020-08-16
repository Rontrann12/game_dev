package com.mygdx.game.novel1.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.dto.AssetsDTO;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.AssetReader;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static com.badlogic.gdx.Input.Keys.SPACE;


public class InGame implements Screen {

    private final NovelOne game;
    private LinkedHashMap<String, TextureRegion> onScreenCharacters;
    private ArrayDeque<String> onScreenOrder;
    private Batch batch;
    private Stage stage;
    private InGameUI uiHandler;
    private AssetsDTO assetsDTO;
    private AssetReader reader;
    private ArrayDeque<String> script;
    private HashMap<String, TextureRegion> sprites;

    public InGame(final NovelOne game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        this.reader = new AssetReader();
        this.assetsDTO = this.reader.getAssets();
        this.onScreenCharacters = new LinkedHashMap<String, TextureRegion>();
        this.onScreenOrder = new ArrayDeque<String>();
        disassembleDTO(this.assetsDTO);
        this.uiHandler = new InGameUI(stage, game, processScriptLine());

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
            String sprite = line.substring(0, line.indexOf(Separators.KEYVALUE));
            Gdx.app.log("InGame::processScriptLine", "Adding sprite on screen: " + sprite);
            onScreenCharacters.put(sprite, sprites.get(sprite));
            onScreenOrder.add(sprite);
            line = this.script.pop();
        }


        return line;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        Iterator iterator = onScreenOrder.iterator();

        batch.begin();
        while (iterator.hasNext()) {
            batch.draw(onScreenCharacters.get(iterator.next()), 0, -100);
        }
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

    private void disassembleDTO(AssetsDTO dto) {
        this.script = dto.getScript();
        this.sprites = dto.getSprites();
    }

}
