 package physics.puzzle.scenes;

import java.util.HashMap;
import java.util.Map;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import physics.puzzle.managers.Resources;
import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Storage;
import physics.puzzle.managers.Scenes.SceneType;
import physics.puzzle.scenes.assist.TwitterEventObserver;

import com.nostra13.socialsharing.common.AuthListener;
import com.nostra13.socialsharing.facebook.FacebookFacade;
import com.nostra13.socialsharing.twitter.TwitterFacade;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class Menu extends Base implements IOnSceneTouchListener, IOnAreaTouchListener, IOnMenuItemClickListener
{
	private static final String TAG = "myLogs";
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_EXIT = 1;
	private final int MENU_RETINGS = 2;
	private Map<String, String> actionsMap;
	private Handler mHandler;
	@Override
	public void createScene() 
	{
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		Sprite _SpriteFon1 = new Sprite(0, 0, _Resources._MenuBackground, _Resources._VBO);
		_SpriteFon1.setSize(GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
        autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, _SpriteFon1));
		setBackground(autoParallaxBackground);
		
		setOnSceneTouchListener(this);
		actionsMap = new HashMap<String, String>();
		actionsMap.put(GameConstants.FACEBOOK_SHARE_ACTION_NAME, GameConstants.FACEBOOK_SHARE_ACTION_LINK);
		createMenuChildScene();
		createButtonFacebook();
		int music = Storage.Get("music");
		Log.d(TAG, "musicInt = "+music);
		if(music == 1&& Constants._firstStart)
		{
			Log.d(TAG, "music == 1&& Constants._firstStart");
			_Resources._ExampleM.setVolume(0.5f);
			_Resources._ExampleM.play();
			Constants._firstStart = false;
		}
		else
		{
			Log.d(TAG, "music == 0&& Constants._firstStart="+Constants._firstStart);
		}
		
	}

	@Override
	public void onBackKeyPressed() 
	{
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public SceneType getSceneType() 
	{
		// TODO Auto-generated method stub
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() 
	{
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) 
	{
		
		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		if(pSceneTouchEvent.isActionDown())
          {
			//Scenes.getInstance().CreateGameScene(_Engine);
          }
          if(pSceneTouchEvent.isActionUp())
          {
          	return true;
          }
		
		return true;
	}

	private void createButtonFacebook()
	{
		Sprite _Facebook = new Sprite(GameConstants.CAMERA_WIDTH - 175,
				GameConstants.CAMERA_HEIGHT - 100, _Resources._Facebook, _Resources._VBO)
		{
			@Override
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	    	{
		  	            if(pSceneTouchEvent.isActionDown())
		  	            {
		  	            	setScale(0.8f);
		  	            	return true;
		  	            }
		  	          if(pSceneTouchEvent.isActionUp())
		  	            {
		  	        	    setScale(1.0f);
		  	        	   _MainActivity.gameFacebook();
		  	            	return true;
		  	            }
						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    	}
		};
		
		Sprite _Twitter = new Sprite(GameConstants.CAMERA_WIDTH - 100,
				GameConstants.CAMERA_HEIGHT - 100, _Resources._Twitter, _Resources._VBO)
		{
			@Override
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	    	{
		  	            if(pSceneTouchEvent.isActionDown())
		  	            {
		  	            	setScale(0.8f);
		  	            	return true;
		  	            }
		  	            if(pSceneTouchEvent.isActionUp())
		  	            {
		  	            	setScale(1.0f);
		  	            	_MainActivity.gameTwitter();
		  	            	return true;
		  	            }

						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    	}
		};
		
		registerTouchArea(_Twitter);
		registerTouchArea(_Facebook);
		
		attachChild(_Facebook);
		attachChild(_Twitter);
	}
	
	private void createMenuChildScene()
	{
		menuChildScene = new MenuScene(_Camera);
		//menuChildScene.setPosition(0, 0);
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, _Resources._NewGame, _Resources._VBO), 1.2f, 1);
		final IMenuItem exitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EXIT, _Resources._Exit, _Resources._VBO), 1.2f, 1);
		final IMenuItem retingsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETINGS, _Resources._Retings, _Resources._VBO), 1.2f, 1);
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(retingsMenuItem);
		menuChildScene.addMenuItem(exitMenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() - 15);
		exitMenuItem.setPosition(exitMenuItem.getX(), exitMenuItem.getY() + 15);
		retingsMenuItem.setPosition(retingsMenuItem.getX(), retingsMenuItem.getY());
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID())
		{
			case MENU_PLAY:
				//Load Game Scene!
				Scenes.getInstance().CreateUpgradeScene(_Engine);
				return true;
			case MENU_EXIT:
				Storage.Add("level", Constants.GameLevel);
				android.os.Process.killProcess(android.os.Process.myPid());
				return true;
			case MENU_RETINGS:
				Intent intent = new Intent(Intent.ACTION_VIEW);
  	      		intent.setData(Uri.parse("market://details?id=physics.puzzle.games"));
  	      	    _MainActivity.startActivity(intent);
				return true;
			default:
				return false;
}
	}
	
	
	
}
