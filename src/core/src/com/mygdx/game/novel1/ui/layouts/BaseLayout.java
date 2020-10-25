package com.mygdx.game.novel1.ui.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.novel1.NovelOne;
import com.mygdx.game.novel1.ui.buttons.BackButton;

abstract class BaseLayout {

    protected String path = null;
    protected Texture texture = null;
    protected InputMultiplexer multiplexer;
    protected NovelOne game;
    protected Stage stage;


    public BaseLayout(Stage stage, NovelOne game) {
        this.path = "img/ui/uicomponents.png";
        this.game = game;
        this.stage = stage;
    }

    public BaseLayout(String assetPath) {
        this.path = assetPath;
    }

    public BaseLayout(Texture texture) {
        this.texture = texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    protected Texture getAssets() {

        if (this.texture != null) {
            return this.texture;
        }

        Gdx.app.log(" base layout", "using asset manager");
        AssetManager manager = new AssetManager();
        manager.load(path, Texture.class);
        manager.finishLoading();

        return manager.get(path);
    }

    public InputMultiplexer getMultiplexer() {
        return this.multiplexer;
    }

    abstract public void generateUI();

}
