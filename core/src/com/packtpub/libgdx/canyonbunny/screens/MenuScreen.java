/**
 * Group 2
 * Made by Philip Deppen (Assignment 6)
 */
package com.packtpub.libgdx.canyonbunny.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.packtpub.libgdx.canyonbunny.util.Assets;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.packtpub.libgdx.canyonbunny.util.CharacterSkin;
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;

public class MenuScreen extends AbstractGameScreen {
	
	private static final String TAG = MenuScreen.class.getName();
	private Stage stage;
	private Skin skinCanyonBunny;
	// menu
	private Image imgBackground;
	private Image imgLogo;
	private Image imgInfo;
	private Image imgCoins;
	private Image imgBunny;
	private Button btnMenuPlay;
	private Button btnMenuOptions;
	// options
	private Window winOptions;
	private TextButton btnWinOptSave;
	private TextButton btnWinOptCancel;
	private CheckBox chkSound;
	private Slider sldSound;
	private CheckBox chkMusic;
	private Slider sldMusic;
	private SelectBox<CharacterSkin> selCharSkin;
	private Image imgCharSkin;
	private CheckBox chkShowFpsCounter;
	
	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;
	
	private Skin skinLibgdx;
	
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * @param game
	 */
	public MenuScreen (Game game) {
		super(game);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * constantly clears the screen by filling it with a solid black.
	 * also checks whether the screen has been touched.
	 * @param deltaTime
	 */
	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <=  0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}
		stage.act(deltaTime);
		stage.draw();
		Table.drawDebug(stage);
	}
	
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * sets the viewport size of the stage
	 * @param width
	 * @param height
	 */
	@Override 
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
    
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * called when the screen is shown. initializes the stage
	 */
	@Override 
    public void show () {
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}
    
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * frees the allocated resources when the screen is hidden
	 */
    @Override 
    public void hide () {
    		stage.dispose();
    		skinCanyonBunny.dispose();
    }
     
    @Override 
    public void pause () { }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * makes up final screen of the menu screen
     */
    private void rebuildStage() {
    		skinCanyonBunny = new Skin (Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI);
    		
    		//build all layers
    		Table layerBackground = buildBackgroundLayer();
    		Table layerObjects = buildOBjectsLayer();
    		Table layerLogos = buildLogosLayer();
    		Table layerControls = buildControlsLayer();
    		Table layerOptionsWindow = buildOptionsWindowLayer();
    		
    		// assemble stage for menu screen
    		stage.clear();
    		Stack stack = new Stack();
    		stage.addActor(stack);
    		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
    		stack.add(layerBackground);
    		stack.add(layerObjects);
    		stack.add(layerLogos);
    		stack.add(layerControls);
    		stack.addActor(layerOptionsWindow);
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * draws background image
     * @return Table
     */
    private Table buildBackgroundLayer() {
    		Table layer = new Table();
    		// + Background
    		imgBackground = new Image(skinCanyonBunny, "background");
    		layer.add(imgBackground);
    		return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * creates image of coins and bunny head
     * @return Table
     */
    private Table buildObjectsLayer() {
    		Table layer = new Table();
    		// + Coins
    		imgCoins = new Image(skinCanyonBunny, "coins");
    		layer.addActor(imgCoins);
    		imgCoins.setPosition(135, 80);
    		// + Bunny
    		imgBunny = new Image(skinCanyonBunny, "bunny");
    		layer.addActor(imgBunny);
    		imgBunny.setPosition(355, 40);
    		return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * anchored in the top-left corner
     * @return Table
     */
    private Table buildLogosLayer () {
        Table layer = new Table();
        layer.left().top();
        
        // + Game logo
        imgLogo = new Image(skinCanyonBunny, "logo");
        layer.add(imgLogo);
        layer.row().expandY();
        
        // + Info Logos
        imgInfo = new Image(skinCanyonBunny, "info");
        layer.add(imgInfo).bottom();
        
        if (debugEnabled) {
        		layer.debug();
        }
        return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * anchored to the bottom-right corner
     * @return Table
     */
    private Table buildControlsLayer () {
    		Table layer = new Table();
    		layer.right().bottom();
    		
    		// + Play Button
    		btnMenuPlay = new Button(skinCanyonBunny, "play");
    		layer.add(btnMenuPlay);
    		btnMenuPlay.addListener(new ChangeListener() {
    			@Override public void changed (ChangeEvent event, Actor actor) {
    				onPlayClicked();
    			}
    		});
    		layer.row();
    		// + Options Button
    		btnMenuOptions = new Button (skinCanyonBunny, "options");
    		layer.add(btnMenuOptions);
    		btnMenuOptions.addListener(new ChangeListener() {
    			@Override
    			public void changed (ChangeEvent event, Actor actor) {
    				onOptionsClicked();
    			}
    		});
    		if (debugEnabled) {
    			layer.debug();
    		}
        return layer;
    }
    
    private void onPlayClicked() {
    		game.setScreen(new GameScreen(game));
    }
    
    private void onOptionsClicked() {
    	
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * @return Table
     */
    private Table buildOptionsWindowLayer () {
        Table layer = new Table();
        return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void loadSettings() {
    	GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        chkSound.setChecked(prefs.sound);
        sldSound.setValue(prefs.volSound);
        chkMusic.setChecked(prefs.music);
        sldMusic.setValue(prefs.volMusic);
        selCharSkin.setSelectedIndex(prefs.charSkin);
        onCharSkinSelected(prefs.charSkin);
        chkShowFpsCounter.setChecked(prefs.showFpsCounter);
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void saveSettings() {
    		GamePreferences prefs = GamePreferences.instance;
    }
}
