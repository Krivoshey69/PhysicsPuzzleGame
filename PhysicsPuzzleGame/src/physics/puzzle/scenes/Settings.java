package physics.puzzle.scenes;

import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Scenes.SceneType;
import android.hardware.Sensor;
import android.hardware.SensorEvent;


public class Settings extends Base 
{

	@Override
	public void createScene() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackKeyPressed() 
	{
		Scenes.getInstance().CreateMenuScene(_Engine);	
	}

	@Override
	public SceneType getSceneType() 
	{
		// TODO Auto-generated method stub
		return SceneType.SCENE_SETTINGS;
	}

	@Override
	public void disposeScene() 
	{
		this.detachSelf();
		this.dispose();
	}



}
