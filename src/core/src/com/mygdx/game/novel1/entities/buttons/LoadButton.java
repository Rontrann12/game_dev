package com.mygdx.game.novel1.entities.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.flow.Load;

public class LoadButton extends BaseButton{

    public LoadButton(Texture texture,final NovelOne game, final String name){
        super(texture,game, name);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log(name, "again");

                action(game);

                return true;
            }
        });

    }


    @Override
    public void action(NovelOne game ){
        game.setScreen(new Load(game));
    }
}
