package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.InGame;
import com.mygdx.game.novel1.screen.Load;
import com.mygdx.game.novel1.utils.AudioHandler;

public class LoadSlot extends BaseButton {

    private String loadDate;

    public LoadSlot(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, String name) {
        super(idleTexture, hoverTexture, game, name);
        this.loadDate = null;
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioHandler.playSound(buttonClick);
                action(game);
                return true;
            }
        });
    }

    @Override
    public void action(NovelOne game) {
        try{
            Load load = (Load) game.getScreen();
            game.setScreen(new InGame(game,load.retrieveSavedData()));
        }catch(ClassCastException e) {
            Gdx.app.log("LoadSlot::action", e.getMessage());
        }
    }
}
