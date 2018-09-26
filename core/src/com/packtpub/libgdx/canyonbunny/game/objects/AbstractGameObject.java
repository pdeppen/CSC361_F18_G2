package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

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
	 * updated by Owen Burnham (Assignment 5)
	 * @param deltaTime
	 * updates objects accordingly in relevance 
	 * to the delta time
	 * updates motion for both x and y
	 */
	public void update(float deltaTime) 
	{
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		// Move to new position
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
	}
	
	/**
	 * @param batch
	 * objects are rendered accordingly
	 */
	public abstract void render(SpriteBatch batch);
	
	/**
	 * author Owen Burnham (Assignment 5)
	 * @param deltaTime 
	 * Updates all physics related variables to the x variable
	 * including velocity, terminal velocity, friction and acceleration
	 */
	protected void updateMotionX (float deltaTime) 
	{
		if (velocity.x != 0)
		{
			// Apply friction
			if (velocity.x > 0)
			{
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			}
			else 
			{
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
			// Apply acceleration
			velocity.x += acceleration.x * deltaTime;
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}
	
	/**
	 * author Owen Burnham (Assignment 5)
	 * @param deltaTime
	 * Updates all physics related variables to the y variable
	 * including velocity, terminal velocity, friction and acceleration
	 */
	protected void updateMotionY (float deltaTime)
	{
		if (velocity.y != 0)
		{
			// Apply friction
			if (velocity.y > 0)
			{
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			}
			else 
			{
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
			// Apply acceleration
			velocity.y += acceleration.y * deltaTime;
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
	}
}
