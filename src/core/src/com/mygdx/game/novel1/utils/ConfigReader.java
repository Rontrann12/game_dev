package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.novel1.constants.ConfigKeys;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.constants.Separators;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.mygdx.game.novel1.constants.ConfigKeys.CHARACTER_MODE;

public class ConfigReader {

    /**
     * adds new mappings of every sprite for a single character to the hashmap
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

        try{
            File file = new File(configPath);
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()) {
                String data = reader.nextLine();

                if(data.indexOf(Separators.FILETYPE) != -1) {
                    targetCharacter = data.substring(0, data.indexOf(Separators.FILETYPE));
                }
                else if (data.contains(targetCharacter)) {
                    key = data;
                }
                if(data.contains(ConfigKeys.COORDINATES)) {
                    String[] parsedData = parseDataField(data);
                    xCoord =  Integer.parseInt(parsedData[0]);
                    yCoord = Integer.parseInt(parsedData[1]);
                }

                if(data.contains(ConfigKeys.DIMENSIONS) && xCoord != -1 && yCoord != -1) {
                    String[] parsedData = parseDataField(data);
                    width = Integer.parseInt(parsedData[0]);
                    height = Integer.parseInt(parsedData[1]);
                }

                if(xCoord != -1 && yCoord != -1 && width != -1 && height != -1) {
                    Gdx.app.log("ConfigReader::mapSprites" , "creating region of dimensions: " + xCoord + " " + yCoord + " " + width + " " + height);
                    TextureRegion region = new TextureRegion(texture,xCoord, yCoord, width, height);
                    mapping.put(key,region);
                    xCoord =-1;
                    yCoord=-1;
                    width=-1;
                    height=-1;
                }
            }

        }catch( Exception e) {
            Gdx.app.log("ConfigReader::mapSprites", e.getMessage());
        }

        return mapping;
    }


    /**
     * Reads the cast list from a config file for a scene
     *
     * @return
     */
    public static ArrayDeque<String> readCastList() {

        ArrayDeque<String> characters = new ArrayDeque<>();
        boolean characterMode = false;
        String separator = Separators.KEYVALUE;

        try {
            File file = new File(Paths.TEST_CONFIG_PATH);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                int separatorIndex = data.indexOf(separator);
                String[] dataArray;

                String config = data.substring(0, separatorIndex);
                dataArray = data.substring(separatorIndex + 2).split(", ");
                Gdx.app.log("ConfigReader::readCastList", "value of the config: " + config);
                Gdx.app.log("ConfigReader::readCastList", "number of data points returned: " + dataArray.length);

                switch (config) {
                    case CHARACTER_MODE:
                        Gdx.app.log("ConfigReader::readCastList", "Setting character mode on");
                        characterMode = true;
                        break;
                }

                if (characterMode) {
                    Gdx.app.log("ConfigReader::readCastList", "In character mode adding characters: " + dataArray.length);
                    List<String> temp = Arrays.asList(dataArray);
                    characters = new ArrayDeque<>(temp);
                }
            }

        } catch (FileNotFoundException e) {
            Gdx.app.log("ConfigReader::readCastList", e.getMessage());
        }
        return characters;
    }

    private static String[] parseDataField(String data) {

        int separatorIndex = data.indexOf(Separators.KEYVALUE);
        String dataField = data.substring(separatorIndex);

        int dataSeparatorIndex = dataField.indexOf(Separators.DATAFIELDS);
        String field1 = dataField.substring(2,dataSeparatorIndex).replace(Separators.SPACE, Separators.EMPTY);
        String field2 = dataField.substring(dataSeparatorIndex+1).replace(Separators.SPACE, Separators.EMPTY);

        return new String[]{field1, field2};
    }

}
