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

import static com.mygdx.game.novel1.constants.Paths.*;
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
    public static AssetsDTO getAllAssets(ArrayDeque<String> cast, ArrayDeque<String> backgroundList) {

        HashMap<String, Texture> characters = loadTextures(cast, CHARACTERS_PATH);
        HashMap<String, Texture> backgrounds = loadTextures(backgroundList, BACKGROUNDS_PATH);
        ArrayDeque<String> script = readScriptTextFile();

        return new AssetsDTO(script, characters, backgrounds);
    }


    /**
     * given a list of items, the method will load in each item in the provided directory
     *
     * @param list
     * @param assetsPath
     * @return
     */
    private static HashMap<String, Texture> loadTextures(ArrayDeque<String> list, String assetsPath){

        AssetManager manager = new AssetManager();
        HashMap<String, Texture> textures = new HashMap<String, Texture>();
        int limit = 10;
        int i = 0;
        Iterator listIterator = list.iterator();

        while (listIterator.hasNext() && i < limit) {
            String name = listIterator.next().toString();
            Gdx.app.log("AssetReader::loadTextures", "loading texture: " + name);
            manager.load(generateFileName(assetsPath, name, FileTypes.PNG), Texture.class);
            i++;
        }

        manager.finishLoading();

        while (!list.isEmpty()) {
            String name = list.pop();
            Texture texture = manager.get(generateFileName(assetsPath, name, FileTypes.PNG));
            textures.put(name, texture);
        }

        Gdx.app.log("AssetReader::loadTexture", "loaded " + textures.size() + " characters");
        return textures;

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
