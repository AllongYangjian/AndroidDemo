//
// Created by 杨建 on 2019-07-17.
//

#include <cstdio>
#include <cstdlib>
#include <cassert>
#include <iosfwd>
#include <sys/types.h>
#include <fcntl.h>
#include <termios.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/stat.h>
#include <unistd.h>

#include "include/JNIInterface.h"
#include "include/common.h"

using namespace std;

static const char *classPathName = "com/allong/imms/view/serial/JNIInterface";

jclass mClazz = nullptr;
JavaVM *mJvm = nullptr;
JNIEnv *mEnv = nullptr;
int mFd;
bool mRun = false;

jboolean open_port(const char *path, jint brate) {
    mFd = open(path, O_RDWR | O_NOCTTY | O_NDELAY | O_CLOEXEC);
    LOGE(">>>>>>> Open Serial Port%d, fd:%d", brate, mFd);
    if (-1 == mFd) {
        LOGI("Can't open Serial port%d", brate);
        return JNI_FALSE;
    }
    if (fcntl(mFd, F_SETFL, 0) < 0) {
        LOGE("fcntl failed");
    }
    if (isatty(STDIN_FILENO) == 0) {
        LOGE("standard input is not a terminal device");
    }
    return JNI_TRUE;
}

bool SetParam(jint brate, int bit, char event, int stop) {
    struct termios newtio, oldtio;
    if (tcgetattr(mFd, &oldtio) != 0) {
        LOGE("SetupSerial failed");
        return false;
    }
    bzero(&newtio, sizeof(newtio));
    newtio.c_cflag |= CLOCAL | CREAD;
    newtio.c_cflag &= ~CSIZE;

    switch (bit) {
        case 7:
            newtio.c_cflag |= CS7;
            break;
        case 8:
            newtio.c_cflag |= CS8;
            break;
    }

    switch (event) {
        case 'O':                     //奇校验
            newtio.c_cflag |= PARENB;
            newtio.c_cflag |= PARODD;
            newtio.c_iflag |= (INPCK | ISTRIP);
            break;
        case 'E':                     //偶校验
            newtio.c_iflag |= (INPCK | ISTRIP);
            newtio.c_cflag |= PARENB;
            newtio.c_cflag &= ~PARODD;
            break;
        case 'N':                    //无校验
            newtio.c_cflag &= ~PARENB;
            break;
    }

    switch (brate) {
        case 2400:
            cfsetispeed(&newtio, B2400);
            cfsetospeed(&newtio, B2400);
            break;
        case 4800:
            cfsetispeed(&newtio, B4800);
            cfsetospeed(&newtio, B4800);
            break;
        case 9600:
            cfsetispeed(&newtio, B9600);
            cfsetospeed(&newtio, B9600);
            break;
        case 19200:
            cfsetispeed(&newtio, B19200);
            cfsetospeed(&newtio, B19200);
            break;
        case 38400:
            cfsetispeed(&newtio, B38400);
            cfsetospeed(&newtio, B38400);
            break;
        case 115200:
            cfsetispeed(&newtio, B115200);
            cfsetospeed(&newtio, B115200);
            break;
        default:
            cfsetispeed(&newtio, B9600);
            cfsetospeed(&newtio, B9600);
            break;
    }
    if (stop == 1) {
        newtio.c_cflag &= ~CSTOPB;
    } else if (stop == 2) {
        newtio.c_cflag |= CSTOPB;
    }
    newtio.c_cc[VTIME] = 0;
    newtio.c_cc[VMIN] = 0;
    tcflush(mFd, TCIFLUSH);
    if ((tcsetattr(mFd, TCSANOW, &newtio)) != 0) {
        LOGE("com set error");
        return false;
    }
    return true;
}

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    init
 * Signature: ()I
 */
jint Java_com_allong_imms_ui_serial_JNIInterface_init(JNIEnv *env, jclass) {
    if (nullptr == mClazz) {
        mClazz = env->FindClass(classPathName);
        if (nullptr == mClazz) {
            LOGE("init, mClazz '%s' is null \n", classPathName);
            return -1;
        }
        env->GetJavaVM(&mJvm);
        if (nullptr == mJvm) {
            mClazz = nullptr;
            LOGE("init,jvm is null\n");
            return -1;
        }
    }
    return 1;
}

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    uninit
 * Signature: ()I
 */
