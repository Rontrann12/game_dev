package com.mygdx.game.novel1.entities.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Paths;

import java.io.File;

public class TestButton {

    private TextButton textButton;
    private FileHandle handler;
    private Skin skin;
    private AssetManager assets;

    public TestButton(final String name){
        this.assets = new AssetManager();
        this.assets.load("menu/button.png", Skin.class);
//        this.handler = new FileHandle(
//                new File("./badlogic.jpg")
//        );
//        this.skin = new Skin(this.handler);
        this.skin = this.assets.get( "menu/button.png", Skin.class);

        this.textButton = new TextButton(name, this.skin);

    }

    public TextButton getTextButton() {
        return this.textButton;
    }

    public void setCoordinates(float x, float y) {
        this.textButton.setX(x);
        this.textButton.setY(y);
    }

}
