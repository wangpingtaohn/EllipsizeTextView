## EllipsizeTextView 在textView末尾添加类似 ...展开文案 或者图片的组件，效果如下：

![image](https://github.com/wangpingtaohn/EllipsizeTextView/blob/master/img-storage/demo.png)

## 使用步骤
1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}  
  ```
 2.Add the dependency
 ```
 dependencies {
	        implementation 'com.github.wangpingtaohn:EllipsizeTextView:1.0.0'
	}
  ```
  ## 实例
方式一：
布局引用：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.wpt.ellipsize.EllipsizeTextView
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:textColor="#111"
        android:textSize="24sp"
        app:minLines="2"
        android:layout_marginTop="20dp"
        app:endIcon="@drawable/ic_down_32"
        app:endColor="@color/colorAccent"
        app:originText="哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
        android:id="@+id/tv1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <com.wpt.ellipsize.EllipsizeTextView
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:textColor="#111"
        android:textSize="24sp"
        app:minLines="2"
        android:layout_marginTop="20dp"
        app:endColor="@color/red"
        app:endText="展开"
        app:endIsBold="true"
        app:originText="哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
        android:id="@+id/tv2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <com.wpt.ellipsize.EllipsizeTextView
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:textColor="#111"
        android:textSize="24sp"
        android:layout_marginTop="20dp"
        android:id="@+id/tv3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <com.wpt.ellipsize.EllipsizeTextView
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:textColor="#111"
        android:textSize="24sp"
        android:layout_marginTop="20dp"
        android:id="@+id/tv4"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
        android:layout_height="wrap_content"/>

</LinearLayout>
```
方式二：
代码：
```
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

```
