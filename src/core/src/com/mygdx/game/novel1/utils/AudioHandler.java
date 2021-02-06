package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.novel1.constants.ConfigValues;
import com.mygdx.game.novel1.constants.ScriptCues;

import java.util.HashMap;

public class AudioHandler {
    private static float bgmVolume = ConfigValues.BGM_VOLUME;
    private static float sfxVolume = ConfigValues.SFX_VOLUME;
    private static Music currentMusic;
    private static HashMap<String, Sound> soundsList = new HashMap();
    private static HashMap<String, Music> musicList = new HashMap();


    /**
     * Will begin to track new sounds
     *
     * @param key   a String key to identify the sound
     * @param value the sound to be stored
     */
    public static void addSound(String key, Sound value) {
        soundsList.put(key, value);
    }

    /**
     * Will begin to track new sounds
     *
     * @param sounds add the current list of sounds
     */
    public static void addSound(HashMap<String, Sound> sounds) {
        soundsList.putAll(sounds);
    }

    /**
     * set the volume for all sounds
     *
     * @param volume
     */
    public static void setSoundVolume(float volume) {
        sfxVolume = volume;
    }

    /**
     * set a new bgm track
     *
     * @param track
     */
    public static void setCurrentTrack(String track) {
        Music music = musicList.get(track);
        Gdx.app.log("AudioHandler::setCurrentTrack", "checking if music is null:" + music);
        if (music != null && music != currentMusic) {
            if (currentMusic != null) {
                currentMusic.dispose();
            }
            currentMusic = music;
            playMusic();
        }
    }

    /**
     * Adds a variety of tracks
     *
     * @param music
     */
    public static void addMusic(HashMap<String, Music> music) {
        musicList.putAll(music);
    }

    /**
     * set the volume of the music
     *
     * @param volume
     */
    public static void setMusicVolume(float volume) {
        bgmVolume = volume;
        currentMusic.setVolume(volume);
    }

    /**
     * plays the specified sound
     *
     * @param key
     */
    public static void playSound(String key) {
        Gdx.app.log("AudioHandler::playSound", "playing sound: " + key);

        Sound sound = soundsList.get(key);
        if(sound != null) {
            sound.play(sfxVolume);
        }
    }

    /**
     * plays the music currently stored
     */
    public static void playMusic() {
        currentMusic.setVolume(bgmVolume);
        currentMusic.play();
    }

    /**
     * Stops the music
     */
    public static void stopMusic() {
        currentMusic.pause();
    }

    /**
     * disposes all sounds stored by the class
     */
    public static void clearSounds() {
        for(Sound sound : soundsList.values()) {
            sound.stop();
        }
        soundsList.clear();
    }

    /**
     * disposes all music stored by the class
     */
    public static void clearMusic() {
        stopMusic();
        musicList.clear();
    }


    /**
     * performs the actions being passed in as parameters. If the action being passed
     * in is the name of a track that had been loaded, it will play the track
     *
     * @param action
     */
    public static void handleMusicCommand(String action) {
        Gdx.app.log("AudioHandler", "new command entered " + action);
        if (action.equals(ScriptCues.PAUSE_MUSIC)) {
            stopMusic();
        } else if (action.equals(ScriptCues.RESUME_MUSIC)) {
            playMusic();
        } else {
            Gdx.app.log("AudioHandler::handleMusicCommand", "setting new track: " + action);
            setCurrentTrack(action);
        }
    }

}
