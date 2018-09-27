/**
 * Group 2
 * Made by Philip Deppen (Assignment 6)
 */
package com.packtpub.libgdx.canyonbunny.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen extends AbstractGameScreen {
	
	private static final String TAG = MenuScreen.class.getName();
	
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * @param game
	 */
	public MenuScreen (Game game) {
		super(game);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * constantly clears the screen by filling it with a solid black.
	 * also checks whether the screen has been touched.
	 * @param deltaTime
	 */
	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
		}
	}
	
	@Override 
	public void resize (int width, int height) { }
     
	@Override 
    public void show () { }
     
    @Override 
    public void hide () { }
     
    @Override 
    public void pause () { }
}
