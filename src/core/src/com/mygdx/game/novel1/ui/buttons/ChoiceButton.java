package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.InGame;
import com.mygdx.game.novel1.utils.AudioHandler;

public class ChoiceButton extends BaseButton {


    public ChoiceButton(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, final String name, final InGame screen) {
        super(idleTexture, hoverTexture, game, name);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(name, "Choice button clicked");
                AudioHandler.playSound(buttonClick);
                action(screen, name);
                return true;
            }
        });

    }


    public void action(InGame screen, String name) {
        screen.handleChoiceSelection(name);
    }

    @Override
    public void action(NovelOne game) {

    }
}
