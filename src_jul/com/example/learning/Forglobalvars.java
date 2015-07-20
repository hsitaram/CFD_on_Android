package com.example.learning;

import android.app.Application;

public class Forglobalvars extends Application
{
	public static final int maxN=500;
	public float[] coords=new float[maxN];

	public float[] getGlobalcoord()
	{
		return coords;
	}
	public int getMaxN()
	{
		return maxN;
	}
}
