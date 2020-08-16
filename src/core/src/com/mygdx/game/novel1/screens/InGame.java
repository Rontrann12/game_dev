package com.mygdx.game.novel1.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.dto.AssetsDTO;
import com.mygdx.game.novel1.ui.layouts.InGameUI;
import com.mygdx.game.novel1.utils.AssetReader;

import java.util.ArrayDeque;
import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.SPACE;


public class InGame implements Screen {

    private final NovelOne game;
    private TextureRegion yuriSprite;
    private TextureRegion monikaSprite;
    private Batch batch;
    private Stage stage;
    private InGameUI uiHandler;
    private AssetsDTO assetsDTO;
    private AssetReader reader;
    private ArrayDeque<String> script;
    private HashMap<String, TextureRegion> sprites;

    public InGame(final NovelOne game){
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.batch = stage.getBatch();
        this.uiHandler = new InGameUI(stage, game);
        this.reader = new AssetReader();
        this.assetsDTO = this.reader.getAssets();
        disassembleDTO(this.assetsDTO);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean keyDown(int keyCode) {
                        if (keyCode == SPACE) {
                            Gdx.app.log("in game", "space bar button down detected");
                            uiHandler.nextLine((String) script.remove());
                        }

                        return true;
                    }
                }
        );

        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();
        batch.draw(yuriSprite,0,-50);
        batch.draw(monikaSprite, 500, -50);
        batch.end();

        this.stage.draw();

    }

    @Override
    public void show() {
        this.uiHandler.generateUI();
        this.yuriSprite = sprites.get("Yuri_shy");
        this.monikaSprite = sprites.get("Monika_turn_away");

        Integer numActors = this.stage.getActors().size;

        Gdx.app.log("InGame::show" ,"Getting actors from stage: " + numActors.toString());


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

    private void disassembleDTO(AssetsDTO dto){
        this.script = dto.getScript();
        this.sprites = dto.getSprites();
    }

}
