package physics.puzzle.scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;

import org.andengine.util.color.Color;

import physics.puzzle.managers.Resources;
import physics.puzzle.managers.Storage;
import physics.puzzle.managers.Scenes.SceneType;





import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.View;


public class Splash extends Base 
{

	@Override
	public void createScene() 
	{
		setBackground(new Background(Color.BLACK));
		Constants.GameLevel = Storage.Get("level");
		float _w = _Resources._LoadingTiled.getWidth();
		float _h = _Resources._LoadingTiled.getHeight();
		AnimatedSprite loadingSprite = new AnimatedSprite(GameConstants.CAMERA_WIDTH/2 - (_w/2), GameConstants.CAMERA_HEIGHT-_h,
				_Resources._LoadingTiled, _Resources._VBO);
		loadingSprite.animate(new long[]{500, 700, 900, 1000, 1200}, new int[]{0,1,2,3,4}, 0);
		attachChild(loadingSprite);
		
	}

	@Override
	public void onBackKeyPressed() 
	{
		 
	}
	
	@Override
	public SceneType getSceneType() 
	{
		
		return SceneType.SCENE_SPLASH;
	}
	
	@Override
	public void disposeScene() 
	{
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}



}
