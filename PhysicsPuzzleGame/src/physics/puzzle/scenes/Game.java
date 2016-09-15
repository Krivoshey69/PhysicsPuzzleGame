package physics.puzzle.scenes;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import physics.puzzle.games.MainActivity;
import physics.puzzle.managers.Resources;
import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Storage;
import physics.puzzle.managers.Scenes.SceneType;


import android.content.Context;
import android.content.IntentSender.SendIntentException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Telephony.Sms.Conversations;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.LineJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;


public class Game extends Base implements IOnSceneTouchListener,  IOnAreaTouchListener
{
	protected static int CAMERA_WIDTH = 720;
	protected static int CAMERA_HEIGHT = 480;
	public static int LEVEL = 1;
	private static float _width, _height;
	private static final String TAG = "myLogs";
	private PhysicsWorld mPhysicsWorld;
	private int mFaceCount = 0;
	private SensorManager mSensorManager;
	private float mGravityX;
	private float mGravityY = 0.0f;

	private AnimatedSprite _face1;
	private List<Integer> elementsToBeDestroyed = new ArrayList<Integer>();
	private List<IAreaShape> elements;
	private String mCircle = "";
	private boolean isFly = false;
	private TMXTiledMap mTMXTiledMap;
	private PhysicsEditorShapeLibrary physicsEditorShapeLibrary;
	private HUD _hud;
	private boolean youWin = false, touchArea = true, touchScreen = false, repetition=false;
	private Sprite _Win, _GameOver;
	private Text text;
	private Body LineJointBodyA, LineJointBodyB, CircleBody;
	private float corX, corY, corX2, corY2, spriteWidth, spriteHeight;
	private Sprite spriteBoxJointA;
	private boolean ss = true, ss2 = true;
	private int musicInt , soundInt;
	
	@Override
	public void createScene() 
	{
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		Sprite _SpriteFon1 = new Sprite(0, 0, _Resources._Fon, _Resources._VBO);
		_SpriteFon1.setSize(GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
        autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, _SpriteFon1));
		setBackground(autoParallaxBackground);
		
