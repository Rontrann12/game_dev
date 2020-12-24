package com.mygdx.game.novel1.typ;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SaveDataCollection {



    // Save Point in the script that the player has reached
    //  - The current script that the player is on (check, stored in configPath)
    //  - Need to save the snapshot collection (check)
    //  - Choices that the player has made until now ( might be stored in the log if back functionality is to be removed)
    //  - All assets need to be restored but this information is kept in the config file
    //  - Characters present on screen at the time (check, character and their sprite is stored in visible characters)
    // log history needs to be the saved (this still has not been implemented)
    //

    private ArrayList<SnapShot> snapShots;
    private String currentScriptConfig;
    private LinkedHashMap<String,String> visibleCharacters;

    public SaveDataCollection(){
    }
    public SaveDataCollection(ArrayList<SnapShot> snapShots,
                              String currentScriptConfig,
                              LinkedHashMap<String,String> visibleCharacters){

        this.snapShots = snapShots;
        this.currentScriptConfig = currentScriptConfig;
        this.visibleCharacters = visibleCharacters;

    }
}
