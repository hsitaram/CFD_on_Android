/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.learning;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 1.0/1.1.
 */
public class Meshandsoln {

	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	
	private float meshCoords[];
	private float solnCoords[];
	private float solnvertcolor[];

	public native void solver(float[] arr,int npoints);
	public native float[] returnmesh();
	public native float[] returnsolnmesh();
	public native float[] returnsolncolor();
	
	static
	{
		System.loadLibrary("tridriver");	   
	}

	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 3;
	static final int COLOR_PER_VERTEX  = 4;

	public void setProblemParams(float xcent,float ycent,float len) {

		float p[];
		p = new float[16];
		p[0]  = 0.33f;       p[1]  = 0.00f;
		p[2]  =	0.66f;       p[3]  = 0.00f;
		p[4]  =	1.00f;       p[5]  = 0.33f;
		p[6]  =	1.00f;       p[7]  = 0.66f;
		p[8]  =	0.66f;       p[9]  = 1.00f;
		p[10] =	0.33f;      p[11]  = 1.00f;
		p[12] =	0.00f;      p[13]  = 0.66f;
		p[14] =	0.0f;       p[15]  = 0.33f;

		solver(p,8);
		meshCoords    = returnmesh();
		solnCoords    = returnsolnmesh();
		solnvertcolor = returnsolncolor();

		//ByteBuffer bb = ByteBuffer.allocateDirect(meshCoords.length * 4);
		ByteBuffer bb = ByteBuffer.allocateDirect(solnCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		//vertexBuffer.put(meshCoords);
		vertexBuffer.put(solnCoords);
		vertexBuffer.position(0);
		
		ByteBuffer clb = ByteBuffer.allocateDirect(solnvertcolor.length * 4);
		clb.order(ByteOrder.nativeOrder());
		colorBuffer = clb.asFloatBuffer();
		colorBuffer.put(solnvertcolor);
		colorBuffer.position(0);
	}

	public void draw(GL10 gl) {
		// Since this shape uses vertex arrays, enable them
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);
		gl.glVertexPointer( COORDS_PER_VERTEX,
				GL10.GL_FLOAT, 0, vertexBuffer);

		gl.glDrawArrays(GL10.GL_TRIANGLES, 0,
				//meshCoords.length / COORDS_PER_VERTEX);
				solnCoords.length / COORDS_PER_VERTEX);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
