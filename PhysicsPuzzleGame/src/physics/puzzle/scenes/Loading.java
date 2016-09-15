package physics.puzzle.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Storage;
import physics.puzzle.managers.Scenes.SceneType;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.View;


public class Loading extends Base 
{

	@Override
	public void createScene() 
	{
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		Sprite _SpriteFon1 = new Sprite(0, 0, _Resources._LoadingFon1, _Resources._VBO);
		_SpriteFon1.setSize(GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
        autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, _SpriteFon1));
		setBackground(autoParallaxBackground);
		
		float _w = _Resources._LoadingTiled2.getWidth();
		float _h = _Resources._LoadingTiled2.getHeight();
		AnimatedSprite loadingSprite = new AnimatedSprite(GameConstants.CAMERA_WIDTH/2 - (_w/2), GameConstants.CAMERA_HEIGHT-_h,
				_Resources._LoadingTiled2, _Resources._VBO);
		loadingSprite.animate(new long[]{400, 600, 800, 900, 1100}, new int[]{0,1,2,3,4}, 0);
		attachChild(loadingSprite);
		
		_Engine.registerUpdateHandler(new TimerHandler(3F, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				_Engine.unregisterUpdateHandler(pTimerHandler);
				Sprite _SpriteFon2 = new Sprite(0, 0, _Resources._LOadingFon2, _Resources._VBO);
				_SpriteFon2.setSize(GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
		        autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, _SpriteFon2));
				setBackground(autoParallaxBackground);
			}
		}));
	}

	@Override
	public void onBackKeyPressed() 
	{

	}

	@Override
	public SceneType getSceneType() 
	{
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() 
	{
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}

	
}
