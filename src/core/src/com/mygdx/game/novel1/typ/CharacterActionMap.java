package com.mygdx.game.novel1.typ;

public class CharacterActionMap {

    private String character;
    private String action;
    public CharacterActionMap(String character, String action) {
        this.character = character;
        this.action = action;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getAction() {
        return this.action;
    }

}

