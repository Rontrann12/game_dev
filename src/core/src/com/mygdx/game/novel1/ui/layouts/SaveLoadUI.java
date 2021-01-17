package com.mygdx.game.novel1.ui.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.Load;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.ui.buttons.*;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.HashMap;

public class SaveLoadUI extends BaseLayout {

    private Load load;
    private Save save;
    private Screen previous;
    private ArrayQueue<String> fileList;
    private HashMap<String, String> parsedFileNames;
    public SaveLoadUI(Stage stage, NovelOne game, Save save , Screen previous) {
        super(stage, game);
        this.save = save;
        this.previous = previous;
        this.load = null;
    }

    public SaveLoadUI(Stage stage, NovelOne game, Load load, Screen previous) {
        super(stage, game);
        this.save = null;
        this.previous = previous;
        this.load = load;
    }

    public SaveLoadUI(Stage stage, NovelOne game, Load load, ArrayDeque<String> fileNameList, Screen previous) {
        super(stage, game);
        this.save = null;
        this.previous = previous;
        this.load = load;
        this.parsedFileNames = parseFileNames(fileNameList);
    }

    private HashMap<String, String> parseFileNames(ArrayDeque<String> fileNameList){
        String slotNum;
        String saveTime;
        HashMap<String, String> parsedFileNames = new HashMap<>();
        while (fileNameList.size() > 0) {
            String temp = fileNameList.pop();
            slotNum = String.valueOf(temp.charAt(0));
            saveTime = temp.substring(2);
            Gdx.app.log("SaveLoadUI::parseFileNames", "slotNum: " + slotNum + ", saveTime: " + saveTime);
            parsedFileNames.put(slotNum, saveTime);
        }

        return parsedFileNames;
    }

    @Override
    public void generateUI() {
        Texture texture = super.getAssets();
        Group miniButtons = new Group();
        Group uiArea = new Group();

        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        TextureRegion backHover = new TextureRegion(texture, 0, 0, 62, 20);
        TextureRegion backIdle = new TextureRegion(texture, 62, 0, 62, 20);
        TextureRegion slotIdle = new TextureRegion(texture, 1217, 0, 165, 91);
        TextureRegion slotHover = new TextureRegion(texture, 1052, 0, 165, 91);

        if(load == null){

            SaveSlot saveSlot1 = new SaveSlot(slotIdle, slotHover, game, this.parsedFileNames.get("1"));
            saveSlot1.spritePos(100, -5);
            SaveSlot saveSlot2 = new SaveSlot(slotIdle, slotHover, game, this.parsedFileNames.get("2"));
            saveSlot2.spritePos(300, -5);
            SaveSlot saveSlot3 = new SaveSlot(slotIdle, slotHover, game, this.parsedFileNames.get("3"));
            saveSlot3.spritePos(600, -5);
            uiArea.addActor(saveSlot1);
            uiArea.addActor(saveSlot2);
            uiArea.addActor(saveSlot3);
        }
        if(save == null) {
            LoadSlot loadSlot1 = new LoadSlot(slotIdle, slotHover, game, "null");
            loadSlot1.spritePos(100, -5);
            LoadSlot loadSlot2 = new LoadSlot(slotIdle, slotHover, game, this.parsedFileNames.get("2"));
            loadSlot2.spritePos(300, -5);
            LoadSlot loadSlot3 = new LoadSlot(slotIdle, slotHover, game, this.parsedFileNames.get("3"));
            loadSlot3.spritePos(600, -5);
            uiArea.addActor(loadSlot1);
            uiArea.addActor(loadSlot2);
            uiArea.addActor(loadSlot3);

        }


        ReturnButton returnButton = new ReturnButton(backIdle, backHover, game, null, this.previous);
        uiArea.setY(500);
        uiArea.setX(300);

        returnButton.spritePos(200, -5);
        miniButtons.setX(150);
        miniButtons.setY(15);

        miniButtons.addActor(returnButton);

        stage.addActor(uiArea);
        stage.addActor(miniButtons);

    }
}
