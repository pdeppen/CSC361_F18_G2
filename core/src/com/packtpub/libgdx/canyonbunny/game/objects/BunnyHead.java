package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.objects.*;
import com.packtpub.libgdx.canyonbunny.util.Assets;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import com.packtpub.libgdx.canyonbunny.util.CharacterSkin;
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;

/**
 * @author Owen Burnham (Assignment 5)
 * edited by Owen Burnham (Assignment 6)
 * edited by Owen Burnham (Assignment 7)
 * This class is for the bunny head object.
 * It is the player's character and consists of only
 * one image, but has to account for jumping, falling,
 * and picking up objects
 */
public class BunnyHead extends AbstractGameObject
{
	public static final String TAG = BunnyHead.class.getName();
	
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = 
			JUMP_TIME_MAX - 0.018f;
	
	public ParticleEffect dustParticles = new ParticleEffect();
	
	// different directions views
	public enum VIEW_DIRECTION { LEFT, RIGHT }
	
	// different jump states
	public enum JUMP_STATE 
	{
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	private TextureRegion regHead;
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasFeatherPowerup;
	public float timeLeftFeatherPowerup;
	
	/**
	 * initializes the bunny head object
	 */
	public BunnyHead () 
	{
		init();
	}
	
	/**
	 * Edited by Owen Burnham (Assignment 7)
	 * Initializes the bunny head game object by setting
	 * its physics values, a starting view direction, and 
	 * jump state.  Also deactivates the feather power-up 
	 * effect.
	 */
	public void init () 
	{
		dimension.set(1, 1);
		regHead = Assets.instance.bunny.head;
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		// Set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		// Jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		// Power-ups
		hasFeatherPowerup = false;
		timeLeftFeatherPowerup = 0;
		
		// Particles
		dustParticles.load(Gdx.files.internal("particles/dustParticle.pfx"),
				Gdx.files.internal("particles"));
	}
	
	/**
	 * Allows us to make the bunny jump.
	 * The state handling in the code will decide whether
	 * jumping is currently possible and whether it is a 
	 * single or a multi jump.
	 * @param jumpKeyPressed
	 */
	public void setJumping (boolean jumpKeyPressed) 
	{
		switch (jumpState)	
		{
		case GROUNDED: // Character is standind on a platform
			if (jumpKeyPressed)
			{
				// Start counting jump time from the beginning
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		case JUMP_RISING: // Rising in the air
			if (!jumpKeyPressed)
				jumpState = JUMP_STATE.JUMP_FALLING;
			break;
		case FALLING: // Falling down
		case JUMP_FALLING: // Falling down after jump
			if (jumpKeyPressed && hasFeatherPowerup)
			{
				timeJumping = JUMP_TIME_OFFSET_FLYING;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		}
	}
	
	/**
	 * Allows us to toggle the feather power-up effect
	 * @param pickedUp
	 */
	public void setFeatherPowerup (boolean pickedUp) 
	{
		hasFeatherPowerup = pickedUp;
		if (pickedUp)
		{
			timeLeftFeatherPowerup = 
					Constants.ITEM_FEATHER_POWERUP_DURATION;
		}
	}
	
	/**
	 * Finds out whether the power-up is still active 
	 * @return if power-up is still still active
	 */
	public boolean hasFeatherPowerup () 
	{
		return hasFeatherPowerup && timeLeftFeatherPowerup > 0;
	}
	
	@Override
	/**
	 * Edited by Owen Burnham (Assignment 7)
	 * Handles the switching of the viewing direction accoriding
	 * to the current move direction.  The time remaining of the 
	 * power-up effect is also checked.  If the time is up, the
	 * feather power-up effect is disabled.
	 */
	public void update (float deltaTime)
	{
		super.update(deltaTime);
		if (velocity.x != 0)
		{
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT :
				VIEW_DIRECTION.RIGHT;
		}
		if (timeLeftFeatherPowerup > 0)
		{
			timeLeftFeatherPowerup -= deltaTime;
			if (timeLeftFeatherPowerup < 0)
			{
				// disable power-up
				timeLeftFeatherPowerup = 0;
				setFeatherPowerup(false);
			}
		}
		dustParticles.update(deltaTime);
	}
	
	@Override
	/**
	 * Edited by Owen Burnham (Assignment 7)
	 * Handles the calculations and switching of states that
	 * is needed to enable jumping and falling
	 */
	protected void updateMotionY (float deltaTime)
	{
		switch (jumpState) 
		{
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			// Jump time left?
			if (timeJumping <= JUMP_TIME_MAX)
			{
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			// Add delta times to track jump time
			timeJumping += deltaTime;
			// Jump to minimal height if jump key was pressed to short
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN)
			{
				// Still Jumping
				velocity.y = terminalVelocity.y;
			}
		}
		if (jumpState != JUMP_STATE.GROUNDED)
		{	
			dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}	
	}
	
	@Override
	/**
	 * Edited by Owen Burnham (Assignment 7)
	 * Edited by Owen Burnham (Assignment 6)
	 * Handles the drawing of the image for the bunny head
	 * game object.  Image will be tinted orange if the feather 
	 * power-up effect is active.
	 */
	public void render (SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		// Draw Particles
		dustParticles.draw(batch);
		
		// Apply Skin Color
		batch.setColor(
				CharacterSkin.values()[GamePreferences.instance.charSkin]
			.getColor());			
		
		// Set special color when game object has a feather power-up
		if (hasFeatherPowerup)
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		// Draw image
		reg = regHead;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, 
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), 
				reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, 
				false);
		
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
	}
}
