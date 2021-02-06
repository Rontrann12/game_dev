package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.utils.AudioHandler;

public class SaveButton extends BaseButton {

    public SaveButton(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, String name) {
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

    @Override
    public void action(NovelOne game) {
        Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.setScreen(new Save(game, pixmap));
    }
}
