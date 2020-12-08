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
    protected String dialogue;
    protected String speaker;
    protected Sprite textBoxImage;
    protected BitmapFont text;
    protected BitmapFont speakerText;
    private final int xPadding = 300;
    private final int yPadding = 10;
    private final int yPadding2 = 50;
    protected String toBeDisplayed = "";
    protected ArrayDeque<Character> brokenString;
    private int index = 0;

    public BaseTextBox(TextureRegion textBoxTexture) {
        textBoxImage = new Sprite(textBoxTexture);
        dialogue = null;
        spritePos(0, 0);
        brokenString = new ArrayDeque<>();

    }


    public void spritePos(float x, float y) {
        this.textBoxImage.setPosition(x, y);
        setBounds(textBoxImage.getX(), textBoxImage.getY(), textBoxImage.getWidth(), textBoxImage.getHeight());
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        textBoxImage.draw(batch);

        String dialogue = toBeDisplayed;

        if (dialogue != null) {
            text.draw(batch, dialogue, textX(), textY());
        }

        if (speaker != null) {
            speakerText.draw(batch, speaker, speakerX(), speakerY());
        }

    }

    @Override
    public void act(float delta) {
        if(index < yPadding){
            toBeDisplayed = toBeDisplayed + "ron";
            index++;
        }

        super.act(delta);
    }


}
