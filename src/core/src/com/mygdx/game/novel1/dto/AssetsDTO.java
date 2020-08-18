package com.mygdx.game.novel1.dto;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayDeque;
import java.util.HashMap;


public class AssetsDTO {

    private HashMap<String, Texture> textures;
    private ArrayDeque<String> script;
    private String[] soundFX;
    private String[] music;

    public AssetsDTO(ArrayDeque<String> script, HashMap<String, Texture> textures) {
        this.script = script;
        this.textures = textures;
    }

    public ArrayDeque<String> getScript() {

        return this.script;
    }

    public HashMap<String, Texture> getTextures() {

        return this.textures;
    }

}
