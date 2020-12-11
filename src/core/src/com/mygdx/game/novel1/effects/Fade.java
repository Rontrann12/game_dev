package com.mygdx.game.novel1.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fade extends Actor {

    private ShapeRenderer fadeRenderer;
    private float alpha;

    public Fade(){
        this.fadeRenderer = new ShapeRenderer(8);
        this.alpha = 1;
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
            this.remove();
        }

    }
}
