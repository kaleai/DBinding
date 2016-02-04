# DBinding   
  
![](./pic/logo.jpg)   
##简介  
**DBinding —— mvvm时代的拓荒者**   

DBinding是一个基于dataBinding的巧妙、灵活的mvvm安卓框架。解决了两个页面的数据自动同步，跨线程更新UI等问题。意在未开发者提供一个结构清晰、方便分工合作、bug少、代码量少的轻量级框架。  

   
 
#一、优点    
分层清晰，ui层和逻辑层耦合度降低，能快速定位问题   
数据自动绑定，直接对ViewData进行操作即可更新UI   
不用担心线程切换的问题，更新UI的操作会自动跑到主线程中处理       
两个页面的数据可以共通，无需建立回调便可更新界面   
可通过插件自动生成viewData，半秒便可建立绑定框架   
支持自定义绑定规则和绑定对象，不用担心对自定义属性支持不足的问题   
支持对一个或多个数组的监听，可针对性完成ui层面的逻辑   

另：可能会支持EditText的双向绑定（正在考虑是否支持）

#二、示例  
![](./pic/01.jpg)
![](./pic/02.jpg)

#三、添加依赖（暂未完成）

1.在项目外层的build.gradle中添加JitPack仓库   

```
repositories {
	maven {
		url "https://jitpack.io"
	}
}
```
2.在用到的项目中添加依赖  

```
dependencies {
	compile 'com.github.tianzhijiexian:DBinding:未做好'
}    
```  

#四、已知问题
①因为增加了一个viewData，所以可能会有一点点重。但是相比起databinding的原本写法来看，此框架的复杂度要低很多。  

②目前对于android中默认属性的支持还不是很完全，大家可以共同完善它。  
完善的方式：  
1. 通过自定义属性对的方式对插件进行扩展  
2. 对pluginLib这个module中的kale.dbinding.parser.TypeFinder中添加case代码，并提交pr   
3. 对dbindingTest这个module中TypeTest进行测试，看是否有未支持的属性  

#五、使用方式
1.编写Layout文件：   

```xml   
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <!-- 定义viewData: private org.kale.vd.UserViewData user -->
        <variable  type="org.kale.vd.UserViewData" name="user"/>
    </data>


    <!-- 绑定数据: textView.setText(user.name) -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="user.name"/>
</layout>  
``` 
2.编写java代码：   

**Activity:**  

```JAVA  
    private UserViewData mUserViewData = new UserViewData();
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding b = DBinding.bind(this, R.layout.activity_main); // 设置布局
        DBinding.setVariables(b, mUserViewData); //设置viewData
        
        // 定义一个vm，传入viewData
        final MainViewModel viewModel = new MainViewModel(mUserViewData); 
    }
```  
**ViewModel:**   

```JAVA
public class MainViewModel {

    public MainViewModel(UserViewData userViewData,OtherViewData otherViewData) {
        userViewData.setName("漩涡鸣人");  // textview中就会自动渲染出文字了
    }

}
```    
#六、详尽文档  

###[DBinding权威使用指南](https://www.zybuluo.com/shark0017/note/256112)    

![](./pic/doc.png)   


## 开发者

![](https://avatars3.githubusercontent.com/u/9552155?v=3&s=460)

Jack Tony: <developer_kale@foxmail.com>  


## License

```  
Copyright 2015 Jack Tony

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

