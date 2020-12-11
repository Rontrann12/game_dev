package com.mygdx.game.novel1.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FadeScreen extends Actor {

    private ShapeRenderer fadeRenderer;
    private float alpha;
    private boolean isComplete;

    public FadeScreen(){
        this.fadeRenderer = new ShapeRenderer(8);
        this.alpha = 1;
        isComplete = false;
    }

    public boolean doneFading() {
        return isComplete;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        fadeRenderer.setColor(0,0,0, alpha);
        fadeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        fadeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fadeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }

    @Override
    public void act(float delta){

        if(alpha > 0) {
            alpha -= 0.01;

        }else{
            isComplete = true;
        }

    }
}
