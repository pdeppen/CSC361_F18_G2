/**
 * Group 2
 */
package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.*;
import com.packtpub.libgdx.canyonbunny.util.Assets;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Rock consists of left edge, middle part, and a right edge
 * 
 * Updated 10/3/2018 @author: Tyler Major
 * The float variables were added and changes were made in init()
 * These changes make sure that the following floating mechanism works properly
 * Starting value of float direction is set to up
 * The cycle is then randomly picked between 0 and half the max cycle time
 * Using a random cycle time gives the floating effect a more natural look
 */
public class Rock extends AbstractGameObject 
{
	
	private TextureRegion regEdge;
	private TextureRegion regMiddle;
	
	private final float FLOAT_CYCLE_TIME = 2.0f;
	private final float FLOAT_AMPLITUDE = 0.25f;
	private float floatCycleTimeLeft;
	private boolean floatingDownwards;
	private Vector2 floatTargetPosition;
	
	private int length;
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 */
	public Rock() 
	{
		init();
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * initializes objects
	 */
	private void init() {
		dimension.set(1, 1.5f);
		
		regEdge = Assets.instance.rock.edge;
		regMiddle = Assets.instance.rock.middle;
		
		// Start length of this rock
		setLength(1);
		
		floatingDownwards = false;
		floatCycleTimeLeft = MathUtils.random(0,
		FLOAT_CYCLE_TIME / 2);
		floatTargetPosition = null;
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * sets length
	 * 
	 * Updated by Tyler Major on 9/20/2018
	 * Added pg210
	 */
	public void setLength(int length) 
	{
		this.length = length;
		//Tyler Major: update bounding box for collision detection
		bounds.set(0,0,dimension.x * length, dimension.y);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * increases length
	 */
	public void increaseLength(int amount) 
	{
		setLength(length + amount);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * method inherited from AbstractGameObject
	 */
	@Override 
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		float relX = 0;
		float relY = 0;
		
		// Draw left edge
		reg = regEdge;
		relX -= dimension.x / 4;
		batch.draw(reg.getTexture(), position.x + relX, position.y +
				   relY, origin.x, origin.y, dimension.x / 4, dimension.y,
				   scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				   reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		// Draw middle
		relX = 0;
		reg = regMiddle;
		for (int i = 0; i < length; i++) {
			 batch.draw(reg.getTexture(), position.x + relX, position.y
					   +  relY, origin.x, origin.y, dimension.x, dimension.y,
					   scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
					   reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			 relX += dimension.x;
		}
		
		// Draw right edge
	   reg = regEdge;
       batch.draw(reg.getTexture(),position.x + relX, position.y +
				   relY, origin.x + dimension.x / 8, origin.y, dimension.x / 4,
				   dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
				   reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				   true, false);
	
	}
	
	
}
