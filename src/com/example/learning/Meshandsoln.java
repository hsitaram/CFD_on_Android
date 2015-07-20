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
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 1.0/1.1.
 */
public class Meshandsoln {

	private FloatBuffer vertexBuffer_soln;
	private FloatBuffer vertexBuffer_mesh;
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

	private void translate_and_scale()
	{
		for(int i=0;i<solnCoords.length/3;i++)
		{
			solnCoords[3*i+0]=0.5f*solnCoords[3*i+0];
			solnCoords[3*i+1]=0.5f*solnCoords[3*i+1]-0.5f;
		}
		
		for(int i=0;i<meshCoords.length/3;i++)
		{
			meshCoords[3*i+0]=0.5f*meshCoords[3*i+0];
			meshCoords[3*i+1]=0.5f*meshCoords[3*i+1]+0.5f;
		}
	}

	public void setProblemParams(int np,float[] coord) {

		solver(coord,np);
		meshCoords    = returnmesh();
		solnCoords    = returnsolnmesh();
		solnvertcolor = returnsolncolor();

		translate_and_scale();

		System.out.println("Hari "+solnvertcolor.length+" "+meshCoords.length+" "+solnCoords.length);

		for(int i=0;i<solnvertcolor.length/4;i++)
		{
			System.out.println("Hari "+ solnvertcolor[4*i]+" "+solnvertcolor[4*i+1]+" "
					+solnvertcolor[4*i+2]+" "+solnvertcolor[4*i+3]);
		}


		ByteBuffer bb0 = ByteBuffer.allocateDirect(meshCoords.length * 4);
		bb0.order(ByteOrder.nativeOrder());
		vertexBuffer_mesh = bb0.asFloatBuffer();
		vertexBuffer_mesh.put(meshCoords);
		vertexBuffer_mesh.position(0);
		
		ByteBuffer bb1 = ByteBuffer.allocateDirect(solnCoords.length * 4);
		bb1.order(ByteOrder.nativeOrder());
		vertexBuffer_soln = bb1.asFloatBuffer();
		vertexBuffer_soln.put(solnCoords);
		vertexBuffer_soln.position(0);
		
		ByteBuffer clb = ByteBuffer.allocateDirect(solnvertcolor.length * 4);
		clb.order(ByteOrder.nativeOrder());
		colorBuffer = clb.asFloatBuffer();
		colorBuffer.put(solnvertcolor);
		colorBuffer.position(0);
	}

	public void drawmesh(GL10 gl) {
		// Since this shape uses vertex arrays, enable them
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glVertexPointer( COORDS_PER_VERTEX,
				GL10.GL_FLOAT, 0, vertexBuffer_mesh);

		gl.glDrawArrays(GL10.GL_LINES, 0,
				meshCoords.length / COORDS_PER_VERTEX);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	public void drawsoln(GL10 gl) {
		
		// Since this shape uses vertex arrays, enable them
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glColorPointer(COLOR_PER_VERTEX,GL10.GL_FLOAT,0,colorBuffer);
		gl.glVertexPointer( COORDS_PER_VERTEX,
				GL10.GL_FLOAT, 0, vertexBuffer_soln);

		gl.glDrawArrays(GL10.GL_TRIANGLES, 0,
				solnCoords.length / COORDS_PER_VERTEX);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
