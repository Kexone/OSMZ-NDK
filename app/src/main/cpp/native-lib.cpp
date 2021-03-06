#include <jni.h>
#include <cmath>
#include <android/bitmap.h>
#include <android/log.h>
#include <string>
#include "Generator.h"
#include "MD5.h"

#define  LOG_TAG    "LibBitmap"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

extern "C"
JNIEXPORT  jchar JNICALL
Java_com_example_kru13_bitmaptest_MainActivity_getMd5(JNIEnv *env, jobject instance, jstring codeToHash){
    Generator generator(128);
    const char *cMsg = env->GetStringUTFChars(codeToHash, false);
    char *returnMsg;
    while (strcmp(md5(std::string( returnMsg =  generator.generate())), cMsg) != 0);
    return *returnMsg;

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_kru13_bitmaptest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}