jint Java_com_allong_imms_ui_serial_JNIInterface_uninit(JNIEnv *, jclass) {
    LOGI("serial port uninit");
    mClazz = nullptr;
    return 1;
}


void startRecver();

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    start
 * Signature: (Ljava/lang/String;I)I
 */
jint
Java_com_allong_imms_ui_serial_JNIInterface_start(JNIEnv *env, jclass, jstring path, jint brate) {
    const char *strPath = env->GetStringUTFChars(path, false);
    int result = -1;
    if (!open_port(strPath, brate)) {
        close(mFd);
    } else {
        if (!SetParam(brate, 8, 'N', 1)) {
            close(mFd);
        } else {
            result = 1;
            mRun = true;
        }
    }
    env->ReleaseStringUTFChars(path, strPath);
    startRecver();
    return result;
}

void startRecver() {
    char szBuf[64] = {0};

}


/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    stop
 * Signature: ()I
 */
jint Java_com_allong_imms_ui_serial_JNIInterface_stop(JNIEnv *, jclass) {
    return 0;
}

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    FreeData
 * Signature: ([B)I
 */
jint Java_com_allong_imms_ui_serial_JNIInterface_FreeData(JNIEnv *, jclass, jbyteArray) {
    return 0;
}

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    GetMesssage
 * Signature: (I)[B
 */
jbyteArray Java_com_allong_imms_ui_serial_JNIInterface_GetMesssage(JNIEnv *, jclass, jint) {
    return nullptr;
}

/*
 * Class:     com_allong_imms_ui_serial_JNIInterface
 * Method:    sendDataToDev
 * Signature: ([BI)Z
 */
jboolean Java_com_allong_imms_ui_serial_JNIInterface_sendDataToDev(JNIEnv *, jclass, jbyteArray,
                                                                   jint) {
    return JNI_FALSE;
}


static JNINativeMethod gMethods[] = {
        {"init",          "()I",                    (void *) Java_com_allong_imms_ui_serial_JNIInterface_init},
        {"uninit",        "()I",                    (void *) Java_com_allong_imms_ui_serial_JNIInterface_uninit},
        {"start",         "(Ljava/lang/String;I)I", (void *) Java_com_allong_imms_ui_serial_JNIInterface_start},
        {"stop",          "()I",                    (void *) Java_com_allong_imms_ui_serial_JNIInterface_stop},
        {"FreeData",      "([B)I",                  (void *) Java_com_allong_imms_ui_serial_JNIInterface_FreeData},
        {"GetMesssage",   "(I)[B",                  (void *) Java_com_allong_imms_ui_serial_JNIInterface_GetMesssage},
        {"sendDataToDev", "([BI)Z",                 (void *) Java_com_allong_imms_ui_serial_JNIInterface_sendDataToDev}
};

/**
 * 注册本地函数
 * @param env
 * @return
 */
jint register_com_allong_imm_ui_JNIInterface(JNIEnv *env) {
    jclass clazz = env->FindClass(classPathName);
    if (clazz == nullptr) {
        LOGE("can't find class '%s'", classPathName);
        return JNI_FALSE;
    }
    int num = sizeof(gMethods) / sizeof(gMethods[0]);
    if (env->RegisterNatives(clazz, gMethods, num) < 0) {
        LOGE("register native method failed of '%s'", classPathName);
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

/**
 * 加载类库时默认调用该方法
 * @param vm
 * @param reserved
 * @return
 */
jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    LOGI("JNI_OnLoad");

    JNIEnv *env = nullptr;
    jint result = -1;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed");
        goto bail;
    }

    assert(env != nullptr);

    if (register_com_allong_imm_ui_JNIInterface(env) < 0) {
        LOGE("ERROR:register native method");
        goto bail;
    }

    return JNI_VERSION_1_4;

    bail:
    return result;
}
