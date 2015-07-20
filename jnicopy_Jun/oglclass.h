#ifndef OGLCLASS_H
#define OGLCLASS_H
#include<GL/glut.h>
#include<iostream>
#include<fstream>

class oglclass
{
	public:

	 static int glargc;
	 static int np_per_tri,ndim;
	 static int ntri,nedges;
	 
	 static double *trivertices;
	 static double *edgevertices;
	 static double *fielddata;
	 
	 static double xmin,xmax,ymin,ymax;
 	 
	 static char **glargv;	 

	 static void assign(int n_tri,int n_edges,double *trivert,double *edgevert,
			 double *field,int nppertri,int dim,double box_xmin,double box_xmax,
			 double box_ymin,double box_ymax);

	 static void displaytri();
	 static void glrender();
};
#endif
