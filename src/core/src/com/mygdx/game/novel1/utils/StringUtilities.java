package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.novel1.constants.ScriptCues;
import com.mygdx.game.novel1.constants.Separators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String[] nameSplit = spriteName.split(Separators.KEY_SPACE);

        if(nameSplit.length == 1) {
            nameSplit[0] = nameSplit[0].replace(Separators.KEYVALUE,Separators.EMPTY);
        }

        String name = nameSplit[0];
        if(nameSplit[0].contains(Separators.OPEN_BRACKET) && nameSplit[0].contains(Separators.CLOSE_BRACKET)) {
            name = name.substring(0,name.indexOf('('));
        }

        return name;
    }

    /**
     * parses out aliases from character names
     */

    public static String getCharacterAlias(String input) {
        String[] nameSplit = input.split(Separators.KEY_SPACE);

        if(isContainer(nameSplit[0], Separators.OPEN_BRACKET.charAt(0), Separators.CLOSE_BRACKET.charAt(0))){
            return getContainedContent(input, Separators.OPEN_BRACKET.charAt(0), Separators.CLOSE_BRACKET.charAt(0), true );
        }
        return null;
    }

    /**
     * parses out the action description of the sprite from the sprite name
     *
     * @param spriteName
     * @return
     */
    public static String getAction(String spriteName) {
        String[] nameSplit = spriteName.split(Separators.KEY_SPACE);
        try{
            return nameSplit[1];

        }catch (ArrayIndexOutOfBoundsException e) {
            Gdx.app.log("StringUtilities::getAction", "unable to get the action");
            return null;
        }
    }

    /**
     *
     * checks that the containerCharacter contains a string
     *
     * @param line
     * @param openingContainer
     * @param closingContainer
     * @return
     */
    public static boolean isContainer(String line, char openingContainer, char closingContainer) {
        try {
            String underCheck = getContainer(line, openingContainer, closingContainer);
            Gdx.app.log("StringUtilities::isDialogue", "testing line: " + underCheck);
            if ((underCheck.indexOf(openingContainer) == 0) && (underCheck.lastIndexOf(closingContainer) == underCheck.length()-1)){
                return true;
            }
            return false;

        }catch(StringIndexOutOfBoundsException e) {
            Gdx.app.log("StringUtilities::isDialogue", e.getMessage());
            return false;

        }
    }


    public static String getContainedContent(String line, char container, boolean stripContainer) {
        int firstChar = line.indexOf(container);
        int secondChar = line.lastIndexOf(container);
        String stripped;

        if(stripContainer){
            stripped = line.substring(firstChar +1 , secondChar);
        }
        else{
            stripped = line.substring(firstChar, secondChar + 1);
        }

        Gdx.app.log("StringUtilities::getContainedContent", "stripped string from " + line + " to: " + stripped);
        return stripped;
    }

    public static String getContainedContent(String line, char openingCharacter, char closingCharacter, boolean stripContainer) {
        int firstChar = line.indexOf(openingCharacter);
        int secondChar = line.lastIndexOf(closingCharacter);
        String stripped;

        if(stripContainer){
            stripped = line.substring(firstChar +1 , secondChar);
        }
        else{
            stripped = line.substring(firstChar, secondChar + 1);
        }

        Gdx.app.log("StringUtilities::getContainedContent", "stripped string from " + line + " to: " + stripped);
        return stripped;
    }


    private static String getContainer(String line, char openingContainer, char closingContainer){
        int firstChar = line.indexOf(openingContainer);
        int secondChar = line.lastIndexOf(closingContainer);
        String stripped = line.substring(firstChar, secondChar+1);
        Gdx.app.log("StringUtilities::getContainer", "stripped string from " + line + " to: " + stripped);
        return stripped;
    }

    public static String getCompactDateFormat(String date) throws ParseException{
        Date parsedOldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        return new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(parsedOldDate);

    }

    public static String getExpandedDateFormat(String date) throws ParseException {
        Date parsedOldDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss").parse(date);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parsedOldDate);
    }

    public static String appendPrefix(String prefix, String string) {
        return prefix + Separators.UNDERSCORE + string;
    }

    public static String getPrefix(String string) {
        return string.split(Separators.UNDERSCORE)[0];
    }

    public static String getStringAfterPrefix(String string) {
        String[] stringSplit = string.split(Separators.UNDERSCORE);
        String result = stringSplit[1];
        for(int i = 2; i < stringSplit.length; i++){
            result = result + Separators.UNDERSCORE + stringSplit[i];
        }
        return result;
    }

    public static String removeFileExtention(String string) {
        return string.split("\\"+Separators.FILETYPE)[0];
    }


}
