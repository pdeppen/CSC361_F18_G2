/**
 * Group 2
 */
package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.*;

/**
 * A cloud object will be one of 3 available cloud images
 */
public class Clouds extends AbstractGameObjects {
	
	private float length;
	
	private Array<TextureRegion> regClouds;
	private Array<Cloud> clouds;
	
	private class Cloud extends AbstractGameObject {
		private TextureRegion regCloud;
		
		public Cloud() {}
		
		public void setRegion (TextureRegion region) {
			regCloud = region;
		}
		
		@Override
		public void render(SpriteBatch batch) {
			TextureRegion reg = regCloud;
	         batch.draw(reg.getTexture(), position.x + origin.x,
					   position.y + origin.y, origin.x, origin.y, dimension.x,
					   dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
					   reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
					   false, false);
	         
		}
	}
	
	public Clouds (float length) {
		this.length = length;
		init();
	}
	
	private void init() {
		
	}
}
