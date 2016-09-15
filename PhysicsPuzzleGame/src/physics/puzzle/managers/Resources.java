package physics.puzzle.managers;


import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import physics.puzzle.games.MainActivity;

import com.nostra13.socialsharing.facebook.FacebookFacade;


import android.util.Log;




public class Resources 
{
	private static final Resources INSTANCE = new Resources();
	public Engine _Engine;
	public MainActivity _MainActivity;
	public Camera _Camera;
	public BoundCamera _BoundCamare;
	public VertexBufferObjectManager _VBO;
	public BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	public BitmapTextureAtlas _GameAtlas1, _GameAtlas2, _MenuAtlas1, _MenuAtlas2, _SelecLevelAtlas1, _SelecLevelAtlas2
	, _GameAtlas3, _SplashAtlas1, _LoadingAtlas1, _LoadingAtlas2, _LoadingAtlas3;
	public ITextureRegion _Fon, _Box1 , _Rectangle1, _Circle, _Triangle, _MenuBackground, _NewGame, _SelectFon, _SF2,
	   _RectangleStatic, _Apple2, _Box1Static, _Box2Static, _Rectangle2Static, _Box2, _Rectangle2, _RectangleVertical,
	    _Rectangle2Vertical, _Win, _GameOver, _butMenu, _SF3, _Exit, _Retings, _Facebook, _Twitter, _Object2, _Object3,
	    _LoadingFon1, _LOadingFon2, _BoxJointA, _CircleJointB, _BoxSmall, _CircleSmall, _RectangleStaticSmall, _RectangleSmall,
	    _TriangleSmall, _RectangleVerticalSmall, _butRest, _RectangleStone;
	public TiledTextureRegion _butSound, _butMusic,
	    _LoadingTiled, _LoadingTiled2;
	public Font _ExampleF;
	public Sound _ExamplesS, _ExamplesS2, _ExamplesS3;
	public Music _ExampleM;
	public TMXTiledMap mTMXTiledMap;
	public Font font;
	
