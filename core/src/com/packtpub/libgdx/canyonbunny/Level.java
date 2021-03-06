package com.packtpub.libgdx.canyonbunny;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead;
import com.packtpub.libgdx.canyonbunny.game.objects.Clouds;
import com.packtpub.libgdx.canyonbunny.game.objects.Mountains;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.game.objects.WaterOverlay;
//Tyler Major added these from pg210 level
//import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead;
import com.packtpub.libgdx.canyonbunny.game.objects.Feather;
import com.packtpub.libgdx.canyonbunny.game.objects.GoldCoin;
//Tyler added imports from pg 341 level
import com.packtpub.libgdx.canyonbunny.game.objects.Carrot;
import com.packtpub.libgdx.canyonbunny.game.objects.Goal;


/* @author: Tyler Major
 * pg 177-178 level code.. also 179-180...also 181-182
 * This class is the loader that will read and interpret the image data
 * All clouds, mountains, and waterOverlay are filled in level class
 */
public class Level 
{
	public static final String TAG = Level.class.getName();
	public Goal goal;                   //Tyler added from pg342. Adds a goal variable

	public enum BLOCK_TYPE 
	{
		EMPTY(0, 0, 0), // black
		ROCK(0, 255, 0), // green
		PLAYER_SPAWNPOINT(255, 255, 255), // white
		ITEM_FEATHER(255, 0, 255), // purple
		GOAL(236, 56, 56), // red
		ITEM_GOLD_COIN(255, 255, 0); // yellow
		private int color;
		
		private BLOCK_TYPE (int r, int g, int b) 
		{
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		public boolean sameColor (int color) 
		{
			return this.color == color;
		}
		
		public int getColor ()
		{
			return color;
		}
	}
		// objects
		public Array<Rock> rocks;
		public BunnyHead bunnyHead;
		public Array<GoldCoin> goldcoins;
		public Array<Feather> feathers;
		public Array<Carrot> carrots;       //Tyler added from pg342. Creates a carrot array
  
		
		// decoration
		public Clouds clouds;
		public Mountains mountains;
		public WaterOverlay waterOverlay;
		
		
		public Level (String filename) 
		{
			init(filename);
		}
		private void init (String filename) 
		{
			//Tyler added: player character
			bunnyHead = null;
			
			// objects
			rocks = new Array<Rock>();
			goldcoins = new Array<GoldCoin>();
			feathers = new Array<Feather>();
			carrots = new Array<Carrot>();
			
			// load image file that represents the level data
			Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
			
			// scan pixels from top-left to bottom-right
			int lastPixel = -1;
			for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) 
			{
				for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) 
				{
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is
				// a match
				// empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) 
				{
					// do nothing
				}
			// rock
			else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) 
			{
				if (lastPixel != currentPixel) 
				{
				obj = new Rock();
				float heightIncreaseFactor = 0.25f;
				offsetHeight = -2.5f;
				obj.position.set(pixelX, baseHeight * obj.dimension.y
				* heightIncreaseFactor + offsetHeight);
				rocks.add((Rock)obj);
				} 
				else
				{
					rocks.get(rocks.size - 1).increaseLength(1);
				}
			}
			// player spawn point
			else if
			(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) 
			{
				obj = new BunnyHead();
				offsetHeight = -3.0f;
				obj.position.set(pixelX,baseHeight * obj.dimension.y +
						offsetHeight);
						bunnyHead = (BunnyHead)obj;
			}
			// feather
			else if (BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) 
			{
				obj = new Feather();
				offsetHeight = -1.5f;
				obj.position.set(pixelX,baseHeight * obj.dimension.y
				+ offsetHeight);
				feathers.add((Feather)obj);
			}
			// gold coin
			else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) 
			{
					obj = new GoldCoin();
					offsetHeight = -1.5f;
					obj.position.set(pixelX,baseHeight * obj.dimension.y
					+ offsetHeight);
					goldcoins.add((GoldCoin)obj);
			}
				// goal
			else if (BLOCK_TYPE.GOAL.sameColor(currentPixel)) 
			{
			obj = new Goal();
			offsetHeight = -7.0f;
			obj.position.set(pixelX, baseHeight + offsetHeight);     //Tyler added from page 343. Adds end goal to level. Marked in red on png file
			goal = (Goal)obj;	
			}
			
				
			// unknown object/pixel color
			else 
			{
				int r = 0xff & (currentPixel >>> 24); //red color channel
				int g = 0xff & (currentPixel >>> 16); //green color channel
				int b = 0xff & (currentPixel >>> 8); //blue color channel
				int a = 0xff & currentPixel; //alpha channel
				Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<"
				+ pixelY + ">: r<" + r+ "> g<" + g + "> b<" + b + "> a<" + a + ">");
			}
			lastPixel = currentPixel;
		}
	}
			
		
			// decoration
			clouds = new Clouds(pixmap.getWidth());
			clouds.position.set(0, 2);
			mountains = new Mountains(pixmap.getWidth());
			mountains.position.set(-1, -1);
			waterOverlay = new WaterOverlay(pixmap.getWidth());
			waterOverlay.position.set(0, -3.75f);
			// free memory
			pixmap.dispose();
			Gdx.app.debug(TAG, "level '" + filename + "' loaded");
		}
		
		//Render matches lastPixel with currentPixel and used to detect rock pixels/colors.
		public void render(SpriteBatch batch) 
		{
			// Draw Mountains
			mountains.render(batch);
			// Draw Goal
			goal.render(batch);           //Tyler added from pg344. Renders end goal which was altered in png file to be red
			// Draw Rocks
			for (Rock rock : rocks)
			rock.render(batch);
			// Draw Gold Coins
			for (GoldCoin goldCoin : goldcoins)
			goldCoin.render(batch);
			// Draw Feathers
			for (Feather feather : feathers)
			feather.render(batch);
			// Draw Carrots
			for (Carrot carrot : carrots)
			carrot.render(batch);              //Tyler added from pg344. Draws carrot
			// Draw Player Character
			bunnyHead.render(batch);
			// Draw Water Overlay
			waterOverlay.render(batch);
			// Draw Clouds
			clouds.render(batch);
		}
		public void update (float deltaTime) 
		{
			bunnyHead.update(deltaTime);
			for(Rock rock : rocks)
			rock.update(deltaTime);
			for(GoldCoin goldCoin : goldcoins)
			goldCoin.update(deltaTime);
			for(Feather feather : feathers)
				feather.update(deltaTime);
			for (Carrot carrot : carrots)
				carrot. update(deltaTime);    //Tyler added from page344. Adds delata time to carrot array
			clouds.update(deltaTime);
			}
			
			
}
		
