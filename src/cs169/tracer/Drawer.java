package cs169.tracer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cs169.tracer.ColorPickerDialog;
import cs169.tracer.Play;

import android.app.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;

public class Drawer extends Activity implements
        ColorPickerDialog.OnColorChangedListener {

	private final int levelscore = 2500; 
	private static int strokeWidth = 6;
	public static int backgroundColor = 0xFFFFFFFF;
	private static int strokeColor = 0xFF000000;
	private static int levelColor = 0xFF000000;
	private static String CREATE_FILENAME = "tracerCreateTmp.png";
	private Bitmap userBitmap;
	private Bitmap originalBitmap;
	private MyView mView;
	// these were added
	private MyView m1View;
	private FrameLayout mFrameLayout;
	
    // override the onCreate method to make our activity

    protected void clearView() {
        mView = new MyView(this);
        setContentView(mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new MyView(0x00FFFFFF, this, 0);
        m1View = new MyView(0xFFFFFFFF, this, 1);
        //setContentView(mView);
        mFrameLayout = new FrameLayout(this);
        mFrameLayout.addView(m1View);
        mFrameLayout.addView(mView);
        setContentView(mFrameLayout);
        // initializing painting stuff
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(strokeColor);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        try {
            FileInputStream fis = openFileInput(CREATE_FILENAME);
            Bitmap ib = BitmapFactory.decodeStream(fis);
            Bitmap b = ib.copy(Bitmap.Config.ARGB_8888, true);
            if (b.getHeight() < 0 || b.getWidth() < 0) return; // For now.
            mView = new MyView(this, b);
            setContentView(mView);
            fis.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    protected void onPause() {
        super.onPause();
        
        // Save the current userBitmap to a file.
        try {
            FileOutputStream fos = openFileOutput(CREATE_FILENAME, Context.MODE_PRIVATE);
            userBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        //Delete the temporary file that we stored
        deleteFile(CREATE_FILENAME);
    }

    private Paint mPaint;


    // change the color of the paint
    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    // change the width of the brush
    // to be used in WidthPickerDialog
    public void brushWidthChanged(float width) {
        if (DELETE_FLAG) {
            brushWidth = mPaint.getStrokeWidth();
        }
        mPaint.setStrokeWidth(width);
    }
    
	protected void setUserBitmap(Bitmap b) {
		userBitmap = b;
	}
	
	public Bitmap getUserBitmap(){
	    return userBitmap;
	}
	
	public Bitmap getOriginalBitmap() {
		return originalBitmap;
	}
	
	protected void setOriginalBitmap(Bitmap b) {
		originalBitmap = b;
	}
	
	public static int getStrokeColor() {
	    return strokeColor;
	}

	//going to attempt to get framelayout to work
	
    // our new version of View
    public class MyView extends View {

        // these are the things that Painter will modify
        private Bitmap mBitmap;
        protected int mView_ID;
        
        
        /**the mBitmapOriginal is no longer needed...
        * this is because we are using two views layered 
        * on top of each other
	    **/
        //private Bitmap mBitmapOriginal;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;

        public MyView(Context c) {
            super(c);

            // initialize the fields to be used
            /**
             * mbitmap will be loaded from memory once the levels are implemented but
             * for now it loads a blank screen to be painted on
             **/
            mBitmap = Bitmap.createBitmap(480, 864, Bitmap.Config.ARGB_8888);
            //mBitmapOriginal = mBitmap.copy(Bitmap.Config.ARGB_8888, false);
            mCanvas = new Canvas(mBitmap);
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }
        
        public MyView(int bgcolor, Context c, int flag) {
            super(c);

            // initialize the fields to be used
            /**
             * mbitmap will be loaded from memory once the levels are implemented but
             * for now it loads a blank screen to be painted on
             **/
            mBitmap = Bitmap.createBitmap(480, 864, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(bgcolor);
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            mView_ID = flag;
            // this is some test code
            // it draws a circle on the underneath bitmap
            // in order to see if it gets modified by drawing on it
            if(flag == 1) {
            	mCanvas.drawCircle(150, 150, 100, mBitmapPaint);
            	Drawer thisDrawer1 =  (Drawer)this.getContext();
            	thisDrawer1.setOriginalBitmap(mBitmap);  	
            }
        }
        
        public MyView(Context c, Bitmap b) {
            super(c);
            
            mBitmap = b;
            mCanvas = new Canvas(mBitmap);
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }
        
        public void setUserBitmap(Bitmap b){
            mBitmap = b;
            mCanvas = new Canvas(b);
        }
        
        public int get_ID() {
        return mView_ID;
        }
        
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        // onDraw method creates the path background etc..
        // initializes to a white background
        @Override
        protected void onDraw(Canvas canvas) {
        	// this is no longer needed because we are using the views to initialize
            //canvas.drawColor(Drawer.backgroundColor);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }
        
        //this is a method i created to test some things
//        public void drawBg(int color) {
 //       canvas.drawColor(Drawer.backgroundColor);	
  //      }
        

        // mX and mY are parameters that are going to keep track of the motion
        // of the finger
        // the touch tolerance is a standard number (taken from APIDemo)
        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        // touch_start is a method that starts the drawing
        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        // touch_move is the function that will follow the user's finger
        // movement
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        // touch_up is a function that cleans up the drawing
        private void touch_up() {
            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        // standard override of onTouchEvent to handle the three events that
        // will take place during the drawing.
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // first get the location of the touch
            float x = event.getX();
            float y = event.getY();

            // then depending on whether the finger is pressing down, moving,
            // or lifting off, call the appropriate function
            // NOTE: invalidate() is a means of calling onDraw without
            // explicitly calling it
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                if (DELETE_FLAG) {
                    DELETE_FLAG = false;
                    mPaint.setStrokeWidth(brushWidth);
                    mPaint.setXfermode(null);
                }
                Drawer thisDrawer = (Drawer)this.getContext();
                thisDrawer.setUserBitmap(mBitmap);
                //thisDrawer.setOriginalBitmap(mBitmapOriginal);
                break;
            }
            return true;
        }
    }

    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int ERASE_MENU_ID = Menu.FIRST + 1;
    private static final int WIDTH_MENU_ID = Menu.FIRST + 2;
    private static final int DONE_MENU_ID = Menu.FIRST + 3;
    private static final int W_SMALL_ID = Menu.FIRST + 4;
    private static final int W_MED_ID = Menu.FIRST + 5;
    private static final int W_LARGE_ID = Menu.FIRST + 6;
    private static final int W_HUGE_ID = Menu.FIRST + 7;
    private static final int E_SMALL_ID = Menu.FIRST + 8;
    private static final int E_MED_ID = Menu.FIRST + 9;
    private static final int E_LARGE_ID = Menu.FIRST + 10;
    private static final int E_HUGE_ID = Menu.FIRST + 11;
    private static final int E_CLEAR_ALL_ID = Menu.FIRST + 12;

    private float brushWidth = 6;

    private boolean DELETE_FLAG = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
        // these are options that we don't need or won't need yet
        // menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 's');
        // menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'z');
        // menu.add(0, SRCATOP_MENU_ID, 0, "SrcATop").setShortcut('5', 'z');

        // adding menu items for width and done
        // not sure what the '5' or 'z' do though...

        // menu.add(0, WIDTH_MENU_ID, 0, "Width").setShortcut('5', 'z');
        menu.add(0, DONE_MENU_ID, 0, "Done").setShortcut('5', 'z');

        SubMenu eraseSubMenu = menu.addSubMenu(0, ERASE_MENU_ID, 0, "Erase");
        eraseSubMenu.add(0, E_SMALL_ID, 0, "Small");
        eraseSubMenu.add(0, E_MED_ID, 0, "Med");
        eraseSubMenu.add(0, E_LARGE_ID, 0, "Large");
        eraseSubMenu.add(0, E_HUGE_ID, 0, "Huge");
        eraseSubMenu.add(0, E_CLEAR_ALL_ID, 0, "Clear");
        // creating a submenu width that will change the size of the paint brush
        SubMenu widthSubMenu = menu.addSubMenu(0, WIDTH_MENU_ID, 0, "Width");
        widthSubMenu.add(0, W_SMALL_ID, 0, "Small");
        widthSubMenu.add(0, W_MED_ID, 0, "Med");
        widthSubMenu.add(0, W_LARGE_ID, 0, "Large");
        widthSubMenu.add(0, W_HUGE_ID, 0, "Huge");

        /****
         * Is this the mechanism to extend with filter effects? Intent intent =
         * new Intent(null, getIntent().getData());
         * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
         * menu.addIntentOptions( Menu.ALTERNATIVE, 0, new ComponentName(this,
         * NotesList.class), null, intent, 0, null);
         *****/
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    
    public double compare(Bitmap originalBitmap, Bitmap userBitmap) {
    	double score = 0.0;
    	int bitmapHeight = originalBitmap.getHeight();
    	int bitmapWidth = originalBitmap.getWidth();
    	int x, y, originalColor, userColor;
    	
    	for(x=0;x<bitmapWidth;x++) {
    		for(y=0;y<bitmapHeight;y++)  {
    			originalColor = originalBitmap.getPixel(x, y);
    			userColor = userBitmap.getPixel(x, y);
    			
    			if(originalColor-userColor<0) { // this needs to be changed to '<' once we have the images
    				if(originalColor!=backgroundColor) {
        				score++;
        				}
    			}
    			else if(originalColor-userColor >0) {
    				// score shouldnt go up down?
    				score--;
    			}
    			else {
    				// nothing happens for now
    			}
    		}
    	}
    	return score;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // note sure what these do yet
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        // so this will be where the menu items are chosen.
        switch (item.getItemId()) {
        case COLOR_MENU_ID:
            new ColorPickerDialog(this, this, mPaint.getColor()).show();
            return true;
        case WIDTH_MENU_ID:
            return true;
        case W_SMALL_ID:
            brushWidthChanged(6);
            return true;
        case W_MED_ID:
            brushWidthChanged(12);
            return true;
        case W_LARGE_ID:
            brushWidthChanged(24);
            return true;
        case W_HUGE_ID:
            brushWidthChanged(128);
            return true;
        case E_SMALL_ID:
            DELETE_FLAG = true;
            brushWidthChanged(12);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return true;
        case E_MED_ID:
            DELETE_FLAG = true;
            brushWidthChanged(18);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return true;
        case E_LARGE_ID:
            DELETE_FLAG = true;
            brushWidthChanged(24);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return true;
        case E_HUGE_ID:
            DELETE_FLAG = true;
            brushWidthChanged(128);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return true;
        case E_CLEAR_ALL_ID:
            clearView();
            return true;
        case DONE_MENU_ID:
            // TODO: implement what happens next
            // needs to return to the previous view
            // and run bitmap checking algorithm
        	
        	double score = compare(originalBitmap, userBitmap);
        	if(score<0.0){
        		score = 0.0;
        	}
        	AlertDialog a = new AlertDialog.Builder(this).create();
        	a.setMessage("Score: " + Double.toString(score));
        	
			a.setButton("OK", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int which) {
	            	 	// after we figure out the levels, this will need to be changed
	                 	//Intent return_intent = new Intent(Drawer.this, Levels.class);
	            		Intent return_intent = new Intent(Drawer.this, Drawer.class);	
	                 	startActivity(return_intent);
	                 	return;
	             }
			});
			a.setButton3("RETRY", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int which) {
	            	 	// after we figure out the levels, this will need to be changed
	                 	//Intent return_intent = new Intent(Drawer.this, Levels.class);
	            		Intent return_intent = new Intent(Drawer.this, Drawer.class);	
	                 	startActivity(return_intent);
	                 	return;
	             }
			});
			a.setButton2("LEVELS", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int which) {
	            		Intent return_intent = new Intent(Drawer.this, Levels.class);	
	                 	startActivity(return_intent);
	                 	return;
	             }
			});
        	a.show();
        	//these will probably need to be changed later
        	/*mFrameLayout.removeView(mView);
        	mFrameLayout.removeView(m1View);
            mView = new MyView(0x00000000, this, 0);
            m1View = new MyView(0xFF0000FF, this, 1);
            mFrameLayout.addView(m1View);
            mFrameLayout.addView(mView);*/
        	//need to do something next
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
