package com.packtpub.libgdx.canyonbunny.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.WorldController;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.packtpub.libgdx.canyonbunny.*;
import com.packtpub.libgdx.canyonbunny.game.objects.*;
import com.packtpub.libgdx.canyonbunny.util.*;
import com.packtpub.libgdx.canyonbunny.util.Assets;
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;
import com.badlogic.gdx.math.MathUtils;

/** @author: Tyler Major
 * pg 189-190 code
 * The added code creates a second camera that is specifically set up just to render
 * the game's GUI.
 * 
 * Tyler: Updated 9/20/22 Added code from pg222
 * Edited by Owen Burnham (Assignment 6)
 */

public class WorldRenderer implements Disposable 
{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	private OrthographicCamera cameraGUI;
	
	public WorldRenderer (WorldController worldController) 
	{ 
		this.worldController = worldController;
		init();
	}
	private void init () 
	{ 
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
		Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
				cameraGUI.position.set(0, 0, 0);
				cameraGUI.setToOrtho(true); // flip y-axis
				cameraGUI.update();
	}
	
	public void render () 
	{ 
		renderWorld(batch);	   //Tyler: Now calls render method of level to draw all game objects
		renderGui(batch);      //Tyler: pg193 renders the GUI
	}
		
	public void resize (int width, int height) 
	{ 
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) *
				width;
				camera.update();
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2,
		cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}
	
	
	@Override public void dispose () 
	{ 
		batch.dispose();
	}
	
	//Tyler Major: Added pg 185-186
	private void renderWorld (SpriteBatch batch) 
	{
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}
	
	/**
	 * Tyler: This code adds Gui score to top left of screen
	 * Edited by Philip Deppen (Assignment 7, p.284)
	 * The value in scoreVisual is cast to an integer value to cut off the fraction. 
	 * The resulting intermediate value will be the score that is shown in the GUI for the counting-up animation. 
	 * A sine function is used to let the coin shake.
	 */
	private void renderGuiScore (SpriteBatch batch) 
	{
		float x = -15;
		float y = -15;
		float offsetX = 50;
		float offsetY = 50;
		
		if (worldController.scoreVisual < worldController.score) 
		{
			long shakeAlpha = System.currentTimeMillis() % 360;
			float shakeDist = 1.5f;
			offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
			offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;
		}
		batch.draw(Assets.instance.goldCoin.goldCoin, x, y, offsetX, offsetY, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + (int)worldController.scoreVisual, x + 75, y + 37);
	}
	
	/**
	 * Tyler: This code adds three bunny heads to indicate the lives in the GUI
	 * Edited by Philip Deppen (Assignment 7, p.282)
	 * The added code will draw a temporary bunny head icon that is changed in its alpha color, scale, and rotation over time to create the animation.
	 */
	private void renderGuiExtraLive (SpriteBatch batch)
	{
		float x = cameraGUI.viewportWidth - 50 -
		Constants.LIVES_START * 50;
		float y = -15;
		
		for (int i = 0; i < Constants.LIVES_START; i++) 
		{
			if (worldController.lives <= i)
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			
			batch.draw(Assets.instance.bunny.head,
			x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
		}
		if (worldController.lives >= 0 && worldController.livesVisual > worldController.lives)
		{
			int i = worldController.lives;
			float alphaColor = Math.max(0, worldController.livesVisual - worldController.lives - 0.5f);
			float alphaScale = 0.35f * (2 + worldController.lives - worldController.livesVisual) * 2;
			float alphaRotate = -45 * alphaColor;
			batch.setColor(1.0f, 0.7f, 0.7f, alphaColor);
			batch.draw(Assets.instance.bunny.head, x + i * 50, y, 50, 50, 120, 100, alphaScale, -alphaScale, alphaRotate);
			batch.setColor(1, 1, 1, 1);
		}
	}
	
	//Tyler: pg192 added frame rate counter to GUI to display
	private void renderGuiFpsCounter (SpriteBatch batch)
	{
		float x = cameraGUI.viewportWidth - 55;
		float y = cameraGUI.viewportHeight - 15;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		if (fps >= 45) 
		{
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} 
		else if (fps >= 30)
		{
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} 
		else 
		{
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
		}
	
	/**
	 * Tyler: pg193 draws icons to GUI
	 * Edited by Owen Burnham (Assignment 6)
	 * @param batch
	 */
	
	private void renderGui (SpriteBatch batch) 
	{
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		
		// draw collected gold coins icon + text
		// (anchored to top left edge)
		renderGuiScore(batch);
		// Tyler major added this from page 225. It draws collected feather icon (anchored to top left edge) 
		renderGuiFeatherPowerup(batch);
		// draw extra lives icon + text (anchored to top right edge)
		renderGuiExtraLive(batch);
		// draw FPS text (anchored to bottom right edge)
		if (GamePreferences.instance.showFpsCounter)
			renderGuiFpsCounter(batch);
		renderGuiFpsCounter(batch);
		
		//Tyler added this from page 226. It draws game over text.
		renderGuiGameOverMessage(batch);
		batch.end();
	}
	
	//Tyler added this from page220. This function adds the text that says Game Over when lives run out
	private void renderGuiGameOverMessage (SpriteBatch batch) 
	{
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameOver()) 
		{
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "GAME OVER", x, y, 0, Align.center, false);		// need to fix alignment center
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}
	
	//Tyler added this code from page 224 in book.
	//This method first checks whether there is still time left for the feather power-up effect
	//to end. Only if this is the case, a feather icon is drawn in the top-left corner under the
	//gold coin icon. A small number is drawn next to it that displays the rounded time
	//that is still left until the effect vanishes.
	private void renderGuiFeatherPowerup (SpriteBatch batch) 
	{
		float x = -15;
		float y = 30;
		float timeLeftFeatherPowerup =
		worldController.level.bunnyHead.timeLeftFeatherPowerup;
		if (timeLeftFeatherPowerup > 0) 
		{
			// Start icon fade in/out if the left power-up time
			// is less than 4 seconds. The fade interval is set
			// to 5 changes per second.
			if (timeLeftFeatherPowerup < 4) 
			{
				if (((int)(timeLeftFeatherPowerup * 5) % 2) != 0) 
				{
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(Assets.instance.feather.feather,
					x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Assets.instance.fonts.defaultSmall.draw(batch,
			"" + (int)timeLeftFeatherPowerup, x + 60, y + 57);
		}
	}
	
}
