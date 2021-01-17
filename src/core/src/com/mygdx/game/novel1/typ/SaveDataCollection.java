package com.mygdx.game.novel1.typ;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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
    private boolean controlsDisabled;
    private String[] options;
    private HashMap<String, String[]> saveDataStructure;


    public SaveDataCollection(ArrayList<SnapShot> snapShots,
                              String currentScriptConfig,
                              LinkedHashMap<String,String> visibleCharacters,
                              boolean controlsDisabled,
                              String[] options){

        this.snapShots = snapShots;
        this.currentScriptConfig = currentScriptConfig;
        this.visibleCharacters = visibleCharacters;
        this.controlsDisabled = controlsDisabled;
        this.options = options;

        //this.saveDataStructure = generateSaveDataStructure();
    }

    /**
     * Generates a representation of how save data is organized. Will help with reading data in from a
     * json file. Null values represent that the key contains a single value. Otherwise, that key represents
     * an object
     *
     * good example: https://github.com/libgdx/libgdx/wiki/Reading-and-writing-JSON#reading-object-graphs
     *
     * TODO - see if i can figure out a better way to represent this
     * @return
     */
//    private HashMap<String, String[]> generateSaveDataStructure() {
//        HashMap<String, String[]> tempStructure = new HashMap<>();
//        tempStructure.put(snapShotsKey, SnapShot.getMetaData());
//        tempStructure.put(currentScriptConfigKey, null);
//        tempStructure.put(visibleCharactersKey, )
//
//        return tempStructure;
//
//    }
}
