//
// Created by 杨建 on 2019-07-17.
//

#ifndef SERIALPORTDEMO_COMMON_H
#define SERIALPORTDEMO_COMMON_H

#define  MOD_LOG_TAG "liballong.so"
#define _ANDROID_LOG_

#ifdef _ANDROID_LOG_
#include <android/log.h>

#define  LOG(fmt,args...) __android_log_print(ANDROID_LOG_DEBUG,MOD_LOG_TAG,fmt,##args)
#define  LOGI(fmt,args...) __android_log_print(ANDROID_LOG_INFO,MOD_LOG_TAG,fmt,##args)
#define  LOGE(fmt,args...) __android_log_print(ANDROID_LOG_ERROR,MOD_LOG_TAG,fmt,##args)
#else
#include <stdio.h>
    #define LOG(fmt, args...)   printf(fmt, ##args)
    #define LOGI(fmt, args...)  printf(fmt, ##args)
    #define LOGE(fmt, args...)  printf(fmt, ##args)
#endif

#endif //SERIALPORTDEMO_COMMON_H
