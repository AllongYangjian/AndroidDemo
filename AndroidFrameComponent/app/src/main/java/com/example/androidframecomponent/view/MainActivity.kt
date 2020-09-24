package com.example.androidframecomponent.view

import android.os.Bundle
import com.example.androidframecomponent.R

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: MainActivity
 * Author:   YJ
 * Date:     2020/3/9 22:35
 * Description: 主界面
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/3/9 22:35        1.0              描述
 */
class MainActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
    }
}