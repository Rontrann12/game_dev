package com.mygdx.game.novel1.entities;

import com.badlogic.gdx.graphics.Texture;

public class Button {

    private String path;
    private String text;
    private Texture button;

    public Button(String imgPath, String text) {

        this.path = imgPath;
        this.text = text;

        button = new Texture(imgPath);

    }



}
