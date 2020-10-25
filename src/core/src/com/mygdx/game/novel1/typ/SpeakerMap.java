package com.mygdx.game.novel1.typ;

/**
 * maps the dialogue to the character who is supposed to speak
 */
public class SpeakerMap {

    private String character;
    private String line;

    public SpeakerMap(){
        this.character = null;
        this.line = null;
    }
    public SpeakerMap(String character, String line){
        this.character = character;
        this.line = line;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getLine() {
        return this.line;
    }

}
