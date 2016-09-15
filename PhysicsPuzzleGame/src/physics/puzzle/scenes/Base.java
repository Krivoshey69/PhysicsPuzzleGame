package physics.puzzle.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import physics.puzzle.games.MainActivity;
import physics.puzzle.managers.Resources;
import physics.puzzle.managers.Scenes;
import physics.puzzle.managers.Scenes.SceneType;



import android.hardware.SensorEventListener;


public abstract class Base extends Scene
{
	protected Engine _Engine;
	protected MainActivity _MainActivity;
	protected Camera _Camera;
	protected BoundCamera _BoundCamera;
	protected Resources _Resources;
	protected VertexBufferObjectManager _VBO;
	
	public Base()
	{
		this._Resources = Resources.getInstance();
		this._Engine = _Resources._Engine;
		this._MainActivity = _Resources._MainActivity;
		this._Camera = _Resources._Camera;
		this._BoundCamera = _Resources._BoundCamare;
		this._VBO = _Resources._VBO;
		createScene();
	}
	
	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();
	

}
