package com.mygdx.game.novel1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.novel1.NovelOne;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Hubby Simulator";
		config.useGL30 = true;
		config.height = 900;
		config.width = 1600;
		new LwjglApplication(new NovelOne(), config);
	}
}
