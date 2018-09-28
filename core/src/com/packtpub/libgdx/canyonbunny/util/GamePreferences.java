package com.packtpub.libgdx.canyonbunny.util;

/* Tyler Major
 * This class abstracts the process of loading and
	saving all of our game settings.
 *  
 *  pg 251 in the book
 *  9/27/2018
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GamePreferences 
{

	public static final String TAG = GamePreferences.class.getName();
	
	public static final GamePreferences instance = new GamePreferences();
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public int charSkin;
	public boolean showFpsCounter;
	private Preferences prefs;
	
	// singleton: prevent instantiation from other classes
	private GamePreferences () 
	{
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	
	public void load () { }
	public void save () { }
	
	//Tyler Major added from page 252. Loads the music and sound into
	//main screen
	public void load () {
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f),
		0.0f, 1.0f);
		volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f),
				0.0f, 1.0f);
				charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0),
				0, 2);
				showFpsCounter = prefs.getBoolean("showFpsCounter", false);
				}
	
	//Tyler Major added from page 252.
	//takes the current values of its
	//This takes the current values of the public variables and puts them into the map of the preferences file. Finally, flush()
	//is called on the preferences file to actually write the changed values into the file.
	public void save () {
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		prefs.putInteger("charSkin", charSkin);
		prefs.putBoolean("showFpsCounter", showFpsCounter);
		prefs.flush();
		}
	
	}

}
