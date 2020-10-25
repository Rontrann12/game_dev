package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.InGame;
import com.mygdx.game.novel1.utils.AudioHandler;

public class BackButton extends BaseButton {
    public BackButton(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, final String name, final InGame screen) {
        super(idleTexture, hoverTexture, game, name);
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(name, "Back button clicked");
                AudioHandler.playSound(buttonClick);
                action(screen);
                return true;
            }
        });
    }

    @Override
    public void action(NovelOne game) {
    }
    public void action(InGame screen) {
        Gdx.app.log("BackButton::action", "stepping back");
        screen.stepBack();
    }

}
