package com.mygdx.game.novel1.typ;


import java.util.ArrayDeque;
import java.util.LinkedHashMap;

public class SnapShot {

    private SpeakerMap dialogue;
    private String music;
    private String sound;
    private String character;
    private LinkedHashMap<String, String> action;

    public SnapShot() {
        this.dialogue = null;
        this.music = null;
        this.sound = null;
        this.character = null;
        this.action = null;
    }

    public SnapShot(SnapShot snapshot){
        this.dialogue = snapshot.getDialogue();
        this.music = snapshot.getBGMCommand();
        this.sound = snapshot.getSound();
        this.character = snapshot.getCharacter();
        this.action = snapshot.getAction();
    }

    public SnapShot(SpeakerMap dialogue, String music, String sound, String character, LinkedHashMap<String, String> action) {
        this.dialogue = dialogue;
        this.music = music;
        this.sound = sound;
        this.character = character;
        this.action = action;
    }

    public void setDialogue(SpeakerMap line) {
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

    public void setAction(LinkedHashMap<String, String> action) {
        this.action = action;
    }

    public SpeakerMap getDialogue() {
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

    public LinkedHashMap<String, String> getAction() {
        return this.action;
    }


}
