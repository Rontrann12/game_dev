package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.constants.Separators;
import com.mygdx.game.novel1.screen.InGame;
import com.mygdx.game.novel1.screen.Load;
import com.mygdx.game.novel1.utils.AudioHandler;
import com.mygdx.game.novel1.utils.StringUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadSlot extends BaseButton {

    private String loadDate;
    private String id;

    public LoadSlot(TextureRegion idleTexture, TextureRegion hoverTexture, final NovelOne game, String name, String id) {
        super(idleTexture, hoverTexture, game, name);
        this.loadDate = null;
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
        try {
            String fileName = StringUtilities.generateFileName(
                    Gdx.files.getLocalStoragePath() + Paths.SAVE_PATH,
                    StringUtilities.appendPrefix(
                            this.id,
                            StringUtilities.getCompactDateFormat(super.label)),
                    FileTypes.SAVE);

            Load load = (Load) game.getScreen();
            game.setScreen(new InGame(game, load.retrieveSavedData(fileName)));
        } catch (ParseException e) {
            Gdx.app.log("LoadSlot::action", e.getMessage());
        } catch (ClassCastException e) {
            Gdx.app.log("LoadSlot::action", e.getMessage());
        }

    }
}
