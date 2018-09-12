/**
 * Philip Deppen
 */
package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.mygdx.game.Constants;

public class Assets implements Disposable, AssetErrorListener {
	
	public static final String TAG = Assets.class.getName();
	
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	
	// singleton: prevent instantiation from other classes
	private Assets() {}
	
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
	}
	
	/**
	 * disposes objects after they're done being used
	 */
	@Override
	public void dispose() {
		assetManager.dispose();
	}
	
	/**
	 * Handles an error
	 * @param filename
	 * @param type
	 * @param throwable
	 */
	@Override
	public void error (String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset: '" + filename + "'", (Exception)throwable);
	}
	
	/**
	 * handles an error
	 */
	@Override
	public void error (AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}
	
	public class AssetLevelDecoration {
		public final AtlasRegion cloud01;
		public final AtlasRegion cloud02;
		public final AtlasRegion cloud03;
		public final AtlasRegion mountainLeft;
		public final AtlasRegion mountainRight;
		public final AtlasRegion waterOverlay;
		
		public AssetLevelDecoration (TextureAtlas atlas) {
			cloud01 = atlas.findRegion("cloud01");
			cloud02 = atlas.findRegion("cloud02");
			cloud03 = atlas.findRegion("cloud03");
			mountainLeft = atlas.findRegion("mountain_left");
			mountainRight = atlas.findRegion("mountain_right");
			waterOverlay = atlas.findRegion("water_overlay");
		}
		
	}
}
