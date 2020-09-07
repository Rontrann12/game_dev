package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
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
        String[] nameSplit = spriteName.split(Separators.KEYVALUE+Separators.SPACE);
        return nameSplit[0];
    }

    /**
     * parses out the action description of the sprite from the sprite name
     *
     * @param spriteName
     * @return
     */
    public static String getAction(String spriteName) {
        String[] nameSplit = spriteName.split(Separators.KEYVALUE+Separators.SPACE);
        try{
            return nameSplit[1];

        }catch (ArrayIndexOutOfBoundsException e) {
            Gdx.app.log("StringUtilities::getAction", "unable to get the action");
            return null;
        }
    }

//    public static boolean isNewTrack(String line) {
//        try{
//            String underCheck = getContainedContent(line, "~")
//        }
//    }

    public static boolean isDialogue(String line) {

        try{
            String underCheck = getContainedContent(line, '\"');
            Gdx.app.log("StringUtilities::isDialogue", "testing line: " + underCheck);
            if ((underCheck.indexOf("\"") == 0) && (underCheck.lastIndexOf("\"") == underCheck.length()-1)){
                return true;
            }
            return false;

        }catch(StringIndexOutOfBoundsException e) {
            Gdx.app.log("StringUtilities::isDialogue", e.getMessage());
            return false;

        }
    }


    public static String getContainedContent(String line, char container ) {
        int firstChar = line.indexOf(container);
        int secondChar = line.lastIndexOf(container);
        String stripped = line.substring(firstChar, secondChar+1);
        Gdx.app.log("StringUtilities::getContainedContent", "stripped string from " + line + " to: " + stripped);
        return stripped;
    }

}
