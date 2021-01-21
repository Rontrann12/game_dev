package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.novel1.constants.ScriptCues;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.typ.SnapShot;
import com.mygdx.game.novel1.typ.SpeakerMap;

import java.util.*;

public class ScriptTracker {
    private ArrayList<SnapShot> scriptHistory;
    private ArrayDeque<String> script;
    private String currentMusic = null;
    private String currentSpeaker = null;
    private String currentSpeakerAlias = null;
    private int historyIndex;
    private String currentAction = null;
    private LinkedHashMap<String, String> activeCharacters;
    private String newScriptName = "";
    private String[] choices = null;

    public ScriptTracker() {
        this.scriptHistory = new ArrayList<>();
        this.historyIndex = -1;
        this.activeCharacters = new LinkedHashMap<>();
    }

    public ScriptTracker(ArrayList<SnapShot> snapShots) {
        this.scriptHistory = snapShots;
        this.historyIndex = scriptHistory.size();
        this.activeCharacters = new LinkedHashMap<>();

    }

    /*
     * TODO - to be removed
     *
     */
//    public ScriptTracker(ArrayDeque<String> script) {
//        this.scriptHistory = new ArrayList<>();
//        this.script = script;
//        this.historyIndex = -1;
//        this.activeCharacters = new LinkedHashMap<>();
//    }

    public void setScript(ArrayDeque<String> script) {
        this.script = script;
    }

    /**
     * Return the next line of dialogue
     *
     * @return
     */
    public SnapShot getNextLine() {

        SnapShot snap;
        if (historyIndex < scriptHistory.size() - 1) {
            historyIndex++;
            snap = scriptHistory.get(historyIndex);

        } else {
            snap = getNewLineFromScript();
            logScriptEvent(snap);
        }

        return snap;

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
        scriptHistory.add(copy);
        historyIndex++;
    }

    public SnapShot traceScriptBackwards() {
        historyIndex--;
        SnapShot snapshot = scriptHistory.get(historyIndex);
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
    public ArrayList<SnapShot> getHistory() {
        return this.scriptHistory;
    }

    public String getNewScriptName() {
        return this.newScriptName;
    }

    public String[] getChoices() {
        return this.choices;
    }

    public void handleScriptBranching(String selection) {
        this.choices = null;
        int limit = 100;
        int index = 0;
        boolean caseFound = false;

        while (index < limit && !caseFound) {
            String line = this.script.pop();
            if (line.contains(ScriptCues.CASE_CUE) && line.contains(selection)) {
                caseFound = true;
            }
            index++;
        }
    }

    /**
     * loads in a new line from the script
     *
     * @return
     */
    private SnapShot getNewLineFromScript() {

        SpeakerMap dialogue = null;
        String soundCue = null;

        while (dialogue == null && this.newScriptName.equals(Separators.EMPTY) && this.choices == null) {
            String scriptLine = this.script.pop();
            this.newScriptName = handleEndScriptCue(scriptLine);
            this.choices = handleChoicesCue(scriptLine);
            handleMusicChange(scriptLine);
            soundCue = handleSoundCue(scriptLine, soundCue);
            handleCharacterCue(scriptLine);
            dialogue = handleSpeechLine(scriptLine);

        }

        LinkedHashMap<String, String> deepCopy = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : activeCharacters.entrySet()) {
            deepCopy.put(entry.getKey(), entry.getValue());
        }

        return new SnapShot(dialogue, this.currentMusic, soundCue, this.currentSpeaker, deepCopy);
    }

    private String[] handleChoicesCue(String line) {
        if (line.contains(ScriptCues.CHOICES_CUE)) {
            int separatorIndex = line.indexOf('-');
            String dataSection = line.substring(separatorIndex + 2);
            String[] choices = dataSection.split(", ");
            return choices;
        }
        return null;
    }

    private void handleCharacterCue(String line) {

        if (line.contains(Separators.KEYVALUE)) {
            this.currentSpeaker = StringUtilities.getCharacterName(line);
            this.currentSpeakerAlias = StringUtilities.getCharacterAlias(line);
            this.currentAction = StringUtilities.getAction(line);

            try {
                if (currentAction.equals(ScriptCues.CHARACTER_EXIT)) {
                    Gdx.app.log("ScriptTracker::handleCharacterCue", "Character exited, removing " + currentSpeaker);
                    activeCharacters.remove(this.currentSpeaker);

                } else {
                    activeCharacters.put(this.currentSpeaker, this.currentAction);
                }
            } catch (NullPointerException e) {
                Gdx.app.log("ScriptTracker::handleCharacterCue", "No action found, no issue: " + e.getMessage());
            }
        }
    }


    private SpeakerMap handleSpeechLine(String line) {
        if (StringUtilities.isContainer(line, ScriptCues.DIALOGUE_CONTAINER, ScriptCues.DIALOGUE_CONTAINER)) {

            Gdx.app.log("ScriptTracker::handleSpeechLine", "current speaker is: " + currentSpeaker);

            //TODO - display thoughts better
            if (this.currentSpeaker.equals("Thought")) {

                return new SpeakerMap(null, StringUtilities.getContainedContent(line, ScriptCues.DIALOGUE_CONTAINER, ScriptCues.DIALOGUE_CONTAINER, true));

            } else if (this.currentSpeakerAlias != null) {

                Gdx.app.log("ScriptTracker::handleSpeechLine", "current speaker alias is: " + currentSpeakerAlias);
                return new SpeakerMap(this.currentSpeakerAlias, StringUtilities.getContainedContent(line, ScriptCues.DIALOGUE_CONTAINER, ScriptCues.DIALOGUE_CONTAINER, false));

            } else {

                return new SpeakerMap(this.currentSpeaker, StringUtilities.getContainedContent(line, ScriptCues.DIALOGUE_CONTAINER, ScriptCues.DIALOGUE_CONTAINER, false));
            }
        }
        return null;
    }

    private String handleSoundCue(String line, String soundCue) {

        if (StringUtilities.isContainer(line, ScriptCues.SFX_CONTAINER, ScriptCues.SFX_CONTAINER)) {
            soundCue = StringUtilities.getContainedContent(line, ScriptCues.SFX_CONTAINER, ScriptCues.SFX_CONTAINER, true);
        }

        return soundCue;
    }

    private String handleMusicChange(String line) {

        if (StringUtilities.isContainer(line, ScriptCues.BGM_CONTAINER, ScriptCues.BGM_CONTAINER)) {
            this.currentMusic = StringUtilities.getContainedContent(line, ScriptCues.BGM_CONTAINER, ScriptCues.BGM_CONTAINER, true);
        }

        return this.currentMusic;
    }

    private String handleEndScriptCue(String line) {

        if (StringUtilities.isContainer(line, ScriptCues.END_SCRIPT_CONTAINER, ScriptCues.END_SCRIPT_CONTAINER)) {
            return StringUtilities.getContainedContent(line, ScriptCues.END_SCRIPT_CONTAINER, ScriptCues.END_SCRIPT_CONTAINER, true);

        }
        return "";
    }

}

