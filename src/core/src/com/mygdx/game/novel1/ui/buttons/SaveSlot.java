package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.utils.AudioHandler;

public class SaveSlot extends BaseButton {

    public SaveSlot(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, final String name) {
        super(idleTexture, hoverTexture, game, name);
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(name, "Save Slot button clicked");
                AudioHandler.playSound(buttonClick);
                action(game);
                return true;
            }
        });
    }

    @Override
    public void action(NovelOne game) {
        Gdx.app.log("SaveSlot::action", "Running save slot action");
        try{
            Save save = (Save) game.getScreen();
            save.saveState();

        }catch(ClassCastException e) {
            Gdx.app.log("SaveSlot::action", e.getMessage());
        }

    }

}
