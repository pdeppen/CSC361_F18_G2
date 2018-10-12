package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.Assets;


/**
 * @author Owen Burnham (Assignment 9)
 * Class for the carrot game object
 */
public class Carrot extends AbstractGameObject
{
	private TextureRegion regCarrot;
	
	/**
	 * Created by Owen Burnham (Assignment 9)
	 * runs the carrot through the init() method
	 */
	public Carrot()
	{
		init();
	
	}
	
	/**
	 * Created by Owen Burnham (Assignment 9)
	 * initializes the carrot appropriately
	 */
	private void init ()
	{
		dimension.set(0.25f, 0.5f);
		
		regCarrot = com.packtpub.libgdx.canyonbunny.util.Assets.instance.levelDecoration.carrot;
		
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	/**
	 * Created by Owen Burnham (Assignment 9)
	 * renders the carrot game object
	 */
	public void render (SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		reg = regCarrot;
		batch.draw(reg.getTexture(), position.x - origin.x, 
				position.y - origin.y, origin.x, origin.y, dimension.x, 
				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
				reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}
}
