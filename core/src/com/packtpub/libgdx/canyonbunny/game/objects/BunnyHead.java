package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.Assets;
import com.packtpub.libgdx.canyonbunny.util.Constants;
/**
 * @author Owen Burnham (Assignment 5)
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
	
	public void init () {};
	public void setJumping (boolean jumpKeyPressed) {};
	public void setFeatherPowerup (boolean pickedUp) {};
	public boolean hasFeatherPowerup () {};
}
