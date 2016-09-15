package physics.puzzle.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Scenes.SceneType;

import android.view.View;


public class Upgrades extends Base 
{
	private LevelSelector levelSelector;
	private Scene mScene;
	@Override
	public void createScene() 
	{
		// TODO Auto-generated method stub
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		Sprite _SpriteFon1 = new Sprite(0, 0, _Resources._SelectFon, _Resources._VBO);
		_SpriteFon1.setSize(GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
        autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, _SpriteFon1));
		setBackground(autoParallaxBackground);
		
		mScene = new Scene();
		levelSelector = new LevelSelector(Constants.GameLevel, 2, GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT, this, _Engine);
		levelSelector.createTiles(_Resources._SF2, _Resources.font, _Resources._SF3);
		levelSelector.show();
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
		return SceneType.SCENE_UPGRADES;
	}

	@Override
	public void disposeScene() 
	{
		this.detachChildren();
		this.detachSelf();
		this.dispose();
	}

	

}
