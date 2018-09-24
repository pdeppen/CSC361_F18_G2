/**
 * Philip Deppen
 */
package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.packtpub.libgdx.canyonbunny.*;
import com.badlogic.gdx.math.Rectangle;
import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead;
import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead.JUMP_STATE;
import com.packtpub.libgdx.canyonbunny.game.objects.Feather;
import com.packtpub.libgdx.canyonbunny.game.objects.GoldCoin;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;

/** will need to create package for this */
/** testing */

public class WorldController extends InputAdapter
{
	public Level level;
	public int lives;
	public int score;
	
	private static final String TAG = 
		WorldController.class.getName();
	
	public CameraHelper cameraHelper;
	
	// Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	private float timeLeftGameOverDelay;
	
	
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 * handles collisions between the bunny and rock objects
	 * called when a collision is detected
	 */
	private void onCollisionBunnyHeadWithRock(Rock rock) {
		BunnyHead bunnyHead = level.bunnyHead;
		float heightDifference = Math.abs(bunnyHead.position.y - (  rock.position.y + rock.bounds.height));
		
		if (heightDifference > 0.25f) {
			boolean hitRightEdge = bunnyHead.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitRightEdge) {
				bunnyHead.position.x = rock.position.x + rock.bounds.width;
			}
			else {
				bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width;
			}
			return;
		}
		
		switch (bunnyHead.jumpState) {
			case GROUNDED:
				break;
			case FALLING:
			case JUMP_FALLING:
				bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height  + bunnyHead.origin.y;
				bunnyHead.jumpState = JUMP_STATE.GROUNDED;
				break;
			case JUMP_RISING:
				 bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
				break;
		}
	} 
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 * flags the gold coin as being collected so that it will disappear
	 */
	private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
		goldcoin.collected = true;
		score += goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	}
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 * flags the feather as being collected and also activates/refreshes the power-up effect
	 */
	private void onCollisionBunnyWithFeather(Feather feather) {
		feather.collected = true;
		score += feather.getScore();
		level.bunnyHead.setFeatherPowerup(true);
		Gdx.app.log(TAG, "Feather collected");
	}
	
	/**
	 * constructor 
	 */
	public WorldController() {
		init();
	}
	
	/**
	 * internal init (useful when reseting an object) 
	 */
	public void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		timeLeftGameOverDelay = 0;
		initLevel();
	}
	
	
	private Pixmap createProceduralPixmap (int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		// Draw a yellow-colored X shape on square
		pixmap.setColor(1, 1, 0, 1);
		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, width, height);
		return pixmap;
	}
	
	/**
	 * contains game logic
	 * called several hundred times per sec
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		if (isGameOver()) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) 
				init();
		} 
		else {
			handleInputGame(deltaTime);
		}
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);
		if (!isGameOver() && isPlayerInWater()) {
			lives--;
			if (isGameOver())
				timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			else
				initLevel();
		}
	}
	
	/**
	 * Made by Philip Deppen (Assignment 2)
	 * Edited by Philip Deppen (Assignment 5)
	 */
	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) 
			return;
				
		// Camera Controls (move)
		if (!cameraHelper.hasTarget(level.bunnyHead)) {
			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) 
				camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if (Gdx.input.isKeyPressed(Keys.LEFT))
				moveCamera(-camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
				moveCamera(camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.UP))
				moveCamera(0, camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.DOWN))
				moveCamera(0, -camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
				cameraHelper.setPosition(0, 0);
		}
		
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) 
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}
	
	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	
	/**
	 * Made by Philip Deppen (Assignment 2)
	 * Edited by Philip Deppen (Assignment 4, 5)
	 */
	@Override
	public boolean keyUp (int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		// Toggle camera follow
		else if (keycode == Keys.ENTER) {
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.bunnyHead);
		    Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
		}
		
		return false;
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * Edited by Philip Deppen (Assignment 5)
	 */
	private void initLevel() {
		score = 0;
		level = new Level (Constants.LEVEL_01);
		cameraHelper.setTarget(level.bunnyHead);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 * iterates through all the game objects and tests whether
	 * there is a collision between the bunny head and another game object
	 */
	private void testCollisions() {
		r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y,
			   level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
		
		// Test collision: Bunny Head <-> Rocks
	     for (Rock rock : level.rocks) {
	       r2.set(rock.position.x, rock.position.y, rock.bounds.width,
	    		      rock.bounds.height);
	       if (!r1.overlaps(r2)) continue;
       			onCollisionBunnyHeadWithRock(rock);
	       // IMPORTANT: must do all collisions for valid
	       // edge testing on rocks.
	     }
	     
	     // Test collision: Bunny Head <-> Gold Coins
	     for (GoldCoin goldcoin : level.goldcoins) {
	       if (goldcoin.collected) continue;
	       		r2.set(goldcoin.position.x, goldcoin.position.y,
	       			   goldcoin.bounds.width, goldcoin.bounds.height);
	       
	       if (!r1.overlaps(r2)) continue;
	       onCollisionBunnyWithGoldCoin(goldcoin);
	       break;
	     }
	     // Test collision: Bunny Head <-> Feathers
	     for (Feather feather : level.feathers) {
	       if (feather.collected) continue;
	       r2.set(feather.position.x, feather.position.y,
	    		   feather.bounds.width, feather.bounds.height);
	       
	       if (!r1.overlaps(r2)) continue;
	       onCollisionBunnyWithFeather(feather);
	       break;
	     }
	}
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 * allows player to be controllable with left and right arrow keys
	 */
	private void handleInputGame (float deltaTime) {
	   if (cameraHelper.hasTarget(level.bunnyHead)) {
		   // Player Movement
		   if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			   level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
		   } 
		   else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			   level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
		   } 
		   else {
			   // Execute auto-forward movement on non-desktop platform
			   if (Gdx.app.getType() != ApplicationType.Desktop) {
				   level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
			   }
		   }
		   // Bunny Jump
		   if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
	         level.bunnyHead.setJumping(true);
	       } else {
	         level.bunnyHead.setJumping(false);
	       }
	   }
   	}
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 */
	public boolean isGameOver() {
		return lives < 0;
	}
	
	/**
	 * Made by Philip Deppen (Assignment 5)
	 */
	public boolean isPlayerInWater() {
		return level.bunnyHead.position.y < -5;
	}

}