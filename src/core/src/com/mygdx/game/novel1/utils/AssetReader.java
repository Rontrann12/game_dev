package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.dto.AssetsDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.mygdx.game.novel1.constants.Paths.CHARACTERS_PATH;
import static com.mygdx.game.novel1.utils.StringUtilities.generateFileName;


/**
 * Read the script and handles loading of character sprites and dialogue/monologue script
 */
public class AssetReader {

    private static String scriptsPath = Paths.TEST_SCRIPT_PATH;


    public AssetReader() {
        this.scriptsPath = Paths.TEST_SCRIPT_PATH;
    }

    /**
     * returns a DTO containing required images and text as specified in the script
     *
     * @return
     */
    public static AssetsDTO getAllAssets(ArrayDeque<String> cast) {

        HashMap<String, Texture> characters = loadCharacterTextures(cast);
        ArrayDeque<String> script = readScriptTextFile();

        return new AssetsDTO(script, characters);
    }

    /**
     * Given a texture containing all expressions for one character, method determines
     * all regions of each sprite and returns a hashmap to map the name and the sprite
     * TODO - DEPRECIATED
     * @return
     */
//    public HashMap<String, TextureRegion> getCharacterExpressions() {
//        HashMap<String, Texture> textures = loadCharacterTextures();
//        HashMap<String, TextureRegion> expressionMappings = new HashMap<String, TextureRegion>();
//        Iterator it = textures.entrySet().iterator();
//
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            Gdx.app.log("AssetReader::getCharacterExpressions", "Getting data from file: " + pair.getKey() + ".txt");
//            String path = generateFileName(CHARACTERS_PATH, pair.getKey().toString(), FileTypes.TEXT);
//
//            HashMap<String, TextureRegion> tmp = ConfigReader.mapSprites(path, (Texture) pair.getValue());
//            tmp.keySet().removeAll(expressionMappings.keySet());
//            expressionMappings.putAll(tmp);
//        }
//
//        Gdx.app.log("AssetReader::getCharacterExpressions", "total number of sprites for all characters: " + expressionMappings.size());
//        return expressionMappings;
//    }


    /**
     * Given a character name, method will search for the png file
     * and load the image in as a texture
     *
     * @return
     */
    public static HashMap<String, Texture> loadCharacterTextures(ArrayDeque<String> cast) {
        AssetManager manager = new AssetManager();
        HashMap<String, Texture> characterTextures = new HashMap<String, Texture>();
        int limit = 10;
        int i = 0;
        Iterator castIterator = cast.iterator();

        while (castIterator.hasNext() && i < limit) {
            String characterName = castIterator.next().toString();
            Gdx.app.log("AssetReader::loadCharacterTextures", "loading character: " + characterName);
            manager.load(generateFileName(CHARACTERS_PATH, characterName, FileTypes.PNG), Texture.class);
            i++;
        }

        manager.finishLoading();

        while (!cast.isEmpty()) {
            String characterName = cast.pop();
            Texture texture = manager.get(generateFileName(CHARACTERS_PATH, characterName, FileTypes.PNG));
            characterTextures.put(characterName, texture);
        }

        Gdx.app.log("AssetReader::loadCharacterTextures", "loaded " + characterTextures.size() + " characters");
        return characterTextures;
    }


    private static ArrayDeque<String> readScriptTextFile() {

        ArrayDeque<String> script = new ArrayDeque();

        try {
            File file = new File(scriptsPath);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                Gdx.app.log("AssetReader::readScriptTextFile", "reading new line: " + data);
                script.add(data);

            }
            return script;

        } catch (FileNotFoundException e) {
            Gdx.app.log("AssetReader::readScriptTextFile", e.getMessage());
        }
        return null;
    }
}
