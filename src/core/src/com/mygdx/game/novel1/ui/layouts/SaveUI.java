package com.mygdx.game.novel1.ui.layouts;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.ui.buttons.BackButton;
import com.mygdx.game.novel1.ui.buttons.MainMenuButton;
import com.mygdx.game.novel1.ui.buttons.ReturnButton;
import com.mygdx.game.novel1.ui.buttons.SaveSlot;

public class SaveUI extends BaseLayout {

    private Save save;
    private Screen previous;

    public SaveUI(Stage stage, NovelOne game, Save save, Screen previous) {
        super(stage, game);
        this.save = save;
        this.previous = previous;

    }

    @Override
    public void generateUI() {
        Texture texture = super.getAssets();
        Group miniButtons = new Group();
        Group saveArea = new Group();

        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        TextureRegion backHover = new TextureRegion(texture, 0, 0, 62, 20);
        TextureRegion backIdle = new TextureRegion(texture, 62, 0, 62, 20);
        TextureRegion saveSlotIdle = new TextureRegion(texture, 1217,0,165, 91);
        TextureRegion saveSlotHover = new TextureRegion(texture, 1052, 0, 165, 91);

        SaveSlot saveSlot1 = new SaveSlot(saveSlotIdle, saveSlotHover, game, null);
        saveSlot1.spritePos(100, -5);
        SaveSlot saveSlot2 = new SaveSlot(saveSlotIdle, saveSlotHover, game, null);
        saveSlot2.spritePos(300, -5);
        SaveSlot saveSlot3 = new SaveSlot(saveSlotIdle, saveSlotHover, game, null);
        saveSlot3.spritePos(600, -5);
        ReturnButton returnButton = new ReturnButton(backIdle, backHover, game, null, this.previous);


        saveArea.setY(500);
        saveArea.setX(300);
        saveArea.addActor(saveSlot1);
        saveArea.addActor(saveSlot2);
        saveArea.addActor(saveSlot3);

        returnButton.spritePos(200, -5);
        miniButtons.setX(150);
        miniButtons.setY(15);

        miniButtons.addActor(returnButton);

        stage.addActor(saveArea);
        stage.addActor(miniButtons);

    }
}
