#include"polygon.h"
#include"triangulation.h"
#include"laplace_solver.h"
#include<cstdlib>
#include<jni.h>

extern "C"
{
	JNIEXPORT void JNICALL 
		Java_com_example_learning_Meshandsoln_solver
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
			float *tripoints;
			jfloat* valuesjf = NULL;

			int f,l;
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

			for(int i=0;i<npoly;i++)
			{
				pgons[i].assignpolypoints(p,n);
				globalavglength += pgons[i].avglength;
			}
			globalavglength = globalavglength/double(npoly);

			std::cout<<"Hariswaran calling solver..\n";

			triobj=new triangulation;
			triobj->setpolydomain(pgons,npoly);
			triobj->generate_external_triangulation();
			triobj->centroidinsert(globalavglength*0.5);
			triobj->allocate_globalvars();
			solverobj=new laplace_solver;
			solverobj->assignelements(triobj,2.0,0.0);
			solverobj->solve();
			triobj->updatetriedgepoints();

		}
//===========================================================================================================
	JNIEXPORT jfloatArray JNICALL 
		Java_com_example_learning_Meshandsoln_returnmesh
		(JNIEnv *env,jobject obj)
	{
			jint arraysize;
			jfloat testpoints[globaltriedgenum];
			for(int i=0;i<globaltriedgenum;i++)
			{
				testpoints[i]=globaltriedgepoints[i];	
			}
			arraysize=globaltriedgenum;
			jfloatArray jArray = env->NewFloatArray(arraysize);
			env->SetFloatArrayRegion(jArray, 0, arraysize, testpoints);
			
			return(jArray);	
	}
//===========================================================================================================
	JNIEXPORT jfloatArray JNICALL 
		Java_com_example_learning_Meshandsoln_returnsolnmesh(JNIEnv *env,jobject obj)
	{
			jint arraysize;
			jfloat testpoints[globaltrisize];
			for(int i=0;i<globaltrisize;i++)
			{
				testpoints[i]=globaltripoints[i];	
			}
			arraysize=globaltrisize;
			jfloatArray jArray = env->NewFloatArray(arraysize);
			env->SetFloatArrayRegion(jArray, 0, arraysize, testpoints);
			
			return(jArray);	
	}
//===========================================================================================================
	JNIEXPORT jfloatArray JNICALL 
		Java_com_example_learning_Meshandsoln_returnsolncolor(JNIEnv *env,jobject obj)
	{
			jint arraysize;
			jfloat testpoints[globalsolncolorsize];
			for(int i=0;i<globalsolncolorsize;i++)
			{
				testpoints[i]=globalsolncolor[i];	
			}
			arraysize=globalsolncolorsize;
			jfloatArray jArray = env->NewFloatArray(arraysize);
			env->SetFloatArrayRegion(jArray, 0, arraysize, testpoints);
			
			return(jArray);	
	}
//===========================================================================================================
}
