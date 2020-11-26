package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.utils.AudioHandler;

public class ReturnButton extends BaseButton {

    public ReturnButton(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, String name, Save save) {
        super(idleTexture, hoverTexture, game, name);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("SaveButton::Savebutton", "Mouse entered bounds");

                AudioHandler.playSound(buttonClick);

                action(game);

                return true;
            }
        });
    }

    public void action(NovelOne game, Save save) {
        Gdx.app.log("ReturnButton::action", "method executing");
        //game.setScreen(save.getPreviousScreen());
    }

    @Override
    public void action(NovelOne game) {
        Gdx.app.log("ReturnButton::action", "overriden method executing");

    }
}