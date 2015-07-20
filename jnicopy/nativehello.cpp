#include<string.h>
#include<jni.h>
#include<stdio.h>
#include"sampleclass.h"

extern "C"
{
	JNIEXPORT jstring JNICALL
		Java_com_example_learning_learningact_stringfromJNI
		(JNIEnv *env,jobject obj)
	{
		std::string str;
		sampleclass sobj;
		str=sobj.add(1,2);
		return env->NewStringUTF(str.c_str());
	}
}
