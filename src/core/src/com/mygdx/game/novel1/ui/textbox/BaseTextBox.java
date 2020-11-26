package com.mygdx.game.novel1.ui.textbox;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract class BaseTextBox extends Actor {

    protected String[] script;
    protected String dialogue;
    protected String speaker;
    protected Sprite textBoxImage;
    protected BitmapFont text;
    protected BitmapFont speakerText;

    public BaseTextBox(TextureRegion textBoxTexture) {
        textBoxImage = new Sprite(textBoxTexture);
        dialogue = null;
        spritePos(0, 0);

    }


    public void spritePos(float x, float y) {
        this.textBoxImage.setPosition(x, y);
        setBounds(textBoxImage.getX(), textBoxImage.getY(), textBoxImage.getWidth(), textBoxImage.getHeight());
    }

    private float textX() {
        return textBoxImage.getX() + textBoxImage.getWidth() / 2;
    }

    private float textY() {
        return textBoxImage.getY() + textBoxImage.getHeight() / 2;
    }

    private float speakerX() {
        return textBoxImage.getX() + textBoxImage.getWidth() / 4;
    }

    private float speakerY() {
        return textBoxImage.getY() + 3 * (textBoxImage.getHeight() / 4);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        textBoxImage.draw(batch);

        if (dialogue != null) {
            text.draw(batch, dialogue, textX(), textY());
        }

        if (speaker != null) {
            speakerText.draw(batch, speaker, speakerX(), speakerY());
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


}
