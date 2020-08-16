package com.mygdx.game.novel1.ui.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.ui.buttons.ChoiceButton;
import com.mygdx.game.novel1.ui.buttons.LoadButton;
import com.mygdx.game.novel1.ui.buttons.MainMenuButton;
import com.mygdx.game.novel1.ui.textbox.Dialogue;


public class InGameUI extends BaseLayout{

    private String dialogue = "Hi rondadT";
    private Dialogue box;
    private TextureRegion textBoxImage;

    public InGameUI(Stage stage, NovelOne game) {
        super(stage, game);


    }

    @Override
    public void generateUI() {

        Texture texture = super.getAssets();
        Group miniButtons = new Group();
        Group choiceButtons = new Group();

        this.textBoxImage = new TextureRegion(texture, 0, 90, 1600, 300);
        this.box = new Dialogue(this.textBoxImage, dialogue);

        TextureRegion backImage = new TextureRegion(texture, 0, 0, 62, 20);
        TextureRegion historyImage = new TextureRegion(texture, 452, 0, 80, 26);
        TextureRegion loadIdle = new TextureRegion(texture, 186, 0, 62, 20);
        TextureRegion loadHover = new TextureRegion(texture, 124, 0, 62, 20);
        TextureRegion mainIdle = new TextureRegion(texture, 952, 0, 100, 26);
        TextureRegion mainHover = new TextureRegion(texture, 852, 0, 100, 26);
        TextureRegion saveImage = new TextureRegion(texture, 310, 0, 62, 20);
        TextureRegion settingsImage = new TextureRegion(texture, 772, 0, 80, 26);
        TextureRegion buttonIdle = new TextureRegion(texture, 900, 26, 900, 64);
        TextureRegion buttonHover = new TextureRegion(texture, 0, 26, 900, 64);


        //create the objects (buttons, textbox)
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
        choiceButtons.setY((Gdx.graphics.getHeight() / 2)- 100);
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

    public void nextLine(String line) {
        Gdx.app.log("In game" , "updating textbox dialog");
        this.dialogue = line;
        this.box.updateLine(line);
    }
}
