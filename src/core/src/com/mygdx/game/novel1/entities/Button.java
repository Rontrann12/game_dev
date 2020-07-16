package com.mygdx.game.novel1.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.flow.InGame;

public class Button extends Actor {

    private Sprite sprite;
    private NovelOne game;

    public Button(Texture texture, final NovelOne game, final String name){
        sprite = new Sprite(texture);
        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);
        this.game = game;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log( name, "again");

                game.setScreen(new InGame(game));

                return true;
            }
        });

    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x,y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }


}
