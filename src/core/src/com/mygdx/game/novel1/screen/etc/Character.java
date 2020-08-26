package com.mygdx.game.novel1.screen.etc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.utils.ConfigReader;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.util.HashMap;

public class Character extends Actor {

    private String name;
    private HashMap<String, TextureRegion> expressions;
    private Sprite currentExpression;


    public Character(String name, Texture sheet) {
        this.name = name;
        this.expressions = ConfigReader.mapSprites(StringUtilities.generateFileName(Paths.CHARACTERS_PATH, name, FileTypes.TEXT), sheet);
    }

    public void setExpression(String expression) {
        currentExpression = new Sprite(expressions.get(expression));
    }

    public void spritePos(float x, float y) {
        currentExpression.setPosition(x, y);
        setBounds(currentExpression.getX(), currentExpression.getY(), currentExpression.getWidth(), currentExpression.getHeight());
    }

    public String getName() {
        return name;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        currentExpression.draw(batch);
    }
}
