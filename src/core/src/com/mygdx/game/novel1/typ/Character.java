package com.mygdx.game.novel1.typ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.AssetPaths;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.utils.ConfigReader;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.util.HashMap;

public class Character extends Actor {

    private String name;
    private HashMap<String, TextureRegion> expressions;
    private Sprite currentExpression;
    private float alpha;
    private boolean triggerFadeIn;
    private boolean animationComplete;
    private boolean isPresent;


    public Character(String name, Texture[] sheets) {
        this.name = name;
        this.expressions = new HashMap<>();
        gatherExpressions(sheets);
        this.alpha = 0;
        this.triggerFadeIn = true;
        this.animationComplete = false;

    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public boolean isPresent(){
        return this.isPresent;
    }

    private void gatherExpressions(Texture[] sheets) {
        for (int i = 0; i < sheets.length; i++) {
            HashMap<String, TextureRegion> temp;
            temp = ConfigReader.mapSprites(StringUtilities.generateFileName(AssetPaths.CHARACTERS_PATH, name + Separators.UNDERSCORE + i, FileTypes.TEXT), sheets[i]);
            this.expressions.putAll(temp);
        }
    }

    public void setFadeIn() {
        if (this.animationComplete) {
            this.alpha = 0;
            this.triggerFadeIn = true;
            this.animationComplete = false;
        }
    }

    public void setFadeOut() {
        if(this.animationComplete){
            this.alpha = 1;
            this.triggerFadeIn = false;
            this.animationComplete = false;
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
    public void act(float delta) {
        if (this.alpha < 1 && this.triggerFadeIn) {
            this.alpha += 0.1;
            if(this.alpha >= 1){
                this.animationComplete = true;
            }
        }
        else if(this.alpha > 0 && !this.triggerFadeIn) {
            this.alpha -= 0.1;
            if(this.alpha <= 0) {
                this.animationComplete = true;
            }
        }

    }

    public boolean animationComplete(){
        return this.animationComplete;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

        currentExpression.draw(batch, this.alpha);

        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

    }

}
