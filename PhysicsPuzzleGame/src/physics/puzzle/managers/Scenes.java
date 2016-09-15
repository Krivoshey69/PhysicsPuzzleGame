package physics.puzzle.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import physics.puzzle.scenes.Base;
import physics.puzzle.scenes.Game;
import physics.puzzle.scenes.Loading;
import physics.puzzle.scenes.Menu;
import physics.puzzle.scenes.Settings;
import physics.puzzle.scenes.Splash;
import physics.puzzle.scenes.Upgrades;




public class Scenes 
{
	private static final Scenes INSTANCE = new Scenes();
	public SceneType _CurrentSceneType = SceneType.SCENE_SPLASH; 
	private Engine _Engine = Resources.getInstance()._Engine;
	public Base
	_SplashScene,// Red
	_MenuScene,// Blue
	_LoadingScene,// Gray
	_UpgradesScene,// Yellow
	_SettingsScene,// Brown
	_GameScene,// Green
	_CurrentScene;
	public enum SceneType
	{
		SCENE_SPLASH, SCENE_MENU, SCENE_LOADING, SCENE_UPGRADES, SCENE_SETTINGS, SCENE_GAME
	}
	public void setScene(Base _Scene)
	{
		_Engine.setScene(_Scene);
		_CurrentScene = _Scene;
		_CurrentSceneType = _Scene.getSceneType();
	}
	public static Scenes getInstance()
	{
		return INSTANCE;
	}
	public SceneType getCurrentSceneType()
	{
		return _CurrentSceneType;
	}
	public Base getCurrentScene()
	{
		return _CurrentScene;
	}
	
	public void setScene(SceneType _SceneType)
	{
		switch(_SceneType)
		{
		   case SCENE_SPLASH:setScene(_SplashScene);break;
		   case SCENE_MENU:setScene(_MenuScene);break;
		   case SCENE_LOADING:setScene(_LoadingScene);break;
		   case SCENE_SETTINGS:setScene(_SettingsScene);break;
		   case SCENE_UPGRADES:setScene(_UpgradesScene);break;
		   case SCENE_GAME:setScene(_GameScene);break;
		   default:break;
		}
	}
	
	public void CreateSplashScene(OnCreateSceneCallback _OCSC)
	{
		Resources.getInstance().CreateSplashResources();
		_SplashScene = new Splash();
		_CurrentScene = _SplashScene;
		_OCSC.onCreateSceneFinished(_SplashScene);
	}
	public void CreateMenuScene(final Engine mEngine)
	{
		if(_SplashScene != null){Resources.getInstance().DestroySplashResources();}
		if(_LoadingScene != null){Resources.getInstance().DestroyLoadingResources();}
		if(_SettingsScene != null){Resources.getInstance().DestroySettingsResources();}
		if(_UpgradesScene != null){Resources.getInstance().DestroyUpgradesResources();}
		if(_GameScene != null){Resources.getInstance().DestroyGameResources();_GameScene.disposeScene();}
		if(Resources.getInstance()._ExampleM == null){Resources.getInstance().loadGameAudio();}
		if(Resources.getInstance()._ExamplesS == null&&Resources.getInstance()._ExamplesS2==null&&
				Resources.getInstance()._ExamplesS3==null){Resources.getInstance().loadGameSound();}
		Resources.getInstance().CreateMenuResources();
		_MenuScene = new Menu();
		 setScene(_MenuScene);
		_CurrentScene = _MenuScene;
		
	}
	public void CreateLoadingScene(final Engine mEngine)
	{
		if(_SplashScene != null){Resources.getInstance().DestroySplashResources();}
		if(_MenuScene != null){Resources.getInstance().DestroyMenuResources();}
		if(_SettingsScene != null){Resources.getInstance().DestroySettingsResources();}
		if(_UpgradesScene != null){Resources.getInstance().DestroyUpgradesResources();}
		if(_GameScene != null){Resources.getInstance().DestroyGameResources();}
		Resources.getInstance().CreateLoadingResources();
		_LoadingScene = new Loading();
		 setScene(_LoadingScene);
		_CurrentScene = _LoadingScene;
		
	}
	public void CreateSettingsScene(final Engine mEngine)
	{
		if(_MenuScene != null){Resources.getInstance().DestroyMenuResources();}
		Resources.getInstance().CreateSettingsResources();
		_SettingsScene = new Settings();
		setScene(_SettingsScene);
	}
	public void CreateUpgradeScene(final Engine mEngine)
	{
		if(_MenuScene != null){Resources.getInstance().DestroyMenuResources();}
		Resources.getInstance().CreateUpgradesResources();
		_UpgradesScene = new Upgrades();
		setScene(_UpgradesScene);
	}
	public void CreateGameScene(final Engine mEngine)
	{
		if(_UpgradesScene != null){Resources.getInstance().DestroyUpgradesResources();}
		if(_GameScene != null){Resources.getInstance().DestroyGameResources();}
		Resources.getInstance().CreateLoadingResources();
		_LoadingScene = new Loading();
		_CurrentScene = _LoadingScene;
		setScene(_LoadingScene);
		mEngine.registerUpdateHandler(new TimerHandler(5F, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				if(_LoadingScene != null){Resources.getInstance().DestroyLoadingResources();}
				if(_MenuScene != null){Resources.getInstance().DestroyMenuResources();}
				if(_UpgradesScene != null){Resources.getInstance().DestroyUpgradesResources();}
				Resources.getInstance().CreateGameResources();
				_GameScene = new Game();
				setScene(_GameScene);
				_CurrentScene = _GameScene;
			}
		}));
		
	}
	
	
}
