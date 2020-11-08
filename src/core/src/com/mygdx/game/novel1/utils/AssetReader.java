package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.typ.AssetsDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.mygdx.game.novel1.constants.Paths.*;
import static com.mygdx.game.novel1.utils.StringUtilities.generateFileName;


/**
 * Read the script and handles loading of character sprites and dialogue/monologue script
 */
public class AssetReader {


    /**
     * returns a DTO containing required images and text as specified in the script
     *
     * @return All requested assets packaged in an object
     */
    public static AssetsDTO getAllAssets(String scriptsPath, ArrayDeque<String> cast, String backgroundList, ArrayDeque<String> musicList, ArrayDeque<String> sfxList) {

        HashMap<String, Texture> characters = loadTextures(cast, CHARACTERS_PATH);
        Texture backgrounds = loadTextures(backgroundList, BACKGROUNDS_PATH);
        HashMap<String, Music> bgm = loadMusic(musicList);
        ArrayDeque<String> script = readScriptTextFile(scriptsPath);
        HashMap<String, Sound> sounds = loadSounds(sfxList);

        return new AssetsDTO(script, characters, backgrounds, bgm, sounds);
    }

    /**
     * Given a list of items, method will load in each item in the provided directory
     * <p>
     * TODO - remove the infinite loop protector
     *
     * @param list the list of items to load
     * @return a hashmap containing the assets
     */
    private static HashMap<String, Sound> loadSounds(ArrayDeque<String> list) {

        HashMap<String, Sound> allSounds = new HashMap<>();
        AssetManager manager = new AssetManager();
        int limit = 10;
        int i = 0;
        Iterator iterator = list.iterator();

        while (iterator.hasNext() && i < limit) {
            String name = iterator.next().toString();

            Gdx.app.log("AssetReader::loadSound", "loading sfx: " + name);
            manager.load(generateFileName(SFX_PATH, name, FileTypes.AUDIO), Sound.class);
            i++;
        }
        manager.finishLoading();

        while (!list.isEmpty()) {
            String name = list.pop();
            Sound sound = manager.get(generateFileName(SFX_PATH, name, FileTypes.AUDIO), Sound.class);
            allSounds.put(name, sound);
        }

        Gdx.app.log("AssetReader::loadSound", "loaded " + allSounds.size() + " tracks");
        return allSounds;

    }

    /**
     * given a list of items, the method will load in each item in the provided directory
     * <p>
     * TODO - remove the infinite loop protector
     *
     * @param list a list of assets to be loaded
     * @return a hashmap containing the assets
     */
    private static HashMap<String, Music> loadMusic(ArrayDeque<String> list) {
        HashMap<String, Music> allMusic = new HashMap<>();
        AssetManager manager = new AssetManager();
        int limit = 10;
        int i = 0;
        Iterator iterator = list.iterator();

        while (iterator.hasNext() && i < limit) {
            String name = iterator.next().toString();

            Gdx.app.log("AssetReader::loadMusic", "loading track: " + name);
            manager.load(generateFileName(BGM_PATH, name, FileTypes.AUDIO), Music.class);
            i++;
        }
        manager.finishLoading();

        while (!list.isEmpty()) {
            String name = list.pop();
            Music music = manager.get(generateFileName(BGM_PATH, name, FileTypes.AUDIO), Music.class);
            allMusic.put(name, music);
        }

        Gdx.app.log("AssetReader::loadMusic", "loaded " + allMusic.size() + " tracks");
        return allMusic;

    }


    /**
     * given a list of items, the method will load in each item in the provided directory
     *
     * @param list       a list of items to be loaded
     * @param assetsPath the directory path to the assets
     * @return a hashmao containing all of the assets
     */
    private static HashMap<String, Texture> loadTextures(ArrayDeque<String> list, String assetsPath) {

        AssetManager manager = new AssetManager();
        HashMap<String, Texture> textures = new HashMap<>();
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

    /**
     * loads in the item specified. For single item
     *
     * @param name
     * @param assetsPath
     * @return
     */
    public static Texture loadTextures(String name, String assetsPath) {
        AssetManager manager = new AssetManager();
        manager.load(generateFileName(assetsPath, name, FileTypes.PNG), Texture.class);
        manager.finishLoading();
        return manager.get(generateFileName(assetsPath, name, FileTypes.PNG));
    }

    private static ArrayDeque<String> readScriptTextFile(String path) {

        ArrayDeque<String> script = new ArrayDeque();

        try {
            File file = new File(path);
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
