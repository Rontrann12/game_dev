package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class AudioHandler {
    private static float bgmVolume = 0.5f;
    private static float sfxVolume = 0.5f;
    private static Music music;
    private static HashMap<String, Sound> sounds;


    /**
     * Will begin to track new sounds
     *
     * @param key   a String key to identify the sound
     * @param value the sound to be stored
     */
    public static void addSound(String key, Sound value) {
        sounds.put(key, value);
    }

    /**
     * Will begin to track new sounds
     *
     * @param sounds    add the current list of sounds
     */
    public static void addSound(HashMap<String, Sound> sounds) {

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
     * @param track
     */
    public static void setMusicTrack(Music track) {
        music.dispose();
        music = track;
    }

    /**
     * set the volume of the music
     *
     * @param volume
     */
    public static void setMusicVolume(float volume) {
        bgmVolume = volume;
        music.setVolume(volume);
    }

    /**
     * plays the specified sound
     * @param key
     */
    public static void playSound(String key) {
        sounds.get(key).play(sfxVolume);
    }

    /**
     * plays the music currently stored
     */
    public static void playMusic() {
        music.play();
    }

    /**
     * Stops the music
     */
    public static void stopMusic() {
        music.pause();
    }

    /**
     * disposes all sounds stored by the class
     */
    public static void clearSounds() {

    }

}
