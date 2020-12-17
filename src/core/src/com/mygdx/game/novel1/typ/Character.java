package com.mygdx.game.novel1.typ;

import com.badlogic.gdx.Gdx;
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
    private float alpha;


    public Character(String name, Texture[] sheets) {
        this.name = name;
        this.expressions = new HashMap<>();
        gatherExpressions(sheets);
        this.alpha = 0;
    }

    private void gatherExpressions(Texture[] sheets) {
        for (int i = 0; i < sheets.length; i ++) {
            HashMap<String,TextureRegion> temp;
            temp = ConfigReader.mapSprites(StringUtilities.generateFileName(Paths.CHARACTERS_PATH, name + Separators.UNDERSCORE + i, FileTypes.TEXT), sheets[i]);
            this.expressions.putAll(temp);
        }
    }

    public void resetFade() {
        Gdx.app.log("Character::resetFade", "resetFade called");
        this.alpha = 0;
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
    public void act(float delta) {
        if( this.alpha < 1 ){
            Gdx.app.log("Character::act", "new character entry: " + this.alpha);
            this.alpha += 0.1;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.app.log("Character::render", "Character alpha: " + this.alpha);
        currentExpression.draw(batch,this.alpha);
    }
}
