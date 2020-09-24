//
// Created by 杨建 on 2019-07-17.
//
#include <jni.h>

#ifndef SERIALPORTDEMO_JNIINTERFACE_H
#define SERIALPORTDEMO_JNIINTERFACE_H
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_allong_imms_ui_serial_JNIInterface_init
        (JNIEnv *, jclass);

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    uninit
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_allong_imms_ui_serial_JNIInterface_uninit
        (JNIEnv *, jclass);

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    start
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_allong_imms_ui_serial_JNIInterface_start
        (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    stop
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_allong_imms_ui_serial_JNIInterface_stop
        (JNIEnv *, jclass);

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    FreeData
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_allong_imms_ui_serial_JNIInterface_FreeData
        (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    GetMesssage
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_allong_imms_ui_serial_JNIInterface_GetMesssage
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    sendDataToDev
 * Signature: ([BI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_allong_imms_ui_serial_JNIInterface_sendDataToDev
        (JNIEnv *, jclass, jbyteArray, jint);

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved);

JNIEXPORT jint JNICALL open_port(jstring, jint);

#ifdef __cplusplus
}
#endif
#endif //SERIALPORTDEMO_JNIINTERFACE_H
