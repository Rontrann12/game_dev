package com.mygdx.game.novel1.typ;

public class ScriptLine {
    private String dialogue;
    private String music;
    private String sound;
    private String character;
    private String action;

    public ScriptLine () {
        this.dialogue = null;
        this.music = null;
        this.sound = null;
        this.character = null;
        this.action = null;
    }

    public ScriptLine (String dialogue, String music, String sound, String character, String action) {
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

    public void setAction(String action) {
        this.action = action;
    }

    public String getDialogue() {
        return this.dialogue;
    }

    public String getMusic() {
        return this.music;
    }

    public String getSound() {
        return this.sound;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getAction() {
        return this.action;
    }

}
