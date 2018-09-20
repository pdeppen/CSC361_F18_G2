package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
/**
 * @author Owen Burnham (Assignment 4)
 * edited by Owen Burnham (Assignment 5)
 * This class will hold all the common attributes
 * and fucntionalities that each of our game objects
 * inherit from
 */
public abstract class AbstractGameObject 
{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;
	
	public Vector2 acceleration;
	public Rectangle bounds;
	
	/**
	 * edited by Owen Burnham (Assignment 5)
	 * Makes instances of all the variables for the 
	 * abstract game object
	 */
	public AbstractGameObject() 
	{
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	/**
	 * @param deltaTime
	 * updates objects accordingly in relevance 
	 * to the delat time
	 */
	public void update(float deltaTime) 
	{
		
	}
	
	/**
	 * @param batch
	 * objects are rendered accordingly
	 */
	public abstract void render(SpriteBatch batch);
}
