/**
 * Group 2
 * AudioManager - centralized point of control over the game's audio playback
 */
package com.packtpub.libgdx.canyonbunny.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
	
	public static final AudioManager instance = new AudioManager();
	
	private Music playingMusic;
	
	// singleton: prevent instantiation from other classes
	private AudioManager() {}
	
	/**
	 * Made by Philip Deppen (Assignment 8, p.322)
	 * Overloaded this method to make certain paramters optional
	 * @param sound
	 */
	public void play (Sound sound) {
		play (sound, 1);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 8, p.322)
	 * Overloaded this method to make certain paramters optional
	 * @param sound
	 * @param volume
	 */
	public void play (Sound sound, float volume) {
		play (sound, volume, 1);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 8, p.322)
	 * Overloaded this method to make certain paramters optional
	 * @param sound
	 * @param volume
	 * @param pitch
	 */
	public void play (Sound sound, float volume, float pitch) {
		play (sound, volume, pitch, 0);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 8, p.322)
	 * Overloaded this method to make certain paramters optional
	 * @param sound
	 * @param volume
	 * @param pitch
	 * @param pan
	 */
	public void play (Sound sound, float volume, float pitch, float pan) {
		if (!GamePreferences.instance.sound)
			return;
		
		sound.play(GamePreferences.instance.volSound * volume, pitch, pan);
	}
	
	
}
