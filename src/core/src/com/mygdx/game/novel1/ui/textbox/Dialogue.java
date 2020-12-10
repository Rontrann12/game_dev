package com.mygdx.game.novel1.ui.textbox;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dialogue extends BaseTextBox {

    public Dialogue(TextureRegion textBoxTexture, String speaker, String dialogue) {
        super(textBoxTexture);
        super.dialogue = dialogue.toCharArray();
        super.text = new BitmapFont();
        super.speakerText = new BitmapFont();
        super.speaker = speaker;

        spritePos(0,-100);
    }

    public void setAlpha(float alpha) {

        super.textBoxImage.setAlpha(alpha);
    }

    public void updateLine(String speaker, String line) {
        super.dialogue = line.toCharArray();
        super.speaker = speaker;
        super.index = 0;
        super.toBeDisplayed = "";
    }

}
