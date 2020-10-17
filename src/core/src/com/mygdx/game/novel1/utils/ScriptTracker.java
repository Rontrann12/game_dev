package com.mygdx.game.novel1.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.constants.StringWrappers;
import com.mygdx.game.novel1.typ.CharacterActionMap;
import com.mygdx.game.novel1.typ.SnapShot;

import java.util.ArrayDeque;
import java.util.EmptyStackException;

public class ScriptTracker {
    private ArrayDeque<SnapShot> scriptLogger;
    private ArrayDeque<String> script;
    private String currentMusic = null;

    public ScriptTracker(ArrayDeque<String> script) {
        this.scriptLogger = new ArrayDeque<>();
        this.script = script;
    }

    /**
     * Return the next line of dialogue
     *
     * TODO - how should this handle the actions of multiple characters on screen? (create a new class to map character to action)
     * TODO - how will the name of the speaker be displayed?
     * TODO - make sure that snap shots are being logged when needed (should break whenever new dialogue is received?)
     * @return
     */
    public SnapShot getNextLine() {
        SnapShot snapshot = new SnapShot();
        ArrayDeque<CharacterActionMap> actionRequests = new ArrayDeque<>();
        int limit = 10;
        for(int index = 0 ; index < limit; index ++) {
            Gdx.app.log("ScriptTracker::getNextLine", "popping new line from script");
            String line = this.script.pop();

            if(line.contains(Separators.KEYVALUE)) {
                actionRequests.push(new CharacterActionMap(StringUtilities.getCharacterName(line), StringUtilities.getAction(line)));
                Gdx.app.log("InGame::processScriptLine", "target Character: " + snapshot.getCharacter() + " action: " + snapshot.getAction());
            }
            else if(StringUtilities.isContainer(line, StringWrappers.BGM_CONTAINER)) {
                currentMusic = StringUtilities.getContainedContent(line, StringWrappers.BGM_CONTAINER);
            }
            else if(StringUtilities.isContainer(line, StringWrappers.SFX_CONTAINER)) {
                snapshot.setSound(StringUtilities.getContainedContent(line, StringWrappers.SFX_CONTAINER));
            }
            else if(StringUtilities.isContainer(line, StringWrappers.DIALOGUE_CONTAINER)) {
                snapshot.setDialogue(line);
                break;
            }
        }
        snapshot.setMusic(currentMusic);
        snapshot.setAction(actionRequests);

        logScriptEvent(snapshot);
        return snapshot;

    }

    /**
     * adds a new event (sounds, music played, and line) at a given point in the script
     */
    private void logScriptEvent(SnapShot snapshot) {
        Gdx.app.log("ScriptTracker::logScriptEvent", "line: " + snapshot.getDialogue() +
                ", music: " +snapshot.getBGMCommand() +
                ", sound: " + snapshot.getSound() +
                ", character: " + snapshot.getCharacter() +
                ", action: " + snapshot.getAction());

        scriptLogger.push(snapshot);
    }

    /**
     * returns the history taken from the logger
     */
    public void getHistory() {

    }

    private String processScriptLine() throws EmptyStackException {

        int limit = 10;
        for (int index = 0; index < limit; index++) {

            String line = this.script.pop();
            if (line.contains(Separators.KEYVALUE)) {
                String targetCharacter = StringUtilities.getCharacterName(line);
                String action = StringUtilities.getAction(line);
                //processAction(targetCharacter, action);
                Gdx.app.log("InGame::processScriptLine", "target Character: " + targetCharacter + " action: " + action);
            } else if (StringUtilities.isContainer(line, StringWrappers.DIALOGUE_CONTAINER)) {
                return line;
            } else if (StringUtilities.isContainer(line, StringWrappers.BGM_CONTAINER)) {
                String audioCommand = StringUtilities.getContainedContent(line, StringWrappers.BGM_CONTAINER);
                AudioHandler.handleMusicCommand(audioCommand);
            } else if (StringUtilities.isContainer(line, StringWrappers.SFX_CONTAINER)) {
                String sfxCue = StringUtilities.getContainedContent(line, StringWrappers.SFX_CONTAINER);
                AudioHandler.playSound(sfxCue);

            }
        }
        return null;
    }



}

