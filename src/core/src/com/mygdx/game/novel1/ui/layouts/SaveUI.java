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

        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        TextureRegion backHover = new TextureRegion(texture, 0, 0, 62, 20);
        TextureRegion backIdle = new TextureRegion(texture, 62, 0, 62, 20);

        SaveSlot saveslot1 = new SaveSlot(mainIdle, mainHover, game, null);
        ReturnButton returnButton = new ReturnButton(backIdle, backHover, game, null, this.previous);

        returnButton.spritePos(200, -5);
        saveslot1.spritePos(100, -5);
        miniButtons.setX(150);
        miniButtons.setY(15);

        miniButtons.addActor(returnButton);
        miniButtons.addActor(saveslot1);

        stage.addActor(miniButtons);

    }
}
