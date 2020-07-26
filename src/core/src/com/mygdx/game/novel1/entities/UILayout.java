package com.mygdx.game.novel1.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.LayoutOptions;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.entities.buttons.LoadButton;
import com.mygdx.game.novel1.entities.buttons.StartButton;

public class UILayout {

    private Texture uiTexture;
    private AssetManager assets;
    private Group group;
    private String path = null;
    private Texture texture = null;

    public UILayout() {


    }

    public UILayout(String assetPath) {
        path = assetPath;
    }

    public UILayout(Texture componentTextures) {
        texture = componentTextures;
    }

    /**
     * sets up the ui on the stage.
     *
     * @param inputStage    UI components will be drawn on to the stage
     * @param game          Some components may change need to change the state of the game
     * @param layoutType    Determines the layout style
     */
    public void generateUI(Stage inputStage, NovelOne game, int layoutType) {
        // determine the layout type
        switch (layoutType) {
            case LayoutOptions.MENU:
                menuLayout(inputStage, game);
                break;

            case LayoutOptions.IN_GAME:
                inGameLayout();
                break;

        }

        //get the textures

        // create sprites

        //apply sprites to any entity needed for the ui

        //add the component to a group

        //add the group to the stage

    }


    public void setPath(String assetPath) {
        path = assetPath;
    }

    public void setTexture(Texture assetTexture) {
        texture = assetTexture;
    }

    private void menuLayout(Stage stage, NovelOne game) {
        texture = getAssets();
        Group menuGroup = new Group();

        TextureRegion idleTexture = new TextureRegion(texture, 256, 0, 256, 64);
        TextureRegion hoverTexture = new TextureRegion(texture, 0, 0, 256, 64);

        StartButton start = new StartButton(idleTexture, hoverTexture, game, "start button");
        LoadButton load = new LoadButton(idleTexture, hoverTexture, game, "load button");

        menuGroup.setOrigin(0, 0);
        start.spritePos(0, 100);
        load.spritePos(0, 0);
        menuGroup.setX(Gdx.graphics.getWidth() / 3);
        menuGroup.setY(Gdx.graphics.getHeight() / 3);

        menuGroup.addActor(start);
        menuGroup.addActor(load);
        stage.addActor(menuGroup);
    }


    private void inGameLayout() {

    }

    private void loadLayout() {

    }

    private void settingsLayout() {

    }

    private void menuInGameLayout() {

    }

    private void loggerLayout() {

    }

    private Texture getAssets() {
        if (path == null) {
            path = "img/menu/menu_better.png";
        } else if (texture == null) {
            return texture;
        }

        AssetManager manager = new AssetManager();
        manager.load(path, Texture.class);
        manager.finishLoading();

        return manager.get(path);
    }

    private void generateOptions(String[] options) {
        //assign possible options to buttons
    }


    private void loadUI() {
        assets.load(Paths.IMAGE_PATH + "ui.png", Texture.class);

        assets.finishLoading();
        uiTexture = assets.get(Paths.IMAGE_PATH + "ui.png");
    }
}
