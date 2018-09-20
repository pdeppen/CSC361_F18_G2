/**
 * Group 2
 */
package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.*;
import com.packtpub.libgdx.canyonbunny.util.Assets;

/**
 * Rock consists of left edge, middle part, and a right edge
 */
public class Rock extends AbstractGameObject {
	
	private TextureRegion regEdge;
	private TextureRegion regMiddle;
	
	private int length;
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 */
	public Rock() {
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
	public void increaseLength(int amount) {
		setLength(length + amount);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * method inherited from AbstractGameObject
	 */
	@Override 
	public void render(SpriteBatch batch) {
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
