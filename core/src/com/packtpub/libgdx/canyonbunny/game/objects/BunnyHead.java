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
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.util.AudioManager;
import com.badlogic.gdx.graphics.g2d.Animation;

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
	
	//Tyler added from page 389
	// Changes bunny's animation states
	private Animation<TextureRegion> animNormal;
	private Animation<TextureRegion> animCopterTransform;
	private Animation<TextureRegion> animCopterTransformBack;
	private Animation<TextureRegion> animCopterRotate;
	
	
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
		
		animNormal = Assets.instance.bunny.animNormal;
		animCopterTransform = Assets.instance.bunny.animCopterTransform;
		animCopterTransformBack =
		Assets.instance.bunny.animCopterTransformBack;
		animCopterRotate = Assets.instance.bunny.animCopterRotate;
		setAnimation(animNormal);
		
		
		
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
	 * 
	 * Tyler Major added pg326-327 code to BunnyHead
	 * The changes in the code for BunnyHead trigger the sound effects for the jumped and
     * jumped-in-mid-air events at the right time. The jumpWithFeather sound is played
     *  using a different play() method of the AudioManager class. It is also provided with
     *  a random pitch value in the range from 1.0 to 1.1, which adds a little change in the
     *  frequency, rendering the rapidly repeated sound effect more interesting.
	 */
	public void setJumping (boolean jumpKeyPressed) 
	{
		switch (jumpState)	
		{
		case GROUNDED: // Character is standind on a platform
			if (jumpKeyPressed)
			{
				AudioManager.instance.play(Assets.instance.sounds.jump);
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
				AudioManager.instance.play(
						Assets.instance.sounds.jumpWithFeather, 1,
						MathUtils.random(1.0f, 1.1f));
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
	 * 
	 * Tyler Major updated for Assignment 12
	 * Changes animation state if feather is picked up
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
			if (animation == animCopterTransformBack) {
				// Restart "Transform" animation if another feather power-up
				// was picked up during "TransformBack" animation. Otherwise,
				// the "TransformBack" animation would be stuck while the
				// power-up is still active.
				setAnimation(animCopterTransform);
				}
			
			timeLeftFeatherPowerup -= deltaTime;
			if (timeLeftFeatherPowerup < 0)
			{
				// disable power-up
				timeLeftFeatherPowerup = 0;
				setFeatherPowerup(false);
				setAnimation(animCopterTransformBack);
			}
		}
		dustParticles.update(deltaTime);
		
		
		/* Tyler Major added this code from page 390. (Assignment 12)
		 * These if statements change depending on the animation state that the bunny is currently in
		 * when is picks up a feather
		 * All animation states are global variables above
		 */
		// Change animation state according to feather power-up
		if (hasFeatherPowerup)
		{
			if (animation == animNormal) 
			{
				setAnimation(animCopterTransform);
			} 
			else if (animation == animCopterTransform)
			{
				if (animation.isAnimationFinished(stateTime))
					setAnimation(animCopterRotate);
			}
		}
		else 
		{
			if (animation == animCopterRotate) 
			{
				if (animation.isAnimationFinished(stateTime))
					setAnimation(animCopterTransformBack);
			} 
		else if (animation == animCopterTransformBack)
		 {
			if (animation.isAnimationFinished(stateTime))
				setAnimation(animNormal);
		 }
		}
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
			if (velocity.x != 0)
			{
				dustParticles.setPosition(position.x + dimension.x / 2, position.y);
				dustParticles.start();
			}
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
	 * 
	 * Updated by Tyler Major on 10/20/2018 with pg_391
	 *  In the render() method, the color-tinting effect has been removed for
		new animations. If an animation other than the standard one (animNormal) is
		detected, the correcting values to the width and heightwill be applied for rendering.
		Since the standard animation is of a different dimension than the other animations,
		the other ones will look off-centered without the correcting values.
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
		
		float dimCorrectionX = 0;
		float dimCorrectionY = 0;
		if (animation != animNormal)
		{
			dimCorrectionX = 0.05f;
			dimCorrectionY = 0.2f;
		}
		
		// Draw image
		reg = animation.getKeyFrame(stateTime, true);
		
		
		// Set special color when game object has a feather power-up
		if (hasFeatherPowerup)
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		// Draw image
		//reg = regHead;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, 
				origin.y, dimension.x + dimCorrectionX,
				dimension.y + dimCorrectionY, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), 
				reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, 
				false);
		
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
	}
}
