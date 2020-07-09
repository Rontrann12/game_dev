package com.mygdx.game.novel1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.novel1.constants.Constants;
import com.mygdx.game.novel1.flow.GameFlowHandler;


public class NovelOne extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	GameFlowHandler menu;
	Sprite sprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture(Constants.IMAGE_PATH+"menu/title_page.png");
		sprite = new Sprite(img);
		font = new BitmapFont();
		font.setColor(Color.RED);
		menu = new GameFlowHandler();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		font.draw(batch, "Hello World", 200, 200);
		batch.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height){

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
