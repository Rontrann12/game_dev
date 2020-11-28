package com.mygdx.game.novel1.typ;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.utils.ConfigReader;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.util.HashMap;

public class Character extends Actor {

    private String name;
    private boolean hideName;
    private HashMap<String, TextureRegion> expressions;
    private Sprite currentExpression;


    public Character(String name, Texture[] sheets) {
        this.name = name;
        this.expressions = new HashMap<>();
        gatherExpressions(sheets);
    }

    private void gatherExpressions(Texture[] sheets) {
        for (int i = 0; i < sheets.length; i ++) {
            HashMap<String,TextureRegion> temp;
            temp = ConfigReader.mapSprites(StringUtilities.generateFileName(Paths.CHARACTERS_PATH, name + Separators.UNDERSCORE + i, FileTypes.TEXT), sheets[i]);
            this.expressions.putAll(temp);
        }
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
