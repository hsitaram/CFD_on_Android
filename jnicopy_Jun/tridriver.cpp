#include"polygon.h"
#include"triangulation.h"
#include"laplace_solver.h"
#include<cstdlib>
#include<jni.h>

extern "C"
{
	JNIEXPORT jfloatArray JNICALL 
		Java_com_example_learning_Square_solver
		(JNIEnv *env,jobject obj,jfloatArray ppoints,jint npoints)
		{
			int n,npoly;
			double *p;
			polygon *pgons;
			char num[20];
			double minlength;
			double globalavglength;
			std::vector<int> vec;
			int triplen;
			//jint arraysize;
			float *tripoints;
			jfloat* valuesjf = NULL;

			int f,l;

			//oglclass::glargc=argc;
			//oglclass::glargv=argv;
			triangulation *triobj;
			laplace_solver *solverobj;

			npoly = 1;
			globalavglength = 0.0;
			pgons = new polygon[npoly];
			
			n = npoints;
			p = new double[DIM*n];
			valuesjf = env->GetFloatArrayElements(ppoints,NULL);	

			for(int i=0;i<DIM*n;i++)
			{
				p[i]=valuesjf[i];
			}
			/*p[0] =  0.00;     p[1]   = 0.0;
			p[2] =  0.25;     p[3]   = 0.0;
			p[4] =	0.50;     p[5]   = 0.0;
			p[6] =	0.75;     p[7]   = 0.0;
			p[8] =	1.00;     p[9]   = 0.0;
			p[10] =	1.433;    p[11]  = 0.25;
			p[12]=	1.866;    p[13]  = 0.50;
			p[14] =	2.299;    p[15]  = 0.75;
			p[16] =	2.732;    p[17]  = 1.00;
			p[18] =	2.732;    p[19]  = 1.50;
			p[20] =	2.732;    p[21]  = 2.00;
			p[22] =	2.732;    p[23]  = 2.50;
			p[24] =	2.732;    p[25]  = 3.00;
			p[26] =	1.366;    p[27]  = 3.00;
			p[28] =	0.000;    p[29]  = 3.00;*/

			for(int i=0;i<npoly;i++)
			{
				pgons[i].assignpolypoints(p,n);
				globalavglength += pgons[i].avglength;
			}
			globalavglength = globalavglength/double(npoly);

			triobj=new triangulation;
			triobj->setpolydomain(pgons,npoly);
			triobj->generate_external_triangulation();
			triobj->centroidinsert(globalavglength*0.5);
			solverobj=new laplace_solver;
			solverobj->assignelements(triobj,2.0,0.0);
			solverobj->solve();
			triobj->updatetriedgepoints();

			jint arraysize;
			jfloat testpoints[triedgenum];
			for(int i=0;i<triedgenum;i++)
			{
				testpoints[i]=triedgepoints[i];	
			}
			arraysize=triedgenum;
			jfloatArray jArray = env->NewFloatArray(arraysize);
			env->SetFloatArrayRegion(jArray, 0, arraysize, testpoints);
			return(jArray);
			
			/*triplen=12;
			tripoints = new float[triplen];

			tripoints[0]=0.0; tripoints[1]=0.0; tripoints[2]=0.0;
			tripoints[3]=1.0; tripoints[4]=0.0; tripoints[5]=0.0;

			tripoints[6]=1.0; tripoints[7]=0.0; tripoints[8]=0.0;
			tripoints[9]=1.0; tripoints[10]=1.0; tripoints[11]=0.0;

			jfloat testpoints[triplen];
			for(int i=0;i<triplen;i++)
			{
				testpoints[i]=tripoints[i]*0.5;
			}*/
			/*for(int i=0;i<n;i++)
			  {
			  f=i;
			  testpoints[6*i+0]=p[2*f+0]*0.1;
			  testpoints[6*i+1]=p[2*f+1]*0.1;
			  testpoints[6*i+2]=0.0;

			  l=(i+1)%n;
			  testpoints[6*i+3]=p[2*l+0]*0.1;
			  testpoints[6*i+4]=p[2*l+1]*0.1;
			  testpoints[6*i+5]=0.0;
			  }*/
			//arraysize = triplen;
			//jfloatArray jArray = env->NewFloatArray(triplen);

			//env->SetFloatArrayRegion(jArray, 0, arraysize, testpoints);
			//env->SetFloatArrayRegion(jArray, 0, triplen, testpoints);
			//return jArray;
		}
}
