package com.mygdx.game.novel1.dto;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayDeque;
import java.util.HashMap;


public class AssetsDTO {

    private HashMap<String, Texture> characterTextures;
    private ArrayDeque<String> script;
    private String[] soundFX;
    private HashMap<String, Music> tracks;
    private HashMap<String,Texture> backgrounds;
    private HashMap<String,Sound> sounds;

    public AssetsDTO(ArrayDeque<String> script, HashMap<String, Texture> textures, HashMap<String, Texture> backgrounds, HashMap<String, Music> tracks, HashMap<String, Sound> sounds) {
        this.script = script;
        this.characterTextures = textures;
        this.backgrounds = backgrounds;
        this.tracks = tracks;
        this.sounds = sounds;
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

    public HashMap<String, Music> getTracks() { return this.tracks; }

    public HashMap<String, Sound> getSounds(){ return this.sounds; }
}
