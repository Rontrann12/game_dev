package com.mygdx.game.novel1.ui.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.screen.InGame;
import com.mygdx.game.novel1.typ.SpeakerMap;
import com.mygdx.game.novel1.ui.buttons.*;
import com.mygdx.game.novel1.ui.textbox.Dialogue;


public class InGameUI extends BaseLayout {

    private SpeakerMap speakerLine;
    private Dialogue box;
    private TextureRegion textBoxImage;
    private InGame screen;
    private TextureRegion buttonIdle;
    private TextureRegion buttonHover;
    private Group choiceButtons;

    public InGameUI(Stage stage, NovelOne game, SpeakerMap initialLine, InGame screen) {
        super(stage, game);
        Texture texture = super.getAssets();
        this.speakerLine = initialLine;
        this.screen = screen;
        this.buttonHover = new TextureRegion(texture, 0, 26, 900, 64);
        this.buttonIdle = new TextureRegion(texture, 900, 26, 900, 64);
        this.choiceButtons = null;
    }

    @Override
    /**
     * TODO - should transfer button handling to the screen class
     * TODO - should return a list of buttons to be tracked of by the screen
     */
    public void generateUI() {

        Texture texture = super.getAssets();
        Group miniButtons = new Group();


        this.textBoxImage = new TextureRegion(texture, 0, 90, 1600, 300);
        this.box = new Dialogue(this.textBoxImage, speakerLine.getCharacter(), speakerLine.getLine());

        TextureRegion backHover = new TextureRegion(texture, 0, 0, 62, 20);
        TextureRegion backIdle = new TextureRegion(texture, 62, 0, 62, 20);
        TextureRegion historyImage = new TextureRegion(texture, 452, 0, 80, 26);
        TextureRegion loadIdle = new TextureRegion(texture, 186, 0, 62, 20);
        TextureRegion loadHover = new TextureRegion(texture, 124, 0, 62, 20);
        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        TextureRegion saveIdle = new TextureRegion(texture, 310, 0, 62, 20);
        TextureRegion saveHover = new TextureRegion(texture, 248, 0, 62, 20);
        TextureRegion settingsImage = new TextureRegion(texture, 772, 0, 80, 26);

        //create the objects (buttons, textbox)
        box.setAlpha((float) 0.8);
        LoadButton loadButton = new LoadButton(loadIdle, loadHover, game, null);
        MainMenuButton mainButton = new MainMenuButton(mainIdle, mainHover, game, null);
        SaveButton saveButton = new SaveButton(saveIdle, saveHover, game, null);
        BackButton backButton = new BackButton(backIdle, backHover, game, null, screen);
        mainButton.spritePos(100, -5);
        backButton.spritePos(250, -5);
        saveButton.spritePos(350, -5);
        miniButtons.setX(150);
        miniButtons.setY(15);


        miniButtons.addActor(backButton);
        miniButtons.addActor(loadButton);
        miniButtons.addActor(mainButton);
        miniButtons.addActor(saveButton);

        //draw buttons
        stage.addActor(box);
        stage.addActor(miniButtons);

    }

    public void presentChoices(String[] choices) {
        this.choiceButtons = new Group();

        int diff = 0;
        for (String option : choices) {
            ChoiceButton temp = new ChoiceButton(buttonIdle, buttonHover, game, option, screen);
            temp.spritePos(0, diff);
            diff = diff + 80;
            choiceButtons.addActor(temp);
        }
        choiceButtons.setX(400);
        choiceButtons.setY((Gdx.graphics.getHeight() / 2) - 50);

        stage.addActor(choiceButtons);
    }

    public void removeChoices(){
        if(this.choiceButtons != null) {
            this.choiceButtons.remove();
        }
    }
    public void nextLine(SpeakerMap speaker) {
        Gdx.app.log("InGameUI::nextLine", "updating textbox dialog");
        this.speakerLine = speaker;
        try {
            this.box.updateLine(speakerLine.getCharacter(), speakerLine.getLine());
        } catch (NullPointerException e) {
            Gdx.app.log("InGameUI::nextLine", e.getMessage());
        }

    }
}
