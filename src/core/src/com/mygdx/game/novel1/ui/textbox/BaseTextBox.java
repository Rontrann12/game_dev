package com.mygdx.game.novel1.ui.textbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayDeque;

abstract class BaseTextBox extends Actor {

    protected String[] script;
    protected char[] dialogue;
    protected String speaker;
    protected Sprite textBoxImage;
    protected BitmapFont text;
    protected BitmapFont speakerText;
    private final int xPadding = 300;
    private final int yPadding = 10;
    private final int yPadding2 = 50;
    protected String toBeDisplayed = "";
    protected int index = 0;
    private boolean textDisplayed = true;

    public BaseTextBox(TextureRegion textBoxTexture) {
        textBoxImage = new Sprite(textBoxTexture);
        dialogue = null;
        spritePos(0, 0);
    }


    public void spritePos(float x, float y) {
        this.textBoxImage.setPosition(x, y);
        setBounds(textBoxImage.getX(), textBoxImage.getY(), textBoxImage.getWidth(), textBoxImage.getHeight());
    }

    public boolean textFullyDisplayed(){
        return textDisplayed;
    }

    public void fastForward() {
        this.index = dialogue.length;
        this.toBeDisplayed = new String(dialogue);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        textBoxImage.draw(batch);

        String dialogue = toBeDisplayed;

        if (dialogue != null) {
            text.draw(batch,dialogue,textX(),textY(), Gdx.graphics.getWidth()/4f, -1, true);
        }

        if (speaker != null) {
            speakerText.draw(batch, speaker, speakerX(), speakerY());
        }

    }

    @Override
    public void act(float delta) {
        if (index < dialogue.length) {
            textDisplayed = false;
            toBeDisplayed = toBeDisplayed + dialogue[index];
            index++;
        }
        else{
            textDisplayed = true;
        }


        super.act(delta);
    }


    private float textX() {
        return textBoxImage.getX() + xPadding;
    }

    private float textY() {
        return textBoxImage.getY() + textBoxImage.getHeight() / 2  + yPadding2;
    }

    private float speakerX() {
        return textBoxImage.getX() + xPadding;
    }

    private float speakerY() {
        return textBoxImage.getY() + 3 * (textBoxImage.getHeight() / 4 + yPadding);
    }



}
