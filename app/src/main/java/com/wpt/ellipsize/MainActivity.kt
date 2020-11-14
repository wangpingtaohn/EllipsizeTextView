package com.wpt.ellipsize

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val text3 = "哈哈哈哈啊哈哈啊哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈啊哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈啊哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈啊哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈啊哈哈哈哈哈啊结束"

        tv3.setMinLine(2) //设置超过几行展示
        tv3.setEndText("全部") //末尾要显示是文字
        tv3.setEndColor(getColor(R.color.blue))//末尾要显示是文字的颜色
        tv3.setText(text3)

        tv4.setMinLine(2)
        tv4.setEndIcon(getDrawable(R.drawable.ic_down_32))//末尾要显示是的图标
        tv4.setText(text3)
    }

}
