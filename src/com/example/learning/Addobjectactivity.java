package com.example.learning;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

public class Addobjectactivity extends Activity
{
    private Addobject add_obj;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addobj);
	add_obj=(Addobject)findViewById(R.id.drawing);

    	/*Forglobalvars app_obj = ((Forglobalvars)getApplicationContext());
	for(int i=0;i<app_obj.maxN;i++)
	{
		app_obj.coords[i]=app_obj.junk;
	}
	app_obj.coords[0]  =    0.33f;       app_obj.coords[1]  = 0.00f;
	app_obj.coords[2]  =	0.66f;       app_obj.coords[3]  = 0.00f;
	app_obj.coords[4]  =	1.00f;       app_obj.coords[5]  = 0.33f;
	app_obj.coords[6]  =	1.00f;       app_obj.coords[7]  = 0.66f;
	app_obj.coords[8]  =	0.66f;       app_obj.coords[9]  = 1.00f;
	app_obj.coords[10] =	0.33f;      app_obj.coords[11]  = 1.00f;
	app_obj.coords[12] =	0.00f;      app_obj.coords[13]  = 0.66f;
	app_obj.coords[14] =	0.0f;       app_obj.coords[15]  = 0.33f;*/
    }

}
