package com.example.learning;
import android.app.Application;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.View;

public class learningact extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	View drawButton= findViewById(R.id.Draw_button);
	View addButton = findViewById(R.id.Add_button);
	drawButton.setOnClickListener(this);
	addButton.setOnClickListener(this);
    }
    

    public void onClick(View thisView)
    {
    	switch(thisView.getId())
	{
		case R.id.Draw_button:
			Intent showOpenGL = new Intent(this,openglinterface.class);
			startActivity(showOpenGL);
			break;

		case R.id.Add_button:

			//drawView = new Addobject(this);
			//drawView.setBackgroundColor(Color.WHITE);
			//setContentView(drawView);
			Intent drawshape = new Intent(this,Addobjectactivity.class);
			startActivity(drawshape);
			break;
	}	
    }	
}
