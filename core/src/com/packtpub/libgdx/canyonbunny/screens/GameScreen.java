/** Tyler Major
 * 9/20/2018
 * pg 230-231-232
 * Edited by Owen Burnham (Assignment 6)
 * Edited by Owen Burnham (Assignment 9)
 *  The code that was in the create() and dispose() methods of
	CanyonBunnyMain have been moved over to the show() and hide() methods,
	respectively, in order to accommodate the Screen interface. Furthermore, catching
	Android's back key will be enabled when the game screen is shown and disabled
	again when the screen is hidden.
 */
package com.packtpub.libgdx.canyonbunny.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.WorldController;
import com.packtpub.libgdx.canyonbunny.game.WorldRenderer;
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;


public class GameScreen extends AbstractGameScreen
{
	
	 
	
		private static final String TAG = GameScreen.class.getName();
		private WorldController worldController;
		private WorldRenderer worldRenderer;
		private boolean paused;
		public GameScreen (Game game) 
		{
			super(game);
	    }
	
	@Override
	public void render(float deltaTime) 
	{
		// Do not update game world when paused.
		if (!paused) 
		{
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(deltaTime);
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed /
		255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	}
	
	@Override
	public void resize(int width, int height) 
	{
		worldRenderer.resize(width, height);
	}
	
	@Override
	/**
	 * Edited by Owen Burnham (Assignment 6)
	 * Ensures that the game screen will always work with the
	 * latest game settings
	 */
	public void show() 
	{
		GamePreferences.instance.load();
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	/**
	 * Edited by Owen Burnham (Assignment 9)
	 */
	public void hide() 
	{
		worldController.dispose();
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public void pause()
	{
		paused = true;
	}
	
	@Override
	public void resume()
	{
		super.resume();
		// Only called on Android!
		paused = false;
	}
		
  }
	

	
	
