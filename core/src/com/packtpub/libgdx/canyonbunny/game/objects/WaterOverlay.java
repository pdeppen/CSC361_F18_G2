package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.*;
import com.packtpub.libgdx.canyonbunny.util.Assets;

/**
 * @author Owen Burnham (Assignment 4)
 * The water overlay class will consist of an image that 
 * overlays the ground of the whole level
 */
public class WaterOverlay extends AbstractGameObject 
{
	private TextureRegion regWaterOverlay;
	private float length;
	
	/**
	 * @param length
	 * Makes an instance of WaterOverlay with
	 * it's length
	 */
	public WaterOverlay (float length) 
	{
		this.length = length;
		init();
	}
	
	/**
	 * initializes the water overlay's dimensions
	 */
	private void init() 
	{
		dimension.set(length * 10, 3);
		
		regWaterOverlay = Assets.instance.levelDecoration.waterOverlay;
		
		origin.x = -dimension.x / 2;
	}
	
	@Override
	/**
	 * @param batch
	 * renders/draws the appropriate batch for the
	 * water overlay
	 */
	public void render (SpriteBatch batch) 
	{
		TextureRegion reg = null;
		reg = regWaterOverlay;
		batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, 
				origin.y, dimension.x, dimension.y,  scale.x, scale.y, rotation, reg.getRegionX(), 
				reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
}
