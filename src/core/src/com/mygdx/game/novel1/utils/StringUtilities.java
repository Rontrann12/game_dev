package com.mygdx.game.novel1.utils;

import com.mygdx.game.novel1.constants.Separators;

public class StringUtilities {


    /**
     * concatenates the string path, file name and file extension
     *
     * @param path
     * @param fileName
     * @param extension
     * @return
     */
    public static String generateFileName(String path, String fileName, String extension) {
        return path + fileName + Separators.FILETYPE + extension;
    }

    /**
     * parses out the character name from the sprite names
     *
     * @param spriteName
     * @return
     */
    public static String getCharacterName(String spriteName) {
        String[] nameSplit = spriteName.split("_");
        return nameSplit[0];
    }

    /**
     * parses out the action description of the sprite from the sprite name
     *
     * @param spriteName
     * @return
     */
    public static String getAction(String spriteName) {
        String[] nameSplit = spriteName.split("_");
        String result = Separators.EMPTY;

        for (int iterator = 1; iterator < nameSplit.length; iterator++){
            result = result + nameSplit[iterator];
        }

        return result;

    }
}