		elements = new ArrayList<IAreaShape>();
		mCircle = "circleBody";
		CAMERA_WIDTH = _MainActivity._Width;
		CAMERA_HEIGHT = _MainActivity._Height;
		_width = _MainActivity._Camera_Width;
		_height = _MainActivity._Camera_Height;
		musicInt=Storage.Get("music");soundInt=Storage.Get("sound");
		Log.d(TAG, "musicInt="+musicInt+" soundInt="+soundInt);
		_hud = new HUD();
		this._Engine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
                    //updateSpritePosition();            
            }

            public void reset() {
                    // TODO Auto-generated method stub
            }
    });
		setOnSceneTouchListener(this);
		setOnAreaTouchListener(this);
		
		this.mPhysicsWorld = new FixedStepPhysicsWorld(59, new Vector2(0, SensorManager.GRAVITY_EARTH), false, 3, 2)
		{
			 @Override
			    public void onUpdate(float pSecondsElapsed) {
			        super.onUpdate(pSecondsElapsed); 
			        for (Integer i: elementsToBeDestroyed) {
			        	//removeFace(i);
	                }
	                elementsToBeDestroyed.clear();
	               
			    }
		};
		this.mPhysicsWorld.setContactListener(createContactListener());

		loadLevel(LEVEL);
		createSpriteWinandGameOver();
		createTiledSprite();
		text = new Text(GameConstants.CAMERA_WIDTH/2, 0, _Resources.font, "5", _Resources._VBO);
		text.setVisible(false);
		attachChild(text);

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
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() 
	{
		_MainActivity.runOnUpdateThread(new Runnable() {
		    @Override
		    public void run() {
		    	detachChildren();
		    }
		});
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		if(this.mPhysicsWorld != null) 
		{
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				//_SmoothCamera.setZoomFactor(0.5f);
				if(touchScreen)
				{
					if(repetition){disposeScene();Scenes.getInstance().CreateGameScene(_Engine);}
					else{LEVEL++;
						if(LEVEL==19){LEVEL=1;}
						if(LEVEL>Constants.GameLevel){
							int i = LEVEL;
							Constants.GameLevel=LEVEL;
							Log.d(TAG, ""+Constants.GameLevel);
							}
					disposeScene();
					Scenes.getInstance().CreateGameScene(_Engine);}
				}
				break;
			case TouchEvent.ACTION_UP:
				//_SmoothCamera.setZoomFactor(1.0f);
				break;
		}
		return true;
		}
		return false;
	}
	
	private void loadLevel(int x)
	{
		try {
			final TMXLoader tmxLoader = new TMXLoader(this._MainActivity.getAssets(), _MainActivity.getTextureManager(), TextureOptions.DEFAULT, _Resources._VBO) {
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					/* We are going to count the tiles that have the property "cactus=true" set. */
					
				}
			};
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/map"+x+".tmx");
			//Toast.makeText(this, "Cactus count in this TMXTiledMap: " + this.mCactusCount, Toast.LENGTH_LONG).show();
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}

		for (int i = 0; i < this.mTMXTiledMap.getTMXLayers().size(); i++) {
			TMXLayer layer = this.mTMXTiledMap.getTMXLayers().get(i);
			attachChild(layer);
			if(i==0)
			{
				//_BoundCamera.setBounds(0, 0, layer.getWidth(), layer.getHeight());
				//_BoundCamera.setBoundsEnabled(true);
			}
		}
		
		for (final TMXObjectGroup group : this.mTMXTiledMap
				.getTMXObjectGroups()) {
			for (final TMXObject object : group.getTMXObjects()) {
				if(group.getName().equals("object"))
				{
					final IAreaShape rect = new Rectangle(object.getX(),
							object.getY(), object.getWidth(),
							object.getHeight(), _Resources._VBO);
					final FixtureDef wallFixtureDef2 = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
					Body mBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, rect, BodyType.StaticBody, wallFixtureDef2);
					rect.setVisible(false);
					mBody.setUserData("object");
					rect.setUserData(mBody);
					attachChild(rect);	
				}
				
				if(group.getName().equals("object2"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Object2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "34", "Graphics/object2.xml", true, "object2");	
				}
				
				if(group.getName().equals("object3"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Object3, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "35", "Graphics/object3.xml", true, "object3");	
				}
				
				if(group.getName().equals("rectangle1"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle1, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood012", "Graphics/123.xml", true, "rectangle1");		
				}
				if(group.getName().equals("rectangleStatic"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleStatic, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementMetal012", "Graphics/rectangleStatic.xml", false, "rectangleStatic");		
				}
				if(group.getName().equals("rectangleDensity"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle1, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood012", "Graphics/rectangleDensity.xml", true, "rectangleDensity");		
				}
				
				if(group.getName().equals("rectangleVertical"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleVertical, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood019", "Graphics/rectangleVertical.xml", true, "rectangleVertical");		
				}
				if(group.getName().equals("rectangle1DensityVertical"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleVertical, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood019", "Graphics/rectangle1DensityVertical.xml", true, "rectangle1DensityVertical");		
				}
				if(group.getName().equals("rectangle2"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood018", "Graphics/rectangle2.xml", true, "rectangle2");		
				}
				if(group.getName().equals("rectangle2Density"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood018", "Graphics/rectangle2Density.xml", true, "rectangle2Density");		
				}
				if(group.getName().equals("rectangle2DensityVertical"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle2Vertical, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood020", "Graphics/rectangle2DensityVertical.xml", true, "rectangle2DensityVertical");		
				}
				if(group.getName().equals("rectangle2Vertical"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood020", "Graphics/rectangle2Vertical.xml", true, "rectangle2Vertical");		
				}
				if(group.getName().equals("rectangle2Static"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Rectangle2Static, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementMetal019", "Graphics/rectangle2Static.xml", false, "rectangle2Static");		
				}
				if(group.getName().equals("box2"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood017", "Graphics/box2.xml", true, "box2");		
				}
				if(group.getName().equals("box2Density"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood017", "Graphics/box2Density.xml", true, "box2Density");		
				}
				if(group.getName().equals("box2Static"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box2Static, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementMetal018", "Graphics/box2Static.xml", false, "box2Static");		
				}
				
				
				if(group.getName().equals("triangle"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Triangle, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood005", "Graphics/triangle.xml", true, "triangle");
				}
				if(group.getName().equals("triangleDensity"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Triangle, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood005", "Graphics/triangleDensity.xml", true, "triangleDensity");
				}
				
				if(group.getName().equals("apple2"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Apple2, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "apple2", "Graphics/apple2.xml", false, "apple2");
				}
				
				if(group.getName().equals("circle"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Circle, _Resources._VBO);
					sprite1.setSize(64, 64);
					final FixtureDef wallFixtureDef2 = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
					final Body mBody = PhysicsFactory.createCircleBody(mPhysicsWorld, sprite1, BodyType.DynamicBody, wallFixtureDef2);
					registerTouchArea(sprite1);
							attachChild(sprite1);
					this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite1, mBody, true, true));
				}
				if(group.getName().equals("circleStatic"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Circle, _Resources._VBO);
					sprite1.setSize(64, 64);
					final FixtureDef wallFixtureDef2 = PhysicsFactory.createFixtureDef(0.0f, 0.5f, 0.5f);
					final Body mBody = PhysicsFactory.createCircleBody(mPhysicsWorld, sprite1, BodyType.DynamicBody, wallFixtureDef2);
					registerTouchArea(sprite1);
							attachChild(sprite1);
					this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite1, mBody, true, true));
				}
				if(group.getName().equals("box1"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box1, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood010", "Graphics/321.xml", true, "box1");	
				}
				if(group.getName().equals("box1Density"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box1, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood010", "Graphics/box1Density.xml", true, "box1Density");	
				}
				if(group.getName().equals("boxStatic"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box1Static, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementMetal011", "Graphics/boxStatic.xml", false, "boxStatic");	
				}
				if(group.getName().equals("box1Static2"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._Box1, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood010", "Graphics/box1Static2.xml", true, "box1Static2");	
				}
				if(group.getName().equals("boxSmallDensity"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._BoxSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood010small", "Graphics/boxSmallDensity.xml", true, "boxSmallDensity");	
				}
				if(group.getName().equals("boxSmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._BoxSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood010small", "Graphics/boxSmall.xml", true, "boxSmall");	
				}
				if(group.getName().equals("rectangleSmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood012Small", "Graphics/rectangleSmall.xml", true, "rectangleSmall");	
				}
				if(group.getName().equals("rectangleDensitySmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood012Small", "Graphics/rectangleDensitySmall.xml", true, "rectangleDensitySmall");	
				}
				if(group.getName().equals("rectangleStaticSmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleStaticSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementMetal013Small", "Graphics/rectangleStaticSmall.xml", false, "rectangleStaticSmall");	
				}
				if(group.getName().equals("rectangleVerticalSmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleVerticalSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood019Small", "Graphics/rectangleVerticalSmall.xml", true, "rectangleVerticalSmall");	
				}
				if(group.getName().equals("rectangleVerticalDensitySmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleVerticalSmall, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementWood019Small", "Graphics/rectangleVerticalDensitySmall.xml", true, "rectangleVerticalDensitySmall");	
				}
				if(group.getName().equals("rectangleStone"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._RectangleStone, _Resources._VBO);
					sprite1.setSize(object.getWidth(), object.getHeight());
					createTriangleBody(mPhysicsWorld, sprite1, "elementStone013", "Graphics/rectangleStone.xml", true, "rectangleStone");	
				}
				if(group.getName().equals("circleSmall"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._CircleSmall, _Resources._VBO)
					{
					@Override
					protected void onManagedUpdate(final float pSecondsElapsed)
					{
						super.onManagedUpdate(pSecondsElapsed);
						CircleBody.applyTorque(-10f);
						CircleBody.setAngularVelocity( Math.min(
								CircleBody.getAngularVelocity(),0.2f));
					}
					};
					sprite1.setSize(32, 32);
					final FixtureDef wallFixtureDef2 = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
					this.CircleBody = PhysicsFactory.createCircleBody(mPhysicsWorld, sprite1, BodyType.DynamicBody, wallFixtureDef2);
					registerTouchArea(sprite1);
							attachChild(sprite1);
					this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite1, CircleBody, true, true));
				}
				if(group.getName().equals("boxJointA"))
				{
					corX = object.getX(); corY = object.getY();
					spriteWidth = object.getWidth(); spriteHeight = object.getHeight();
					spriteBoxJointA = new Sprite(object.getX(),
							object.getY(), _Resources._BoxJointA, _Resources._VBO);
					spriteBoxJointA.setSize(object.getWidth(), object.getHeight());
					final FixtureDef wallFixtureDef2 = PhysicsFactory.createFixtureDef(10f, 0.2f, 0.5f);
					this.LineJointBodyA = PhysicsFactory.createBoxBody(mPhysicsWorld, spriteBoxJointA, BodyType.KinematicBody, wallFixtureDef2);
				}
				if(group.getName().equals("circleJointB"))
				{
					final Sprite sprite1 = new Sprite(object.getX(),
							object.getY(), _Resources._CircleJointB, _Resources._VBO){
						@Override
						protected void onManagedUpdate(float pSecondsElapsed) {
						    super.onManagedUpdate(pSecondsElapsed);
						}
					};
					sprite1.setSize(object.getWidth(), object.getHeight());
					createJointPhysics(sprite1);
				}
			}
		}
		
		registerUpdateHandler(this.mPhysicsWorld);
	}
	
	private void createTriangleBody(final PhysicsWorld pPhysicsWorld, final Sprite sprite, final String shape, final String link, final 
			boolean isT, String nameBody) {
		/* Remember that the vertices are relative to the center-coordinates of the Shape. */
		this.physicsEditorShapeLibrary = new PhysicsEditorShapeLibrary();
		this.physicsEditorShapeLibrary.open(_MainActivity, link);
		Body mBody = this.physicsEditorShapeLibrary.createBody(shape, sprite, pPhysicsWorld);
		if(isT){registerTouchArea(sprite);}
		mBody.setUserData(nameBody.toString());
		sprite.setUserData(mBody);
		attachChild(sprite);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, mBody, true, true));
	}
	
	private void removeFace(final Sprite sprite) {
		final PhysicsConnector facePhysicsConnector = this.mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(sprite);
     
		this.mPhysicsWorld.unregisterPhysicsConnector(facePhysicsConnector);
		this.mPhysicsWorld.destroyBody(facePhysicsConnector.getBody());

		unregisterTouchArea(sprite);
		detachChild(sprite);
		
		System.gc();
	}
	
	
	
	private ContactListener createContactListener() {
	    ContactListener contactListener = new ContactListener() {
	    	 @Override
		        public void beginContact(Contact pContact) {
		            final Fixture fixtureA = pContact.getFixtureA();
		            final Body bodyA = fixtureA.getBody();
		            final String userDataA = (String) bodyA.getUserData();
		            final Fixture fixtureB = pContact.getFixtureB();
		            final Body bodyB = fixtureB.getBody();
		            final String userDataB = (String) bodyB.getUserData();   
		            
		            if (("apple2".equals(userDataA) && "object".equals(userDataB)) || ("object".equals(userDataA) && "apple2".equals(userDataB))) {
		            	if(ss2)
		            	{
		            		if(soundInt==1){_Resources._ExamplesS3.play();}
		            	youWin = false;
		            	text.setVisible(false);
		            	moveSprite(_GameOver);
		            	repetition=true; ss2 = false; ss= false;
		            	}
		            }
		            if (("apple2".equals(userDataA) && "boxStatic".equals(userDataB)) || ("boxStatic".equals(userDataA) && "apple2".equals(userDataB))) {
		            	if(ss){
		            		youWin=true;	
			            	time();	
			            	ss=false; repetition=false;
		            	}
		            }
		            if (("apple2".equals(userDataA) && "box2Static".equals(userDataB)) || ("box2Static".equals(userDataA) && "apple2".equals(userDataB))) {
		            	if(ss){
		            		youWin=true;	
			            	time();	
			            	ss=false;
		            	}
		            }
                    if (("apple2".equals(userDataA) && "rectangle2Static".equals(userDataB)) || ("rectangle2Static".equals(userDataA) && "apple2".equals(userDataB))) {
                    	if(ss){
		            		youWin=true;	
			            	time();
			            	ss=false;
		            	}
		            }
                    if (("apple2".equals(userDataA) && "rectangleStatic".equals(userDataB)) || ("rectangleStatic".equals(userDataA) && "apple2".equals(userDataB))) {
                    	if(ss){
		            		youWin=true;	
			            	time();
			            	ss=false;
		            	}
		            }
                    if (("apple2".equals(userDataA) && "rectangleStaticSmall".equals(userDataB)) || ("rectangleStaticSmall".equals(userDataA) && "apple2".equals(userDataB))) {
                    	if(ss){
		            		youWin=true;	
			            	time();
			            	ss=false;
		            	}
		            }
		        }
		 
		        @Override
		        public void endContact(Contact contact) {
		        	final Fixture fixtureA = contact.getFixtureA();
		            final Body bodyA = fixtureA.getBody();
		            final String userDataA = (String) bodyA.getUserData();
		            final Fixture fixtureB = contact.getFixtureB();
		            final Body bodyB = fixtureB.getBody();
		            final String userDataB = (String) bodyB.getUserData(); 
		           
		        }
		 
		        @Override
		        public void preSolve(Contact contact, Manifold oldManifold) {
		        }
		 
		        @Override
		        public void postSolve(Contact contact, ContactImpulse impulse) {
		        }

			
	       
	    };
	    return contactListener;
	}                   
	
	


	/*@Override
	public void onSensorChanged(SensorEvent event) 
	{
		
		 synchronized (this) {
             switch (event.sensor.getType()) 
             {
             case Sensor.TYPE_ACCELEROMETER:
            	this.mGravityX = event.values[1];
         		//this.mGravityY = event.values[0];
                     break;
             }
     }
	}*/


	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) 
	{
		if(pSceneTouchEvent.isActionDown()) {
			if(touchArea)
			{
			if(soundInt==1){_Resources._ExamplesS.play();}
			final Sprite face = (Sprite) pTouchArea;
			removeFace(face);
			
			return true;
			}
		}
		return false;
	}


	private void jumpFace(final AnimatedSprite face) {
		final Body faceBody = (Body)face.getUserData();
		//Log.d(TAG, "mGravityX="+this.mGravityX+" mGravityY="+this.mGravityY);
		//Log.d(TAG, "mGravityX*="+this.mGravityX*-50+" mGravityY*="+this.mGravityY*-50);
		final Vector2 velocity = Vector2Pool.obtain(0, this.mGravityY*-0.9f);
		faceBody.setLinearVelocity(velocity);
		Vector2Pool.recycle(velocity);
		//isFly=true;
	}

	
	  private void updateSpritePosition() {
          if ((this.mGravityX != 0) || (this.mGravityY != 0)) 
          {
        	final Vector2 gravity = Vector2Pool.obtain(this.mGravityX*9, this.mGravityY);
       		this.mPhysicsWorld.setGravity(gravity);
       		Vector2Pool.recycle(gravity);
       		
          }
  }
	  
	  private void time()
	  {
		  
		 		  _MainActivity.runOnUiThread(new Runnable() {
			    @Override
			    public void run() {
			    	 int x = 5;
			    	 text.setVisible(true);
			    	for(int i=0;i<5;i++)
			    	{
			       try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       x--;
			       text.setText(""+(x));
			    	}
			    	if(youWin){
			    	if(soundInt==1){_Resources._ExamplesS2.play();}
			    	moveSprite(_Win);repetition = false;ss2=false;
			    	}
			    }
			});
	  }
	private void createSpriteWinandGameOver()
	{
		float _w = GameConstants.CAMERA_WIDTH/3; float _h = GameConstants.CAMERA_HEIGHT/2;
		float _seredinaWidht = (GameConstants.CAMERA_WIDTH/2) - (_w/2); float seredinaHeight = (GameConstants.CAMERA_HEIGHT/2) - (_h/2);
		_Win = new Sprite(_seredinaWidht, -_h, _Resources._Win, _Resources._VBO);
		_Win.setSize(_w, _h);
		
		_GameOver = new Sprite(_seredinaWidht, -_h, _Resources._GameOver, _Resources._VBO);
		_GameOver.setSize(_w, _h);
		attachChild(_Win);
		attachChild(_GameOver);
	}
	
	private void moveSprite(Sprite sprite)
	{
		float _w = GameConstants.CAMERA_WIDTH/3; float _h = GameConstants.CAMERA_HEIGHT/2;
		float _seredinaWidht = (GameConstants.CAMERA_WIDTH/2) - (_w/2); float seredinaHeight = (GameConstants.CAMERA_HEIGHT/2) - (_h/2);
		sprite.registerEntityModifier(
				new MoveModifier(0.5f, sprite.getX(),
						_seredinaWidht, sprite.getY(), seredinaHeight) );
		touchArea=false; touchScreen=true;
	}
	
	private void createTiledSprite()
	{
		
		TiledSprite mSoundMusic = new TiledSprite(60, 0, _Resources._butSound, _Resources._VBO)
		{
			@Override
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	    	{
		  	            if(pSceneTouchEvent.isActionDown())
		  	            {
		  	            	if(soundInt==1)
		  	            	{
		  	            		soundInt = 0;
		  	            	    this.setCurrentTileIndex(soundInt);
		  	            	    Storage.Add("sound", soundInt);
		  	            	}
		  	            	else if(soundInt==0)
		  	            	{
		  	            		soundInt = 1;
			  	            	this.setCurrentTileIndex(soundInt);
			  	            	Storage.Add("sound", soundInt);
		  	            	}
		  	            	return true;
		  	            }

						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    	}
		};
		registerTouchArea(mSoundMusic);
		mSoundMusic.setCurrentTileIndex(soundInt);
		mSoundMusic.setSize(60, 60);
		
		TiledSprite mMusicSound = new TiledSprite(120, 0, _Resources._butMusic, _Resources._VBO)
		{
			@Override
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	    	{
		  	            if(pSceneTouchEvent.isActionDown())
		  	            {
		  	            	if(musicInt==1)
		  	            	{
		  	            	musicInt = 0;
		  	            	this.setCurrentTileIndex(musicInt);
		  	            	Resources.getInstance()._ExampleM.pause();
		  	            	Log.d(TAG, "musicInt="+musicInt);
		  	            	Storage.Add("music", musicInt);
		  	            	}
		  	            	else if(musicInt==0)
		  	            	{
		  	            		musicInt = 1;
			  	            	this.setCurrentTileIndex(musicInt);
			  	            	Resources.getInstance()._ExampleM.setVolume(0.5f);
			  	            	Resources.getInstance()._ExampleM.play();
			  	            	Log.d(TAG, "musicInt="+musicInt);
			  	            	Storage.Add("music", musicInt);
		  	            	}
		  	            	return true;
		  	            }

						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    	}
		};
		registerTouchArea(mMusicSound);
		mMusicSound.setCurrentTileIndex(musicInt);
		mMusicSound.setSize(60, 60);
		
		ButtonSprite buttonMenu = new ButtonSprite(0, 0, _Resources._butMenu, _Resources._VBO)
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
		  	        	    Scenes.getInstance().CreateMenuScene(_Engine);
		  	            	return true;
		  	            }
						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    	}
		};
		buttonMenu.setSize(60, 60);
		registerTouchArea(buttonMenu);
		ButtonSprite buttonRest = new ButtonSprite(180, 0, _Resources._butRest, _Resources._VBO)
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
	  	        	    disposeScene();Scenes.getInstance().CreateGameScene(_Engine);
	  	            	return true;
	  	            }

						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	    	}
		};
		buttonRest.setSize(60, 60);
		registerTouchArea(buttonRest);
		attachChild(buttonRest);
		attachChild(buttonMenu);
		attachChild(mSoundMusic);
		attachChild(mMusicSound);
	}
	
	private void createJointPhysics(Sprite sprite)
	{
		final FixtureDef wallFixtureDef2 = PhysicsFactory.createFixtureDef(10f, 0.2f, 0.5f);
		this.LineJointBodyB = PhysicsFactory.createCircleBody(mPhysicsWorld, sprite, BodyType.DynamicBody, wallFixtureDef2);
		
		final Line connectionLine = new Line(corX+spriteWidth/2, corY+spriteHeight/2, corX+spriteWidth/2, corY+spriteHeight/2, 
				_Resources._VBO);
		attachChild(connectionLine);
		attachChild(sprite);
		attachChild(spriteBoxJointA);
		
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteBoxJointA, LineJointBodyA, true, true){
			@Override
			public void onUpdate(final float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				final Vector2 movingBodyWorldCenter = LineJointBodyB.getWorldCenter();
				connectionLine.setPosition(connectionLine.getX1(), connectionLine.getY1(), movingBodyWorldCenter.x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, movingBodyWorldCenter.y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			}
		});
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, LineJointBodyB, true, true));
		
		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(LineJointBodyA, LineJointBodyB, LineJointBodyA.getWorldCenter());
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.motorSpeed = 1f;
		revoluteJointDef.maxMotorTorque = 300f;
		
		this.mPhysicsWorld.createJoint(revoluteJointDef);
	}
}
