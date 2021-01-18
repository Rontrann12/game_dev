package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.novel1.constants.ConfigKeys;
import com.mygdx.game.novel1.constants.AssetPaths;
import com.mygdx.game.novel1.constants.Separators;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.mygdx.game.novel1.constants.ConfigKeys.*;

public class ConfigReader {

    private static String background;
    private static ArrayDeque<String> castList;
    private static ArrayDeque<String> music;
    private static ArrayDeque<String> sounds;
    private static String scriptPath = "";

    /**
     * reads info on what assets should be loaded in the configuration for a scene
     *
     * @param path
     */
    public static void readNewConfiguration(String path) {

        String separator = Separators.KEY_SPACE;

        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                int separatorIndex = data.indexOf(separator);

                String config = data.substring(0, separatorIndex);
                String dataSection = data.substring(separatorIndex + 2);
                Gdx.app.log("ConfigReader::readNewConfiguration", "value of the config: " + config);
                Gdx.app.log("ConfigReader::readNewConfiguration", "reading data section: " + dataSection);

                switch (config) {
                    case CHARACTER_MODE:
                        castList = new ArrayDeque<>(Arrays.asList(dataSection.split((Separators.DATAFIELDS + Separators.SPACE))));
                        break;

                    case BACKGROUND_MODE:
                        background = dataSection;
                        break;

                    case BGM_MODE:
                        music = new ArrayDeque<>(Arrays.asList(dataSection.split((Separators.DATAFIELDS + Separators.SPACE))));
                        break;

                    case SFX_MODE:
                        sounds = new ArrayDeque<>(Arrays.asList(dataSection.split((Separators.DATAFIELDS + Separators.SPACE))));
                        break;
                    case SCRIPT_PATH_MODE:
                        scriptPath = AssetPaths.SCRIPTS_PATH + dataSection;
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            Gdx.app.log("ConfigReader::readCastList", e.getMessage());
        }

    }

    /**
     * Reads a config file specifying the location and dimensions of each sprite in a
     * texture region and maps it with its name.
     *
     * @param configPath
     */
    public static HashMap<String, TextureRegion> mapSprites(String configPath, Texture texture) {
        HashMap<String, TextureRegion> mapping = new HashMap<String, TextureRegion>();
        String targetCharacter = null;
        String key = null;
        int xCoord = -1;
        int yCoord = -1;
        int width = -1;
        int height = -1;

        try {
            File file = new File(configPath);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                if (data.indexOf(Separators.FILETYPE) != -1) {
                    targetCharacter = data.split("\\" + Separators.FILETYPE)[0].split(Separators.UNDERSCORE)[0];
                } else if (data.contains(targetCharacter)) {
                    key = data;
                }
                if (data.contains(ConfigKeys.COORDINATES)) {
                    String[] parsedData = parseDataField(data);
                    xCoord = Integer.parseInt(parsedData[0]);
                    yCoord = Integer.parseInt(parsedData[1]);
                }

                if (data.contains(ConfigKeys.DIMENSIONS) && xCoord != -1 && yCoord != -1) {
                    String[] parsedData = parseDataField(data);
                    width = Integer.parseInt(parsedData[0]);
                    height = Integer.parseInt(parsedData[1]);
                }

                if (xCoord != -1 && yCoord != -1 && width != -1 && height != -1) {
                    Gdx.app.log("ConfigReader::mapSprites", "creating region of dimensions: " + xCoord + " " + yCoord + " " + width + " " + height + " For key: " + key);
                    TextureRegion region = new TextureRegion(texture, xCoord, yCoord, width, height);
                    mapping.put(key, region);
                    xCoord = -1;
                    yCoord = -1;
                    width = -1;
                    height = -1;
                }
            }

        } catch (Exception e) {
            Gdx.app.log("ConfigReader::mapSprites", e.getMessage());
        }

        return mapping;
    }

    public static ArrayDeque<String> getSoundList(){
        return sounds;
    }

    public static ArrayDeque<String> getCastList() {
        return castList;
    }

    public static String getBackground() {
        return background;
    }

    public static ArrayDeque<String> getMusicList(){ return music; }

    public static String getScriptPath(){
        Gdx.app.log("ConfigReader::getScriptPath", scriptPath);
        return scriptPath;
    }
    /**
     * TODO - decide if this should be moved to String utilities
     * @param data
     * @return
     */

    private static String[] parseDataField(String data) {

        int separatorIndex = data.indexOf(Separators.KEY_SPACE);
        String dataField = data.substring(separatorIndex);

        int dataSeparatorIndex = dataField.indexOf(Separators.DATAFIELDS + Separators.SPACE);
        String field1 = dataField.substring(2, dataSeparatorIndex).replace(Separators.SPACE, Separators.EMPTY);
        String field2 = dataField.substring(dataSeparatorIndex + 1).replace(Separators.SPACE, Separators.EMPTY);

        return new String[]{field1, field2};
    }

}
