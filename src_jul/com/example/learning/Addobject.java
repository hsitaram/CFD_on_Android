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
	int Nfrq=10;
	int movecounter=0;
	private Path drawPath;
	private Paint drawPaint, canvasPaint;
	private int paintColor = 0xFF000000;
	private Canvas drawCanvas;
	private Bitmap canvasBitmap;
	private Context local_context;
	Paint paint=new Paint();
	public Addobject(Context context,AttributeSet attr) 
	{
		super(context,attr);            
		local_context=context;
		setupDrawing();
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
	}


	public boolean onTouchEvent(MotionEvent event)
	{
		int arrsize;
		float touchX,touchY;

		touchX = event.getX();
		touchY = event.getY();

		String text = "x = " + event.getX() + " y = " + event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				drawPath.moveTo(touchX,touchY);
		            	Toast.makeText(local_context, text, Toast.LENGTH_LONG).show();
				break;

			case MotionEvent.ACTION_MOVE:
				if(movecounter%Nfrq == 0)
				{
		            		Toast.makeText(local_context, text, Toast.LENGTH_LONG).show();
				}
				movecounter++;
				drawPath.lineTo(touchX,touchY);
				break;

			case MotionEvent.ACTION_UP:
		            	Toast.makeText(local_context, text, Toast.LENGTH_LONG).show();
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
