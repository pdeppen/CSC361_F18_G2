package com.packtpub.libgdx.canyonbunny.util;
/*  Tyler Major
 *  pg 113 
 *  9/7/2018
 *  
 *  new code added 9/13 from pg162
 *  
 *  Edited by Owen Burnham for Assignment 4
 */
/**
 * @author Edited by Owen Burnham (Assignment 4)
 * Holds variables that will always hold the same
 * value
 */
public class Constants 
{
	// Visible game world is 5 meters wide
	public static final float VIEWPORT_WIDTH = 5.0f;
	
	// Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	
	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = "images/canyonbunny.pack.atlas";
	
	// Location of image file for level 01
	public static final String LEVEL_01 = "levels/level-01.png";
	
	// Amount of extra lives at level start
	public static final int LIVES_START = 3;
}