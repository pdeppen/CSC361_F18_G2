/**
 * Group 2
 */
package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.*;

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
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Made by Philip Deppen (Assignment 4)
	 * increases length
	 */
	public void increaseLength(int amount) {
		setLength(length + amount);
	}
	
	
}
