package physics.puzzle.games;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.FixedResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import physics.puzzle.managers.Resources;
import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Storage;
import physics.puzzle.scenes.Base;
import physics.puzzle.scenes.Constants;
import physics.puzzle.scenes.GameConstants;
import physics.puzzle.scenes.assist.FacebookEventObserver;
import physics.puzzle.scenes.assist.TwitterEventObserver;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.nostra13.socialsharing.common.AuthListener;
import com.nostra13.socialsharing.facebook.FacebookFacade;
import com.nostra13.socialsharing.twitter.TwitterFacade;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity
{
	
	private Camera _Camera;
	private BoundCamera  _BoundCamera;
	public static AdView adView;
	public static InterstitialAd interstitial;
	public static int _Camera_Width = 8192, _Camera_Height = 8192, _Width = 800, _Height = 480;
	public int _SIZE_Rectangle_Width, _SIZE_Rectangle_Height;
	public static final String TAG = "myLogs";
	private TMXTiledMap mTMXTiledMap;
	private TwitterFacade twitter;
	private TwitterEventObserver twitterEventObserver;
	private FacebookFacade facebook;
	private FacebookEventObserver facebookEventObserver;
	private int x = 0;
	@Override
	public EngineOptions onCreateEngineOptions() {
		final DisplayMetrics _DisplayMetrics = new DisplayMetrics();
		WindowManager _WM = (WindowManager)getSystemService(WINDOW_SERVICE);
		_WM.getDefaultDisplay().getMetrics(_DisplayMetrics);
		
		_Width = _DisplayMetrics.widthPixels;
		_Height = _DisplayMetrics.heightPixels;
		_SIZE_Rectangle_Width = _Width/4;
		_SIZE_Rectangle_Height = _Height/4;
		_Camera = new Camera(0, 0, GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
		//_BoundCamera = new BoundCamera (0, 0, GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
		twitter = new TwitterFacade(this, GameConstants.TWITTERKEY, 
      			GameConstants.TWITTERSECRET);
		twitterEventObserver = TwitterEventObserver.newInstance();
		facebook = new FacebookFacade(this, GameConstants.FACEBOOKID);
		facebookEventObserver = FacebookEventObserver.newInstance();
		printKeyHash();
		EngineOptions _EO = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), _Camera);
		_EO.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		_EO.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		_EO.getTouchOptions().setNeedsMultiTouch(true);
		FixedStepEngine _EOFIXED = new FixedStepEngine(_EO, 60);
		
		return _EO;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {

		return new FixedStepEngine(pEngineOptions, 59);
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception 
	{
		Resources.prepareManager(mEngine, this, _Camera, _BoundCamera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception 
	{
		Storage.Initializiation(this);
		//this.mEngine.registerUpdateHandler(new FPSLogger());
		Resources.getInstance().ResourcesPathsInitialization();
		Scenes.getInstance().CreateSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception 
	{
		
		mEngine.registerUpdateHandler(new TimerHandler(5F, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				Scenes.getInstance().CreateMenuScene(mEngine);	
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
    public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) 
    {
		if(Scenes.getInstance()._CurrentScene == Scenes.getInstance()._GameScene)
		 {
			 if(pKeyCode == KeyEvent.KEYCODE_BACK) 
            {
				 Scenes.getInstance().getCurrentScene().onBackKeyPressed();
				 return false;
            }
		 }
		if(Scenes.getInstance()._CurrentScene == Scenes.getInstance()._LoadingScene)
		 {
			 if(pKeyCode == KeyEvent.KEYCODE_BACK) 
           {
				 Scenes.getInstance().getCurrentScene().onBackKeyPressed();
				 return false;
           }
		 }
		if(Scenes.getInstance()._CurrentScene == Scenes.getInstance()._SplashScene)
		 {
			 if(pKeyCode == KeyEvent.KEYCODE_BACK) 
          {
				 Scenes.getInstance().getCurrentScene().onBackKeyPressed();
				 return false;
          }
		 }
		if(Scenes.getInstance()._CurrentScene == Scenes.getInstance()._UpgradesScene)
		 {
			 if(pKeyCode == KeyEvent.KEYCODE_BACK) 
         {
				 Scenes.getInstance().getCurrentScene().onBackKeyPressed();
				 return false;
         }
		 }
		return super.onKeyDown(pKeyCode, pEvent); 
    }
	
	@Override
	protected void onStart() {
		super.onStart();
		//twitterEventObserver.registerListeners(this);
		//if (!twitter.isAuthorized()) {
		//	twitter.authorize();
		//}
		//facebookEventObserver.registerListeners(this);
		//if (!facebook.isAuthorized()) {
		//	facebook.authorize();
      //  }
	}

	@Override
	protected void onStop() {
		//twitterEventObserver.unregisterListeners();
		//facebookEventObserver.unregisterListeners();
		super.onStop();
}
	
	@Override
    protected void onDestroy() 
	{
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        Storage.Add("level", Constants.GameLevel);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

	
	@Override
	public synchronized void onResumeGame() {
	if(Resources.getInstance()._ExampleM != null && !Resources.getInstance()._ExampleM.isPlaying()){
		Resources.getInstance()._ExampleM.play();
	}
	super.onResumeGame();
	}
	
	public synchronized void onPauseGame() {
		if(Resources.getInstance()._ExampleM != null && Resources.getInstance()._ExampleM.isPlaying()){
			Resources.getInstance()._ExampleM.pause();
		}
		super.onPauseGame();
		}
	
	
	
	public void displayInterstitial() 
	{
    	if (interstitial.isLoaded()) 
    	{
    	interstitial.show();
    	}
    }
	
	private void printKeyHash()
	{
		try
		{
			PackageInfo info = getPackageManager().getPackageInfo("physics.puzzle.games", PackageManager.GET_SIGNATURES);
			for(Signature signature : info.signatures){
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("myLogs", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		}
		catch (NameNotFoundException e){
			Log.e("name not found", e.toString());
	  } catch (NoSuchAlgorithmException e){
		  Log.e("no such an algorithm", e.toString());
		  
	  }
	}
	
	public void gameFacebook() {
	    this.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	if (facebook.isAuthorized()) {
	            		publishMessage();
	           	   
					} else {
						// Start authentication dialog and publish image after successful authentication
						facebook.authorize(new AuthListener() {
						
						@Override
						public void onAuthSucceed() {
							// TODO Auto-generated method stub
							publishMessage();
						}
						
						@Override
						public void onAuthFail(String arg0) {
							// TODO Auto-generated method stub
							
						}
						});
					}
	        }
	    });
	}
	
	private void publishMessage() {
		facebook.publishMessage(GameConstants.FACEBOOK_SHARE_MESSAGE, GameConstants.FACEBOOK_SHARE_LINK,
				GameConstants.FACEBOOK_SHARE_LINK_NAME, GameConstants.FACEBOOK_SHARE_LINK_DESCRIPTION, GameConstants.FACEBOOK_SHARE_PICTURE);
		facebook.logout();
	}
	
	public void gameTwitter() {
	    this.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	if (twitter.isAuthorized()) {
	            		twitter.publishMessage(GameConstants.TWITTERMESSAGE);
	           	   
					} else {
						// Start authentication dialog and publish image after successful authentication
						twitter.authorize(new AuthListener() {
						
						@Override
						public void onAuthSucceed() {
							// TODO Auto-generated method stub
							twitter.publishMessage(GameConstants.TWITTERMESSAGE);
						}
						
						@Override
						public void onAuthFail(String arg0) {
							// TODO Auto-generated method stub
							
						}
						});
					}
	        }
	    });
	}
}
