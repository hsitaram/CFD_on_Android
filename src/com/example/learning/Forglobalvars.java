package com.example.learning;

import android.app.Application;

public class Forglobalvars extends Application
{
	public int nsurfpoints=0;
	public static final int maxN=500;
	public static final int maxNp=maxN/2;
	public static final float junk=300000000.0f;
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
