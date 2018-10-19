package com.packtpub.libgdx.canyonbunny.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

/**
 * Philip Deppen
 * Edited by Owen Burnham (Assignment 4)
 * Edited by Owen Burnham (Assignment 8)
 * Edited by Philip Deppen (Assignment 10)
 * Assets class for all the images
 */


public class Assets implements Disposable, AssetErrorListener {
	
	public static final String TAG = Assets.class.getName();
	
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	
	public AssetBunny bunny;
	public AssetRock rock;
	public AssetGoldCoin goldCoin;
	public AssetFeather feather;
	public AssetLevelDecoration levelDecoration;
	public AssetFonts fonts;
	
	public AssetSounds sounds;
	public AssetMusic music;
	
	// singleton: prevent instantiation from other classes
	private Assets() {}
	
	/**
	 * Made by Owen Burnahm (Assignment 4)
	 * Creates the three fonts, small, normal, and big, and
	 * gives them the appropriate properties
	 * @return 
	 */

	public class AssetFonts
	{
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		
		public AssetFonts() 
		{
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(
					Gdx.files.internal("../core/assets/font/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(
					Gdx.files.internal("../core/assets/font/arial-15.fnt"), true);
			defaultBig = new BitmapFont(
					Gdx.files.internal("../core/assets/font/arial-15.fnt"), true);
			
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(
					TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(
					TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(
					TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	/**
	 * Made by Philip Deppen (Assignment 3)
	 * Edited by Owen Burnham (Assignment 8)
	 * initializes objects when constructor is called
	 */
	public void init (AssetManager assetManager) 
	{
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		// load sounds
		assetManager.load("sounds/jump.wav", Sound.class);
		assetManager.load("sounds/jump_with_feather.wav", Sound.class);
		assetManager.load("sounds/pickup_coin.wav", Sound.class);
		assetManager.load("sounds/pickup_feather.wav", Sound.class);
		assetManager.load("sounds/live_lost.wav", Sound.class);
		// load music
		assetManager.load("music/keith303_-_brand_new_highscore.mp3", Music.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + 
	     assetManager.getAssetNames().size);
		
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		
		// enable texture filtering for pixel smoothing
		for (Texture t: atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		// create game resource objects
		fonts = new AssetFonts();
		bunny = new AssetBunny(atlas);
		rock = new AssetRock(atlas);
		goldCoin = new AssetGoldCoin(atlas);
		feather = new AssetFeather(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
	}
	
	/**
	 * Updated by Owen Burnham (Assignment 4)
	 * disposes objects after they're done being used
	 */
	@Override
	public void dispose() 
	{
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}
	
	/**
	 * Made by Philip Deppen (Assignment 3)
	 * handles an error
	 */
	public void error (String filename, Class type, Throwable throwable) 
	{
		Gdx.app.error(TAG, "Couldn't load asset: '" + filename + "'", (Exception)throwable);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 3)
	 * handles an error
	 */
	@Override
	public void error (AssetDescriptor asset, Throwable throwable) 
	{
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 3)
	 * Edited by Owen Burnham (Assignment 9)
	 * decorates the atlas
	 */
	public class AssetLevelDecoration 
	{
		public final AtlasRegion cloud01;
		public final AtlasRegion cloud02;
		public final AtlasRegion cloud03;
		public final AtlasRegion mountainLeft;
		public final AtlasRegion mountainRight;
		public final AtlasRegion waterOverlay;
		
		public final AtlasRegion carrot;
		public final AtlasRegion goal;
		
		/**
		 * Edited by Owen Burnham (Assignment 9)
		 * makes an instance of the texture atlas
		 * @param atlas
		 */
		public AssetLevelDecoration (TextureAtlas atlas) 
		{
			cloud01 = atlas.findRegion("cloud01");
			cloud02 = atlas.findRegion("cloud02");
			cloud03 = atlas.findRegion("cloud03");
			mountainLeft = atlas.findRegion("mountain_left");
			mountainRight = atlas.findRegion("mountain_right");
			waterOverlay = atlas.findRegion("water_overlay");
			carrot = atlas.findRegion("carrot");
			goal = atlas.findRegion("goal");
		}
		
	}
	
	//Tyler Major: Class that loads the bunny head
	public class AssetBunny
	{
		public final AtlasRegion head;
		public final Animation animNormal;
		public final Animation animCopterTransfrom;
		public final Animation animCopterTransformBack;
		public final Animation animCopterRotate;
		
		public AssetBunny (TextureAtlas atlas) 
		{
			head = atlas.findRegion("bunny_head");
			
			Array <AtlasRegion> regions = null;
			AtlasRegion region = null;
			
			// Animation: Bunny Normal
			regions = atlas.findRegions("anim_bunny_normal");
			animNormal = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
			
			// Animation: Bunny Copter - knot ears
		}
	}
	
	//Tyler Major: Class that loads the rock edge image and rock middle image
	public class AssetRock 
	{
		public final AtlasRegion edge;
		public final AtlasRegion middle;
		public AssetRock (TextureAtlas atlas) 
		{
			edge = atlas.findRegion("rock_edge");
			middle = atlas.findRegion("rock_middle");
		}
	}
	
	/**
	 * Tyler Major: Class that loads the gold coin image
	 * Edited by Philip Deppen (Assignment 10, p. 383) - Added animation for gold coin
	 */
	public class AssetGoldCoin 
	{
		public final AtlasRegion goldCoin;
		public final Animation animGoldCoin;
		
		public AssetGoldCoin (TextureAtlas atlas) 
		{
			goldCoin = atlas.findRegion("item_gold_coin");
			
			// Animation: Gold Coin
			Array <AtlasRegion> regions = atlas.findRegions("anim_gold_coin");
			AtlasRegion region = regions.first();
			
			for (int i = 0; i < 10; i++) 
			{
				regions.insert(0,  region);
			}
			
			animGoldCoin = new Animation (1.0f / 20.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
		}
	}
	
	//Tyler Major: Class that loads the feather image
	public class AssetFeather 
	{
		public final AtlasRegion feather;
		public AssetFeather (TextureAtlas atlas) 
		{
			feather = atlas.findRegion("item_feather");
		}
	}
	
	/**
	 * Created by Owen Burnham (Assignment 8)
	 * Inner class that holds the loaded sound instances
	 */
	public class AssetSounds
	{
		public final Sound jump;
		public final Sound jumpWithFeather;
		public final Sound pickupCoin;
		public final Sound pickupFeather;
		public final Sound liveLost;
		
		public AssetSounds (AssetManager am)
		{
			jump = am.get("sounds/jump.wav", Sound.class);
			jumpWithFeather = am.get("sounds/jump_with_feather.wav", Sound.class);
			pickupCoin = am.get("sounds/pickup_coin.wav", Sound.class);
			pickupFeather = am.get("sounds/pickup_feather.wav", Sound.class);
			liveLost = am.get("sounds/live_lost.wav", Sound.class);
		}
	}
	
	/**
	 * Created by Owen Burnham (Assignment 8)
	 * Inner class that holds the loaded music instance
	 */
	public class AssetMusic
	{
		public final Music song01;
		
		public AssetMusic (AssetManager am)
		{
			song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
		}
	}


	
}
