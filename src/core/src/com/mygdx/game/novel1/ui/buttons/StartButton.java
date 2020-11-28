package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.screen.InGame;
import com.mygdx.game.novel1.utils.AudioHandler;


/**
 * Action of this button will change the screen to InGame
 * TODO - Should probaly be renamed more appropriately (ToGameButton)
 */
public class StartButton extends BaseButton {

    public StartButton(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, final String name) {
        super(idleTexture, hoverTexture, game, name);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log(name, "Start button clicked");
                AudioHandler.playSound(buttonClick);
                action(game);

                return true;
            }
        });
    }

    @Override
    public void action(NovelOne game) {
        game.setScreen(new InGame(game, Paths.CONFIGS_PATH + "script1"));
    }
}
