package com.mygdx.game.novel1.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.LayoutOptions;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.ui.buttons.ChoiceButton;
import com.mygdx.game.novel1.ui.buttons.LoadButton;
import com.mygdx.game.novel1.ui.buttons.MainMenuButton;
import com.mygdx.game.novel1.ui.buttons.StartButton;
import com.mygdx.game.novel1.ui.textbox.Dialogue;

import java.awt.*;

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
     * <p>
     * TODO - Will need to implement the UI for in game
     * <p>
     * TODO - Create textbox image, tool bar, mini buttons
     *
     * @param inputStage UI components will be drawn on to the stage
     * @param game       Some components may change need to change the state of the game
     * @param layoutType Determines the layout style
     */
    public void generateUI(Stage inputStage, NovelOne game, int layoutType) {
        // determine the layout type
        switch (layoutType) {
            case LayoutOptions.MENU:
                menuLayout(inputStage, game);
                break;

            case LayoutOptions.IN_GAME:
                inGameLayout(inputStage, game);
                break;

        }


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

        TextureRegion idleTexture = new TextureRegion(texture, 900, 26, 900, 64);
        TextureRegion hoverTexture = new TextureRegion(texture, 0, 26, 900, 64);

        StartButton start = new StartButton(idleTexture, hoverTexture, game, "start button");
        LoadButton load = new LoadButton(idleTexture, hoverTexture, game, "load button");

        menuGroup.setOrigin(0, 0);
        start.spritePos(0, 100);
        load.spritePos(0, 0);
        menuGroup.setX(Gdx.graphics.getWidth() / 3 - 100);
        menuGroup.setY(Gdx.graphics.getHeight() / 3);

        menuGroup.addActor(start);
        menuGroup.addActor(load);
        stage.addActor(menuGroup);
    }


    /**
     * This layout has to be more dynamic than the menu layout.
     * How can I continuously update the textbox
     * How can I have buttons appear and disappear
     * <p>
     * Can I think of this using microservices
     * Script reader
     * input handler
     * state updater (for point system)
     * UI
     * manager (a class that tells other classes how to act)
     * <p>
     * For now just worry about displaying the layout
     * later I will think about how to display the text in this class
     * which would probably involve a variable that can be
     * accessed and modified by an external class for text
     * <p>
     * Also need to think about how buttons will be labeled?
     * method call by an external class?
     * how can I allow the number of choices to be abstract?
     * <p>
     * TODO - may want to create a class that returns an array of texture
     * regions simply just reading in the whole texture and the txt file
     */
    private void inGameLayout(Stage stage, NovelOne game) {

        //get the UI components
        Group miniButtons = new Group();
        Group choiceButtons = new Group();
        texture = getAssets();

        TextureRegion textboxImage = new TextureRegion(texture, 0, 90, 1600, 300);
        TextureRegion backImage = new TextureRegion(texture, 0, 0, 62, 20);
        TextureRegion historyImage = new TextureRegion(texture, 452, 0, 80, 26);
        TextureRegion loadIdle = new TextureRegion(texture, 186, 0, 62, 20);
        TextureRegion loadHover = new TextureRegion(texture, 124, 0, 62, 20);
        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        TextureRegion saveImage = new TextureRegion(texture, 310, 0, 62, 20);
        TextureRegion settingsImage = new TextureRegion(texture, 772, 0, 80, 26);
        TextureRegion buttonIdle = new TextureRegion(texture, 900,26, 900, 64);
        TextureRegion buttonHover = new TextureRegion(texture, 0, 26, 900, 64);


        //create the objects (buttons, textbox)
        Dialogue box = new Dialogue(textboxImage, "hello RondadT");
        box.setAlpha((float) 0.8);
        LoadButton loadButton = new LoadButton(loadIdle, loadHover, game, null);
        MainMenuButton mainButton = new MainMenuButton(mainIdle, mainHover, game, null);
        ChoiceButton choice1 = new ChoiceButton(buttonIdle, buttonHover, game, "Yes Baby!");
        ChoiceButton choice2 = new ChoiceButton(buttonIdle, buttonHover, game, "No Hun");

        mainButton.spritePos(100, -5);
        miniButtons.setX(150);
        miniButtons.setY(15);

        choice1.spritePos(0, 80);
        choiceButtons.setX(400);
        choiceButtons.setY(Gdx.graphics.getHeight() /2 );
        choiceButtons.addActor(choice1);
        choiceButtons.addActor(choice2);

        //draw option icons in the textbox
        miniButtons.addActor(loadButton);
        miniButtons.addActor(mainButton);


        //draw buttons
        stage.addActor(box);
        stage.addActor(miniButtons);
        stage.addActor(choiceButtons);



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
            path = "img/ui/uicomponents.png";
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
