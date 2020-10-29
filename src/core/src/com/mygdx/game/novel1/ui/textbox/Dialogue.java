package com.mygdx.game.novel1.ui.textbox;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dialogue extends BaseTextBox {

    public Dialogue(TextureRegion textBoxTexture) {
        super(textBoxTexture);
    }

    public Dialogue(TextureRegion textBoxTexture, String speaker, String dialogue) {
        super(textBoxTexture);
        super.dialogue = dialogue;
        super.text = new BitmapFont();
        super.speakerText = new BitmapFont();
        super.speaker = speaker;
    }

    public void setAlpha(float alpha) {

        super.textBoxImage.setAlpha(alpha);
    }

    public void updateLine(String speaker, String line) {
        super.dialogue = line;
        super.speaker = speaker;
    }

}
