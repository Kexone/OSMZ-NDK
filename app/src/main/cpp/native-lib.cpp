#include <jni.h>
#include <string>
#include <cmath>
#include "Md5.h"
#include <iostream>
#include <cstring>
#define  LOG_TAG    "LibBitmap"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

const char start = 65;
const char end = 90;
int size;
int len;
char *i;
char *buffer;
void resize();
char *generate();
void initGen(const int size);
extern "C"
JNIEXPORT void JNICALL
Java_com_example_kru13_bitmaptest_MainActivity_getMd5(JNIEnv *env, jobject instance, jstring codeToHash){
    initGen(128);
    const char *cMsg = env->GetStringUTFChars(codeToHash, false);
    char *returnMsg;
    while (strcmp((const char *) new MD5(std::string(generate())), cMsg) != 0);

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_kru13_bitmaptest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

void initGen(const int siz) {
        size = siz;
        buffer = new char[size];
        i  =  new char[size];
    for (int j = 0; j < size; j++) {
        i[j] = 0;
    }

    len = 0;
}


char *generate() {
    // Increase size of buffer
    if (len >= size) {
        resize();
    }

    // Generate key
    for (int l = 0; l <= len; l++) {
        buffer[l] = start + i[l];
    }
    buffer[len + 1] = '\0';

    // Update indexes to the start
    for (int l = len; l >= 0; l--) {
        i[l]++;
        if (i[l] + start > end) {
            i[l] = 0;

            // End
            if (l == 0) {
                len++;
                break;
            }
        } else {
            break;
        }
    }

    return buffer;
}

void resize() {
    char *newI = new char[size * 2];
    std::memcpy(i, newI, sizeof(char) * size);

    // Fill the rest with zeros
    for (int j = size; j < size * 2; j++) {
        newI[j] = 0;
    }

    // Reassign new index buffer
    delete i;
    i = newI;

    // Resize buffer, can be destroyed directly
    delete buffer;
    buffer = new char[size * 2];
    size *= 2;
}