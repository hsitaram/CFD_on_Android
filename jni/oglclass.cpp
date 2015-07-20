#include"oglclass.h"

int oglclass::glargc=0;
int oglclass::np_per_tri=3;
int oglclass::ndim=2;
int oglclass::ntri=0;
int oglclass::nedges=0;

double oglclass::xmin=0.0;
double oglclass::xmax=0.0;
double oglclass::ymin=0.0;
double oglclass::ymax=0.0;

double *oglclass::trivertices=NULL;
double *oglclass::edgevertices=NULL;
double *oglclass::fielddata=NULL;

char **oglclass::glargv=NULL;
//======================================================================
void oglclass::assign(int n_tri,int n_edges,
		double *trivert,double *edgevert,double *field,
		int nppertri,int dim,double box_xmin,
		double box_xmax,double box_ymin,double box_ymax)
{
	int vertlength;

	np_per_tri = nppertri;
	ndim = dim;
	ntri = n_tri;
	nedges = n_edges;

	xmin = box_xmin-(box_xmax-box_xmin)/2;
	xmax = box_xmax+(box_xmax-box_xmin)/2;

	ymin = box_ymin-(box_ymax-box_ymin)/2;
	ymax = box_ymax+(box_ymax-box_ymin)/2;
	
	vertlength=np_per_tri*ndim*ntri;
	
	trivertices = new double[vertlength];

	for(int i=0;i<vertlength;i++)
	{
		trivertices[i] = trivert[i];
	}
	
	vertlength=ndim*2*nedges;
	
	edgevertices=new double[vertlength];
	
	for(int i=0;i<vertlength;i++)
	{
		edgevertices[i] = edgevert[i];
	}
	
	vertlength=np_per_tri*ntri;
	
	fielddata   = new double[vertlength];
	
	for(int i=0;i<vertlength;i++)
	{
		fielddata[i] = field[i];
		std::cout<<"ogl field:"<<fielddata[i]<<"\n";
	}

}
//======================================================================
void oglclass::displaytri()
{
	double x,y;
	double maxfdata,minfdata;
	double grayscaleval;
	double fieldav;
	glClear(GL_COLOR_BUFFER_BIT);
	glBegin(GL_TRIANGLES);

	maxfdata=fielddata[0];
	minfdata=fielddata[0];

	for(int i=0;i<np_per_tri*ntri;i++)
	{
		if(fielddata[i] > maxfdata)
		{
			maxfdata = fielddata[i];
		}	
		if(fielddata[i] < minfdata)
		{
			minfdata = fielddata[i];
		}
	}

	std::cout<<"minfdata,maxfdata:"<<minfdata<<"\t"<<maxfdata<<"\n";

	for(int i=0;i<ntri;i++)
	{
		for(int j=0;j<np_per_tri;j++)
		{	
			glVertex3f(trivertices[i*np_per_tri*ndim+ndim*j],
					trivertices[i*np_per_tri*ndim+ndim*j+1],0.0);
		}

		fieldav = 0.3333*(fielddata[i*np_per_tri+0]+
				  fielddata[i*np_per_tri+1]+
				  fielddata[i*np_per_tri+2]);

		grayscaleval=(fieldav-minfdata)/(maxfdata-minfdata);
		glColor3f(grayscaleval,0.0,0.0);
	}

	glEnd();
	glFlush();
}
//=====================================================================
void oglclass::glrender()
{
	printf("hello world\n");
	std::cout<<xmin<<"\t"<<xmax<<"\t"<<ymin<<"\t"<<ymax<<"\n";
	glutInit(&glargc,glargv);
	glutInitDisplayMode ( GLUT_SINGLE | GLUT_RGB | GLUT_DEPTH);

	glutInitWindowPosition(100,100);
	glutInitWindowSize(300,300);
	glutCreateWindow ("square");


	glClearColor(0.0, 0.0, 0.0, 0.0);         // black background
	glMatrixMode(GL_PROJECTION);              // setup viewing projection
	glLoadIdentity();                           // start with identity matrix
	glOrtho(xmin, xmax, ymin, ymax, -1.0, 1.0);   // setup a 10x10x2 viewing world

	glutDisplayFunc(displaytri);
	glutMainLoop();

}
//=====================================================================
