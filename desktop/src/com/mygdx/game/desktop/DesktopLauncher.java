/**
 * Group 2
 * edited by Owen Burnham in Assignment 3
 */
package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.packtpub.libgdx.canyonbunny.game.CanyonBunnyMain;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

/**
 * edited by Owen Burnham in Assignment 3
 * This is the main class for the CanyonBunny-Desktop project 
 */
public class DesktopLauncher
{
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = true;
	
	/**
	 * Edited by Owen Burnham in Assignment 3
	 * This is the main method that runs the project
	 * @param arg
	 */
	public static void main (String[] arg) 
	{
		//Tyler added from page 237 for new loading game screen
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images",
			"../CanyonBunny-android/assets/images",
			"canyonbunny.pack");
			TexturePacker.process(settings, "assets-raw/images-ui",
			"../CanyonBunny-android/assets/images",
			"canyonbunny-ui.pack");
			}
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CanyonBunny";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new CanyonBunnyMain(), config);
	}
}
