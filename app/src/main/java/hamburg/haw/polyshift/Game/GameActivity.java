package hamburg.haw.polyshift.Game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.gms.games.Game;

public class GameActivity extends Activity implements GLSurfaceView.Renderer {
	
	private GLSurfaceView glsv;
	private int x;
	private int y;
	private float deltaTime;
	private long lastFrameStart;
	private GameListener gameListener;
	private float touchedX;
	private float touchedY;
    public String swipedDirection;
	private int width;
	private int height;
    private GestureDetectorCompat mDetector;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setGameListener(GameListener gameListener){
		this.gameListener = gameListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		glsv = new GLSurfaceView(this);
		
		glsv.setRenderer( this );

		setContentView(glsv);

        swipedDirection = null;

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		lastFrameStart = System.nanoTime();
		if(gameListener != null){
			this.gameListener.setup(this, gl);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		this.width = width;
		this.height = height;
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		long currentFrameStart = System.nanoTime();
		deltaTime = (currentFrameStart-lastFrameStart) / 1000000000.0f;
		lastFrameStart = currentFrameStart;
		
		if(gameListener != null){
			this.gameListener.mainLoopIteration(this, gl);
		}
	}


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            touchedX = e1.getX();
            touchedY = e1.getY();
            float sensitvity_x = 50;
            float sensitvity_y = 50;

            // TODO Auto-generated method stub
            if ((e1.getX() - e2.getX()) > sensitvity_x) {
                swipedDirection = Simulation.LEFT;
            } else if ((e2.getX() - e1.getX()) > sensitvity_x) {
                swipedDirection = Simulation.RIGHT;
            } else if ((e1.getY() - e2.getY()) > sensitvity_y) {
                swipedDirection = Simulation.UP;
            } else if ((e2.getY() - e1.getY()) > sensitvity_y) {
                swipedDirection = Simulation.DOWN;
            } else {
                swipedDirection = null;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

	@Override
	protected void onPause() {
		super.onPause();
		//glsv.onPause();		
	}

	/**
	 * Called when the application is resumed. We need to
	 * also resume the GLSurfaceView.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		//glsv.onResume();		
	}
	
	public float getTouchedX() {
		return touchedX;
	}
	
	public float getTouchedY() {
		return touchedY;
	}
	
	public int getViewportWidth( )
	{
		return width;
	}

	public int getViewportHeight( )
	{
		return height;
	}
	
	public float getDeltaTime( )
	{
		return deltaTime;
	}
}
