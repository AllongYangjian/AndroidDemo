package com.example.androidframecomponent.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: NameViewModel
 * Author:   YJ
 * Date:     2020/3/9 15:23
 * Description: 名称ViewModel对象
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/3/9 15:23        1.0              描述
 */
class NameViewModel:ViewModel() {
    val currentName:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
        
    }
}