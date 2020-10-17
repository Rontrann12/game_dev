package com.mygdx.game.novel1.typ;


import java.util.ArrayDeque;

public class SnapShot {
    private String dialogue;
    private String music;
    private String sound;
    private String character;
    private ArrayDeque<CharacterActionMap> action;

    public SnapShot() {
        this.dialogue = null;
        this.music = null;
        this.sound = null;
        this.character = null;
        this.action = null;
    }

    public SnapShot(String dialogue, String music, String sound, String character, ArrayDeque<CharacterActionMap> action) {
        this.dialogue = dialogue;
        this.music = music;
        this.sound = sound;
        this.character = character;
        this.action = action;
    }

    public void setDialogue(String line) {
        this.dialogue = line;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setAction(ArrayDeque<CharacterActionMap> action) {
        this.action = action;
    }

    public String getDialogue() {
        return this.dialogue;
    }

    public String getBGMCommand() {
        return this.music;
    }

    public String getSound() {
        return this.sound;
    }

    public String getCharacter() {
        return this.character;
    }

    public ArrayDeque<CharacterActionMap> getAction() {
        return this.action;
    }

}
