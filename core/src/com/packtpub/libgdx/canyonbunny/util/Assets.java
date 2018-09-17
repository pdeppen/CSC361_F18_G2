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




/**
 * Philip Deppen
 * Updated by Owen Burnham (Assignment 4)
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
	 * initializes objects when constructor is called
	 */
	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
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
	public void error (String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset: '" + filename + "'", (Exception)throwable);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 3)
	 * handles an error
	 */
	@Override
	public void error (AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 3)
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
		
		public AssetLevelDecoration (TextureAtlas atlas) 
		{
			cloud01 = atlas.findRegion("cloud01");
			cloud02 = atlas.findRegion("cloud02");
			cloud03 = atlas.findRegion("cloud03");
			mountainLeft = atlas.findRegion("mountain_left");
			mountainRight = atlas.findRegion("mountain_right");
			waterOverlay = atlas.findRegion("water_overlay");
		}
		
	}
	
	//Tyler Major: Class that loads the bunny head
	public class AssetBunny{
		public final AtlasRegion head;
		
		public AssetBunny (TextureAtlas atlas) {
			head = atlas.findRegion("bunny_head");
		}
	}
	
	//Tyler Major: Class that loads the rock edge image and rock middle image
	public class AssetRock {
		public final AtlasRegion edge;
		public final AtlasRegion middle;
		public AssetRock (TextureAtlas atlas) {
			edge = atlas.findRegion("rock_edge");
			middle = atlas.findRegion("rock_middle");
		}
	}
	
	//Tyler Major: Class that loads the gold coin image
	public class AssetGoldCoin {
		public final AtlasRegion goldCoin;
		public AssetGoldCoin (TextureAtlas atlas) {
		goldCoin = atlas.findRegion("item_gold_coin");
		}
	}
	
	//Tyler Major: Class that loads the feather image
	public class AssetFeather {
		public final AtlasRegion feather;
		public AssetFeather (TextureAtlas atlas) {
		feather = atlas.findRegion("item_feather");
		}
	}


	
}