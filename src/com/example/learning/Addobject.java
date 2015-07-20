package com.example.learning;

import android.content.Context;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

public class Addobject extends View
{
	int Nfrq=4;
	int movecounter=0;
	private Path drawPath;
	private float maxWidth,maxHeight;
	private Paint drawPaint, canvasPaint;
	private int paintColor = 0xFF000000;
	private Canvas drawCanvas;
	private Bitmap canvasBitmap;
	private Context local_context;
	private Forglobalvars gobj;
	Paint paint=new Paint();
	public Addobject(Context context,AttributeSet attr) 
	{
		super(context,attr);            
		local_context=context;
		setupDrawing();
    		gobj = ((Forglobalvars) getContext().getApplicationContext());
		for(int i=0;i<gobj.maxN;i++)
		{
			gobj.coords[i]=gobj.junk;
		}
		gobj.nsurfpoints=0;
	};

	private void setupDrawing()
	{
		drawPath  = new Path();
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(2);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);

		canvasPaint = new Paint(Paint.DITHER_FLAG);

	}

	@Override
	protected void onSizeChanged(int w,int h,int oldw,int oldh)
	{
		super.onSizeChanged(w,h,oldw,oldh);
		canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}
	
		
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
		canvas.drawPath(drawPath,drawPaint);
		maxWidth = getWidth();
		maxHeight = getHeight();
	}


	public boolean onTouchEvent(MotionEvent event)
	{
		int arrsize;
		float touchX,touchY;

		touchX = event.getX();
		touchY = event.getY();

		//String text = "x = " + maxHeight + " y = " + maxWidth;
		//Toast.makeText(getContext().getApplicationContext(),text,Toast.LENGTH_SHORT).show();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				drawPath.moveTo(touchX,touchY);
				if(gobj.nsurfpoints < gobj.maxNp)
				{
					gobj.coords[2*gobj.nsurfpoints+0]=-(touchX-0.5f*maxWidth)/maxWidth; 
					gobj.coords[2*gobj.nsurfpoints+1]=-(touchY-0.5f*maxHeight)/maxHeight;
					gobj.nsurfpoints++;
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if(movecounter%Nfrq == 0)
				{
					if(gobj.nsurfpoints < gobj.maxNp)
					{	
						gobj.coords[2*gobj.nsurfpoints+0]=-(touchX-0.5f*maxWidth)/maxWidth; 
						gobj.coords[2*gobj.nsurfpoints+1]=-(touchY-0.5f*maxHeight)/maxHeight;
						gobj.nsurfpoints++;
					}
				}
				movecounter++;
				drawPath.lineTo(touchX,touchY);
				break;

			case MotionEvent.ACTION_UP:
				drawCanvas.drawPath(drawPath,drawPaint);
				drawPath.reset();
				break;
			default:
				return false;
		}

		invalidate();
		return true;
	}
}
