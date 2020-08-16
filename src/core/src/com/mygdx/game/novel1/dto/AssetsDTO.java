package com.mygdx.game.novel1.dto;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;


public class AssetsDTO {

    private HashMap<String, TextureRegion> sprites;
    private ArrayDeque<String> script;
    private String[] soundFX;
    private String[] music;

    public AssetsDTO(ArrayDeque<String> script, HashMap<String, TextureRegion> sprites) {
        this.script = script;
        this.sprites = sprites;
    }

    public ArrayDeque<String> getScript() {

        return this.script;
    }

    public HashMap<String, TextureRegion> getSprites() {
        return this.sprites;
    }

}
