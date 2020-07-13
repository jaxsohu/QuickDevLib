# QuickDevLib

[![](https://jitpack.io/v/giswangsj/wsjLib.svg)](https://jitpack.io/#giswangsj/wsjLib)

Android快速开发基础框架，包含了`RecyclerView`适配器，缓存，文件下载器，网络请求框架，动态权限申请，下拉刷新上拉加载，自定义View，公共基础类（Bitmap,File,Log,Service,Sp,Time,Display,Device，Spinner）

Implementation
----


```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
	implementation 'com.github.giswangsj:wsjLib:3.1.3'
}
```

##### Spinner

Spinner采用popupwindow实现Android原生的Spinner效果，使用非常简单。简单的使用代码如下所示：

```
PopupwindowUtils.showMenu(requireContext(), it, filters, 
	object : OnPopupWindowListener<String> {
			override fun getText(t: String?): String {
				return "item:"+t
			}

			override fun onClick(t: String?, pos: Int) {
				// TODO click event
			}
		})
```



## License

`QuickDevLib` is released under the Apache License version 2.0.