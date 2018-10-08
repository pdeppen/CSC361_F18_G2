package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.canyonbunny.screens.MenuScreen;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.canyonbunny.util.Assets;
import com.packtpub.libgdx.canyonbunny.util.AudioManager;
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;
/**
 * @author Owen Burnham 
 * Note this is is under branch p157_CanyonBunnyMain but also
 * does the job of p158_CanyonBunnyMain
 * Edited by Owen Burnham in Assigment 3
 * This class has the major credentials for the game
 * in order for it to run and update as needed
 * 
 * Redone by Owen Burnham in Assignment 6
 */
public class CanyonBunnyMain extends Game 
{
	
	/**
	 * edited by Owen Burnham (Assignment 6)
	 * This creates and initializes the controller and renderer
	 * Will also ensure the game world is active on start and that
	 * the assets are loaded in
	 * Additionally LibGDX is instructed through a call of the setScreen()
	 * method by the Game class to change the current screen.
	 * We pass a new instance of MenuScreen
	 * 
	 * edited on 10/7/2018 by Tyler Major (Assignment 8)
	 * These changes will make sure that after the assets and game preferences have
     * been loaded, the music starts playing.
	 */
	@Override public void create () 
	{
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// Load assets
		Assets.instance.init(new AssetManager());
		
		// Load preferences for audio settings and start playing music
		GamePreferences.instance.load();
		AudioManager.instance.play(Assets.instance.music.song01);
		
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}

}
