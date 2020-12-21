package com.mygdx.game.novel1.ui.layouts;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.ui.buttons.BackButton;
import com.mygdx.game.novel1.ui.buttons.LoadButton;
import com.mygdx.game.novel1.ui.buttons.SaveButton;
import com.mygdx.game.novel1.ui.buttons.StartButton;

public class MainMenuUI extends BaseLayout {

    public MainMenuUI(Stage stage, NovelOne game) {
        super(stage, game);
    }

    @Override
    public void generateUI() {
        Texture texture = super.getAssets();
        Group menuGroup = new Group();
        multiplexer = new InputMultiplexer();

        TextureRegion idleTexture = new TextureRegion(texture, 900, 26, 900, 64);
        TextureRegion hoverTexture = new TextureRegion(texture, 0, 26, 900, 64);

        StartButton start = new StartButton(idleTexture, hoverTexture, game, "start button", super.stage);
        LoadButton load = new LoadButton(idleTexture, hoverTexture, game, "load button");
        SaveButton save = new SaveButton(idleTexture, hoverTexture, game, "save Button");

        menuGroup.setOrigin(0, 0);
        start.spritePos(0, 100);
        load.spritePos(0, 0);
        save.spritePos(0, 200);
        menuGroup.setX(Gdx.graphics.getWidth() / 3 - 100);
        menuGroup.setY(Gdx.graphics.getHeight() / 3);

        menuGroup.addActor(start);
        menuGroup.addActor(load);
        menuGroup.addActor(save);
        stage.addActor(menuGroup);

        multiplexer.addProcessor(stage);

    }
}
