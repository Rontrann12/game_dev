package com.mygdx.game.novel1.entities.buttons;

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

abstract class BaseButton extends Actor {

    public Sprite sprite;
    public NovelOne game;

    public BaseButton(Texture texture, final NovelOne game, final String name){
        sprite = new Sprite(texture);
        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);
        this.game = game;


    }

    public void spritePos(float x, float y) {
        sprite.setPosition(x,y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    abstract public void action(NovelOne game);
}
