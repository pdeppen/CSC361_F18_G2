package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * @author edited by Owen Burnham (Assignment 4)
 * CameraHelper assists the camera to be in the right position
 * and therefore displaying the correct image at the right spot.
 */
public class CameraHelper 
{
	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	private Vector2 position;
	private float zoom;
	
	private AbstractGameObject target;
	
	/**
	 * Creates an instance of CameraHelper with correct
	 * postion and zoom amount
	 */
	public CameraHelper () 
	{
	position = new Vector2();
	zoom = 1.0f;
	}
	
	/**
	 * updated by Owen Burnham (Assignment 4)
	 * @param deltaTime
	 * updates the camera position as necessary
	 */
	public void update (float deltaTime) 
	{
	if (!hasTarget()) return;
	position.x = target.position.x + target.origin.x;
	position.y = target.position.y + target.origin.y;
	
	//Tyler added this code from pg222
	// Prevent camera from moving down too far
	position.y = Math.max(-1f, position.y);
	}
	
	
	/**
	 * Sets the position of camera
	 * @param x
	 * @param y
	 */
	public void setPosition (float x, float y) 
	{
	this.position.set(x, y);
	}
	
	/**
	 * @return the position of the camera
	 */
	public Vector2 getPosition () 
	{
		return position; 
	}
	
	/**
	 * adds the requested amount of zoom
	 * @param amount
	 */
	public void addZoom (float amount) 
	{ 
		setZoom(zoom + amount); 
	}
	
	/**
	 * sets the requested amount of zoom
	 * @param zoom
	 */
	public void setZoom (float zoom) 
	{
	this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	/**
	 * @return the zoom value
	 */
	public float getZoom () 
	{
		return zoom; 
	}
	
	
	/**
	 * Updated by Owen Burnham (Assignment 4)
	 * @param target
	 * Makes an instance of target of the abstract 
	 * game object
	 */
	public void setTarget (AbstractGameObject target)
	{
		this.target = target;
	}
	
	/**
	 * Updated by Owen Burnham (Assignment 4)
	 * @return target
	 * returns the current target of the abstract game object
	 */
	public AbstractGameObject getTarget ()
	{ 
		return target; 
	}
	
	/**
	 * @return if the target has value
	 */
	public boolean hasTarget ()
	{
		return target != null; 
	}
	
	/**
	 * Updated by Owen Burnham (Assignment 4)
	 * @param target
	 * @return hasTarget()
	 * returns what is in the target if anything
	 */
	public boolean hasTarget (AbstractGameObject target) 
	{
	return hasTarget() && this.target.equals(target);
	}
	
	/**
	 * applies variables to the camera
	 * @param camera
	 */
	public void applyTo (OrthographicCamera camera)
	{
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}

