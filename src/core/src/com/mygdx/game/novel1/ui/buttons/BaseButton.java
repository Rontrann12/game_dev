package com.mygdx.game.novel1.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.constants.FileTypes;
import com.mygdx.game.novel1.constants.Paths;
import com.mygdx.game.novel1.utils.AudioHandler;
import com.mygdx.game.novel1.utils.StringUtilities;


/**
 * Basic button class. defines all properties that all child button classes should have
 */
abstract class BaseButton extends Actor{

    public Sprite sprite;
    private final Sprite idle;
    private final Sprite hover;
    public final NovelOne game;
    private final BitmapFont font;
    private final String label;
    protected String buttonClick = "Click";

    public BaseButton(final TextureRegion idleButton, final TextureRegion hoverButton, final NovelOne game, final String name){
        this.idle = new Sprite(idleButton);
        this.hover = new Sprite(hoverButton);
        this.sprite = this.idle;

        spritePos(sprite.getX(), sprite.getY());
        setTouchable(Touchable.enabled);
        this.game = game;
        this.font = new BitmapFont();
        this.label = name;

        AssetManager manager = new AssetManager();

        String clickFilePath = StringUtilities.generateFileName(Paths.SFX_PATH, "click", FileTypes.AUDIO);
        manager.load(clickFilePath, Sound.class);
        manager.finishLoading();
        AudioHandler.addSound(buttonClick, (Sound) manager.get(clickFilePath));

        Gdx.app.log("Base Button", "in the base button constructor");

        addListener( new ClickListener() {
           @Override
           public void enter(InputEvent event,float x, float y, int pointer, Actor fromActor){
                Gdx.app.log(name, "Mouse pointer entered button detected");
                sprite = hover;
           }
           @Override
            public void exit(InputEvent event,float x, float y, int pointer, Actor fromActor) {
               Gdx.app.log(name, "Mouse pointer exited button detected");
               sprite = idle;
           }
        });

    }

    /**
     * Sets the position of the button
     * @param x x Coordinate
     * @param y y Coordinate
     */
    public void spritePos(float x, float y) {
        this.idle.setPosition(x,y);
        this.hover.setPosition(x,y);
        setBounds(this.idle.getX(), this.idle.getY(), this.idle.getWidth(), this.idle.getHeight());
        setBounds(this.hover.getX(), this.hover.getY(), this.hover.getWidth(), this.hover.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
        if(label != null) {
            font.draw(batch, this.label, textX(), textY());
        }

    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    private float textX() {
        return sprite.getX() + sprite.getWidth()/2;
    }

    private float textY() {
        return sprite.getY() + sprite.getHeight()/2;
    }

    abstract public void action(NovelOne game);
}
