package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.novel1.constants.ScriptCues;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.constants.StringWrappers;
import com.mygdx.game.novel1.typ.SnapShot;
import com.mygdx.game.novel1.typ.SpeakerMap;

import java.util.*;

public class ScriptTracker {
    private ArrayList<SnapShot> scriptLogger;
    private ArrayDeque<String> script;
    private String currentMusic = null;
    private String currentSpeaker = null;
    private int historyIndex;
    private String currentAction = null;
    private LinkedHashMap<String, String> activeCharacters;

    public ScriptTracker(ArrayDeque<String> script) {
        this.scriptLogger = new ArrayList<>();
        this.script = script;
        this.historyIndex = -1;
        this.activeCharacters = new LinkedHashMap<>();
    }

    /**
     * Return the next line of dialogue
     * @return
     */
    public SnapShot getNextLine() {

        SnapShot snap;
        if (historyIndex < scriptLogger.size() - 1) {
            historyIndex++;
            snap = scriptLogger.get(historyIndex);

        } else {
            snap = getNewLineFromScript();
            logScriptEvent(snap);
        }

        return snap;

    }

    /**
     * loads in a new line from the script
     *
     * @return
     */
    private SnapShot getNewLineFromScript() {

        SpeakerMap dialogue = null;
        String soundCue = null;

        while (dialogue == null) {
            String scriptLine = this.script.pop();
            handleMusicChange(scriptLine);
            soundCue = handleSoundCue(scriptLine, soundCue);
            handleCharacterCue(scriptLine);
            dialogue = handleDialogue(scriptLine);
        }

        LinkedHashMap<String, String> deepCopy = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : activeCharacters.entrySet()) {
            deepCopy.put(entry.getKey(), entry.getValue());
        }

        return new SnapShot(dialogue, this.currentMusic, soundCue, this.currentSpeaker, deepCopy);
    }

    private void handleCharacterCue(String line) {

        if (line.contains(Separators.KEYVALUE)) {
            this.currentSpeaker = StringUtilities.getCharacterName(line);
            this.currentAction = StringUtilities.getAction(line);

            try {
                if (currentAction.equals(ScriptCues.CHARACTER_EXIT)) {
                    Gdx.app.log("ScriptTracker::handleCharacterCue", "Character exited, removing " + currentSpeaker);
                    activeCharacters.remove(this.currentSpeaker);

                } else {
                    activeCharacters.put(this.currentSpeaker,this.currentAction);
                }
            } catch (NullPointerException e) {
                Gdx.app.log("ScriptTracker::handleCharacterCue", "No action found, no issue: " + e.getMessage());
            }
        }
    }

    private SpeakerMap handleDialogue(String line) {
        if (StringUtilities.isContainer(line, StringWrappers.DIALOGUE_CONTAINER)) {
            return new SpeakerMap(this.currentSpeaker, StringUtilities.getContainedContent(line, StringWrappers.DIALOGUE_CONTAINER));
        }
        return null;
    }

    private String handleSoundCue(String line, String soundCue) {
        if (StringUtilities.isContainer(line, StringWrappers.SFX_CONTAINER)) {
            soundCue = StringUtilities.getContainedContent(line, StringWrappers.SFX_CONTAINER);
        }
        return soundCue;
    }

    private String handleMusicChange(String line) {
        if (StringUtilities.isContainer(line, StringWrappers.BGM_CONTAINER)) {
            this.currentMusic = StringUtilities.getContainedContent(line, StringWrappers.BGM_CONTAINER);
        }
        return this.currentMusic;
    }

    /**
     * adds a new event (sounds, music played, and line) at a given point in the script
     */
    public void logScriptEvent(SnapShot snapshot) {
        Gdx.app.log("ScriptTracker::logScriptEvent", "line: " + snapshot.getDialogue() +
                ", music: " + snapshot.getBGMCommand() +
                ", sound: " + snapshot.getSound() +
                ", character: " + snapshot.getCharacter() +
                ", action: " + snapshot.getAction().size());

        SnapShot copy = new SnapShot(snapshot);
        scriptLogger.add(copy);
        historyIndex++;
    }

    public SnapShot traceScriptBackwards() {
        historyIndex--;
        SnapShot snapshot = scriptLogger.get(historyIndex);
        Gdx.app.log("ScriptTracker::traceScriptBackwards", "line: " + snapshot.getDialogue() +
                ", music: " + snapshot.getBGMCommand() +
                ", sound: " + snapshot.getSound() +
                ", character: " + snapshot.getCharacter() +
                ", action: " + snapshot.getAction());

        return snapshot;
    }

    /**
     * returns the history taken from the logger
     */
    public void getHistory() {

    }
}