	public void ResourcesPathsInitialization()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/");
		//FontFactory.setAssetBasePath("Fonts/");
		SoundFactory.setAssetBasePath("Sounds/");
		MusicFactory.setAssetBasePath("Music/");
		
	}
	
	public void CreateSplashResources()
	{
		this._SplashAtlas1 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._LoadingTiled = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this._SplashAtlas1,
				_MainActivity, "loading.png", 0, 0, 1, 5);
		this._SplashAtlas1.load();
	}
	public void DestroySplashResources()
	{
		this._SplashAtlas1.unload();
	}
	
	public void CreateMenuResources()
	{
		
		this._MenuAtlas1 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._MenuBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._MenuAtlas1,
				_MainActivity, "back2.png", 0, 0);
		this._MenuAtlas2 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._NewGame = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._MenuAtlas2,
				_MainActivity, "newgame.png", 0, 0);
		this._Exit = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._MenuAtlas2,
				_MainActivity, "butExit.png", 257, 0);
		this._Retings = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._MenuAtlas2,
				_MainActivity, "ratings.png", 514, 0);
		this._Facebook = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._MenuAtlas2,
				_MainActivity, "facebook.png", 771, 0);
		this._Twitter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._MenuAtlas2,
				_MainActivity, "twitter.png", 0, 76);
		this._MenuAtlas1.load();
		this._MenuAtlas2.load();
		
	}
	
	public void loadGameAudio()
	{
		try {
            this._ExampleM = MusicFactory.createMusicFromAsset(_MainActivity.getMusicManager(), _MainActivity, "music.ogg");
            this._ExampleM.setLooping(true);
            } catch (final IOException e) {
            Debug.e(e);
            }
	}
	
	public void loadGameSound()
	{
		 try {
	 			this._ExamplesS = SoundFactory.createSoundFromAsset(_Engine.getSoundManager(), _MainActivity, "box.ogg");
	 		} catch (final IOException e) {
	 			Debug.e(e);
	 		}
	         
	         try {
	  			this._ExamplesS2 = SoundFactory.createSoundFromAsset(_Engine.getSoundManager(), _MainActivity, "win.ogg");
	  		} catch (final IOException e) {
	  			Debug.e(e);
	  		}
	         
	         try {
	   			this._ExamplesS3 = SoundFactory.createSoundFromAsset(_Engine.getSoundManager(), _MainActivity, "gameover.ogg");
	   		} catch (final IOException e) {
	   			Debug.e(e);
	   		}
	}
	
	public void DestroyMenuResources()
	{
		 this._MenuAtlas1.unload();
		 this._MenuAtlas2.unload();
	}
	
	public void CreateLoadingResources()
	{
		this._LoadingAtlas1 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._LoadingTiled2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this._LoadingAtlas1,
				_MainActivity, "loading.png", 0, 0, 1, 5);
		this._LoadingAtlas2 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._LoadingFon1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._LoadingAtlas2,
				_MainActivity, "loadingBackground2.png", 0, 0);
		this._LoadingAtlas3 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._LOadingFon2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._LoadingAtlas3,
				_MainActivity, "loadingBackground3.png", 0, 0);
		this._LoadingAtlas1.load();
		this._LoadingAtlas2.load();
		this._LoadingAtlas3.load();
	}
	public void DestroyLoadingResources()
	{
		 this._LoadingAtlas1.unload();
		 this._LoadingAtlas2.unload();
		 this._LoadingAtlas3.unload();
	}
	
	public void CreateSettingsResources()
	{
		
	}
	public void DestroySettingsResources()
	{
		 
	}
	
	public void CreateUpgradesResources()
	{
		this._SelecLevelAtlas1 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._SelectFon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._SelecLevelAtlas1,
				_MainActivity, "back2.png", 0, 0);
		this._SelecLevelAtlas2 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this._SF2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._SelecLevelAtlas2,
				_MainActivity, "selectlevel1.png", 0, 0);
		this._SF3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._SelecLevelAtlas2,
				_MainActivity, "selectlevel2.png", 76, 0);
		
		//FontFactory.setAssetBasePath("Fonts/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(
				_MainActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.font = FontFactory.createStrokeFromAsset(_MainActivity.getFontManager(), mainFontTexture, _MainActivity.getAssets(), "Fonts/angrybirds.ttf", 24, true, Color.WHITE.getARGBPackedInt(), 
				1, Color.WHITE.getARGBPackedInt());
         font.load();
		
		this._SelecLevelAtlas1.load();
		this._SelecLevelAtlas2.load();
	}
	public void DestroyUpgradesResources()
	{
		 this._SelecLevelAtlas1.unload();
		 this._SelecLevelAtlas2.unload();
		 font.unload();
	}
	
	public void CreateGameResources()
	{
		final ITexture mainFontTexture = new BitmapTextureAtlas(
				_MainActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.font = FontFactory.createStrokeFromAsset(_MainActivity.getFontManager(), mainFontTexture, _MainActivity.getAssets(), "Fonts/angrybirds.ttf", 48, true, Color.RED.getARGBPackedInt(), 
				1, Color.RED.getARGBPackedInt());
         font.load();

		this._GameAtlas1 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._Box1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood010.png", 451, 0);
		this._Rectangle1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood012.png", 0, 129);
		this._Circle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood000.png", 129, 129);
		this._Triangle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood005.png", 194, 129);
		this._RectangleStatic = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementMetal012.png", 323, 129);
		this._Apple2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "apple2.png", 452, 129);
		this._Rectangle2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood018.png", 485, 129);
		this._Box2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood017.png", 678, 129);
		this._Box1Static = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementMetal011.png", 807, 129);
		this._Box2Static = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementMetal018.png", 872, 129);
		this._RectangleVertical = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood019.png", 0, 258);
		this._Rectangle2Vertical = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementWood020.png", 33, 258);
		this._Rectangle2Static = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "elementMetal019.png", 65, 258);
		this._Object2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "34.png", 258, 258);
		this._Object3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas1,
				_MainActivity, "35.png", 451, 258);
		this._GameAtlas2 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._Fon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas2,
				_MainActivity, "background.png", 0, 0);
		this._GameAtlas3 = new BitmapTextureAtlas(_MainActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this._Win = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "win.png", 0, 0);
		this._GameOver = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "gameover.png", 314, 0);
		this._butSound = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this._GameAtlas3,
				_MainActivity, "butSound.png", 0, 233, 2, 1);
		this._butMusic = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this._GameAtlas3,
				_MainActivity, "butMusic.png", 101, 233, 2, 1);
		this._butMenu= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "butMenu.png", 202, 233);
		this._BoxJointA= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementStone046.png", 253, 233);
		this._CircleJointB= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementStone045.png", 286, 233);
		this._BoxSmall= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementWood010small.png", 320, 233);
		this._RectangleSmall= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementWood012Small.png", 354, 233);
		this._CircleSmall= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementWood000Small.png", 420, 233);
		this._RectangleStaticSmall= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementMetal013Small.png", 454, 233);
		this._TriangleSmall= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementWood005Small.png", 520, 233);
		this._RectangleVerticalSmall= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementWood019Small.png", 586, 233);
		this._butRest= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "butRest.png", 620, 233);
		this._RectangleStone= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this._GameAtlas3,
				_MainActivity, "elementStone013.png", 672, 233);
		this._GameAtlas1.load();
		this._GameAtlas2.load();
		this._GameAtlas3.load();
		
	}
	public void DestroyGameResources()
	{
		 this._GameAtlas1.unload();
		 this._GameAtlas2.unload();
		 this._GameAtlas3.unload();
		 this.font.unload();
	}
	public static void prepareManager(Engine _Engine, MainActivity _MainActivity, Camera _Camera, BoundCamera _BoundCamera, VertexBufferObjectManager _VBO)
	{
		getInstance()._Engine = _Engine;
		getInstance()._MainActivity = _MainActivity;
		getInstance()._Camera = _Camera;
		getInstance()._BoundCamare = _BoundCamera;
		getInstance()._VBO = _VBO;
	}
	
	public static Resources getInstance()
	{
		return INSTANCE;
	}
}
