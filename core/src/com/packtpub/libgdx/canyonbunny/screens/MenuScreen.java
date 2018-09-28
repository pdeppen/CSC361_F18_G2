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
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;
import com.packtpub.libgdx.canyonbunny.util.CharacterSkin;


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
    		skinLibgdx.dispose();
    }
     
    @Override 
    public void pause () { }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * makes up final screen of the menu screen
     */
    private void rebuildStage() {
    		skinCanyonBunny = new Skin (Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI);
    		
    		skinLibgdx = new Skin (Gdx.files.internal(Constants.SKIN_LIBGDX_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
    		
    		//build all layers
    		Table layerBackground = buildBackgroundLayer();
    		Table layerObjects = buildObjectsLayer();
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
     * edited by Tyler Major from page 247
     * This resizes and moves the logos on the home screen
     */
    private Table buildLogosLayer () {
    	Table layer = new Table();
    	layer.left().top();
    	// + Game Logo
    	imgLogo = new Image(skinCanyonBunny, "logo");
    	layer.add(imgLogo);
    	layer.row().expandY();
    	// + Info Logos
    	imgInfo = new Image(skinCanyonBunny, "info");
    	layer.add(imgInfo).bottom();
    	if (debugEnabled) layer.debug();
    	return layer;
    	}
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * anchored to the bottom-right corner
     * @return Table
     * edited by Tyler Major (Assignment 6). Added pg 248 code
     * anchors play button
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
    
    //Tyler Major added from page 248
    private void onPlayClicked() {
    		game.setScreen(new GameScreen(game));
    }
    
  //Tyler Major added from page 248
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
    		
    		prefs.sound = chkSound.isChecked();
    		prefs.volSound = sldSound.getValue();
    		prefs.music = chkMusic.isChecked();
    		prefs.volMusic = sldMusic.getValue();
    		prefs.charSkin = selCharSkin.getSelectedIndex();
    		prefs.showFpsCounter = chkShowFpsCounter.isChecked();
    		prefs.save();
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void onCharSkinSelected(int index) {
    		CharacterSkin skin = CharacterSkin.values() [index];
    		imgCharSkin.setColor(skin.getColor());
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void onSaveClicked() {
    		saveSettings();
    		onCancelClicked();
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void onCancelClicked() {
    		btnMenuPlay.setVisible(true);
    		btnMenuOptions.setVisible(true);
    		winOptions.setVisible(false);
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * use and add widgets defined in the LibGDX ski
     */
    private Table buildOptWinAudioSettings () {
        Table tbl = new Table();
        // + Title: "Audio"
        tbl.pad(10, 10, 0, 10);
        tbl.add(new Label("Audio", skinLibgdx, "default-font", Color.ORANGE)).colspan(3);
        tbl.row();
        tbl.columnDefaults(0).padRight(10);
        tbl.columnDefaults(1).padRight(10);
        // + Checkbox, "Sound" label, sound volume slider
        chkSound = new CheckBox("", skinLibgdx);
        tbl.add(chkSound);
        tbl.add(new Label("Sound", skinLibgdx));
        sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
        tbl.add(sldSound);
        tbl.row();
        // + Checkbox, "Music" label, music volume slider
        chkMusic = new CheckBox("", skinLibgdx);
        tbl.add(chkMusic);
        tbl.add(new Label("Music", skinLibgdx));
        sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
        tbl.add(sldMusic);
        tbl.row();
        return tbl;
   }
}
