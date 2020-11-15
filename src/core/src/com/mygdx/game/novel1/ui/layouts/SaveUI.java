package com.mygdx.game.novel1.ui.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.ui.buttons.MainMenuButton;
import com.mygdx.game.novel1.ui.buttons.ReturnButton;

public class SaveUI extends BaseLayout{

    private Save save;
    public SaveUI(Stage stage, NovelOne game, Save save) {
        super(stage, game);
        this.save = save;

    }

    @Override
    public void generateUI() {
        Texture texture = super.getAssets();
        Group miniButtons = new Group();

        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        MainMenuButton returnButton = new MainMenuButton(mainIdle, mainHover, game, null);

        returnButton.spritePos(100,-5);
        miniButtons.setX(150);
        miniButtons.setY(15);


        miniButtons.addActor(returnButton);

        stage.addActor(miniButtons);

    }
}
