package com.mygdx.game.novel1.ui.textbox;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dialogue extends BaseTextBox {

    private String character;

    public Dialogue(TextureRegion textBoxTexture) {
        super(textBoxTexture);
    }

    public Dialogue(TextureRegion textBoxTexture, String dialogue) {
        super(textBoxTexture);
        super.dialogue = dialogue;
        super.text = new BitmapFont();
    }

    public void setAlpha(float alpha) {
        super.textBoxImage.setAlpha(alpha);
    }

}
