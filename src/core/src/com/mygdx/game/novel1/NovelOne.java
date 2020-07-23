package com.mygdx.game.novel1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.novel1.flow.MainMenu;

// Can use the Screen interface to implement difference screens for menu or game screens
// helpful example: https://gist.github.com/sinistersnare/6367829
/*
NOTES: for VN, camera is probably the same as window dimensions

implement menu as stage instead of using sprite batch on it own
 */


public class NovelOne extends Game {
	public SpriteBatch batch;
	public Texture img;
	public BitmapFont font;
	public Viewport viewport;

	public void create () {

        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font = new BitmapFont();
		this.setScreen(new MainMenu(this));

	}

	public void render () {
	super.render();

	}

	public void dispose () {
		batch.dispose();
		img.dispose();
		font.dispose();
	}

}
