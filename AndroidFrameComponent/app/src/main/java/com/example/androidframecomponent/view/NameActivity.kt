package com.example.androidframecomponent.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.androidframecomponent.R
import com.example.androidframecomponent.domain.NameViewModel
import kotlinx.android.synthetic.main.name_activity.*

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: NameActivity
 * Author:   YJ
 * Date:     2020/3/9 15:25
 * Description: NameActivity
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/3/9 15:25        1.0              描述
 */
class NameActivity : BaseActivity() {

    private lateinit var model: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.name_activity);
        model = ViewModelProvider(this).get(NameViewModel::class.java)

        val nameObserver = Observer<String> { newName ->
            nameTextView.text = newName
        }

        model.currentName.observe(this, nameObserver)

        button.setOnClickListener {
            val anotherName = "Hello World"
            model.currentName.setValue(anotherName)
        }
    }


}