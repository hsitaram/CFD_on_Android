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
    	Forglobalvars app_obj = ((Forglobalvars)getApplicationContext());
	add_obj=(Addobject)findViewById(R.id.drawing);

	for(int i=0;i<app_obj.maxN;i++)
	{
		app_obj.coords[i]=-1.0f;
	}
    }
	
}
