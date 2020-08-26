package com.mygdx.game.novel1.dto;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayDeque;
import java.util.HashMap;


public class AssetsDTO {

    private HashMap<String, Texture> characterTextures;
    private ArrayDeque<String> script;
    private String[] soundFX;
    private String[] music;
    private HashMap<String,Texture> backgrounds;

    public AssetsDTO(ArrayDeque<String> script, HashMap<String, Texture> textures, HashMap<String, Texture> backgrounds) {
        this.script = script;
        this.characterTextures = textures;
        this.backgrounds = backgrounds;
    }

    public ArrayDeque<String> getScript() {

        return this.script;
    }

    public HashMap<String, Texture> getCharacterTextures() {
        return this.characterTextures;
    }

    public HashMap<String, Texture> getBackgroundTextures() {
        return this.backgrounds;
    }

}
