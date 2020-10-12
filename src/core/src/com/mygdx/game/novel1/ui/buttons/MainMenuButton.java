package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.MainMenu;
import com.mygdx.game.novel1.utils.AudioHandler;

public class MainMenuButton extends BaseButton {
    public MainMenuButton(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, final String name){
        super(idleTexture, hoverTexture,game, name);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log(name, "again");
                AudioHandler.playSound(buttonClick);
                action(game);

                return true;
            }
        });

    }

    @Override
    public void action(NovelOne game ){
        game.getScreen().dispose();
        game.setScreen(new MainMenu(game));
    }

}
