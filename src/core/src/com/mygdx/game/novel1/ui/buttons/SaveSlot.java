package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.screen.Save;
import com.mygdx.game.novel1.utils.AudioHandler;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveSlot extends BaseButton {

    private String saveDate;
    private String id;

    public SaveSlot(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, String name, String id) {
        super(idleTexture, hoverTexture, game, name);
        this.saveDate = null;
        this.id = id;
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioHandler.playSound(buttonClick);
                action(game);
                return true;
            }
        });
    }

    @Override
    public void action(NovelOne game) {
        Gdx.app.log("SaveSlot::action", "Running save slot action");
        try {
            Save save = (Save) game.getScreen();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            this.saveDate = formatter.format(date);
            this.label = this.saveDate;
            String fileName = StringUtilities.appendPrefix(
                    this.id,
                    StringUtilities.getCompactDateFormat(this.saveDate));

            save.saveState(fileName);


        } catch (ClassCastException e) {
            Gdx.app.log("SaveSlot::action", e.getMessage());
        } catch (ParseException e) {
            Gdx.app.log("SaveSlot::action", e.getMessage());
        }

    }

}
