#include<string>
#include<jni.h>

extern "C"
{
	JNIEXPORT jstring JNICALL 
		Java_com_example_learning_learningact_stringfromJNI
		(JNIEnv *env,jobject obj)
		{
			std::string str;
			str="Hello";
			env->NewStringUTF(str.c_str());
		}
}
