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
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
		}
	}
	
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * @param width
	 * @param height
	 */
	@Override 
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
     
	@Override 
    public void show () { }
     
    @Override 
    public void hide () { }
     
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
     * @return Table
     */
    private Table buildBackgroundLayer() {
    		Table layer = new Table();
    		return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * @return Table
     */
    private Table buildObjectsLayer() {
    		Table layer = new Table();
    		return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * @return Table
     */
    private Table buildLogosLayer () {
        Table layer = new Table();
        return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * @return Table
     */
    private Table buildControlsLayer () {
    		Table layer = new Table();
        return layer;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * @return Table
     */
    private Table buildOptionsWindowLayer () {
        Table layer = new Table();
        return layer;
    }
}
