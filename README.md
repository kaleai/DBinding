# DBinding   

![](./pic/logo.jpg)   
##简介  
**DBinding —— MVVM时代孤独的拓荒者**   

DBinding是一个基于DataBinding的巧妙、灵活的mvvm框架。解决了两个页面的数据自动同步，跨线程更新UI等疑难杂症。意在未开发者提供一个结构清晰、方便分工合作、bug率低、代码量少的**轻量级**框架。  

##一、优点    
- [x] 完全能代替findViewById，自动建立layout文件的java抽象类  
- [x] 提供了完全代替R.id的方案，解决多个view共用同一id而出现的定位难的问题  
- [x] 不用担心线程切换的问题，更新UI的操作会自动移动到主线程中执行   
- [x] 两个页面的数据可以共通，减少了乏味的多页面回调    
- [x] 提供了能代替部分`onActivityResult`、广播代码的方案      
- [x] 分层清晰，UI层和逻辑层耦合度降低，能快速定位问题，真正能**减少bug量**   
- [x] 数据自动绑定，直接对ViewData进行操作即可更新UI    
- [x] 可通过插件自动生成ViewModel，0.9秒内可建立可供一个项目用的所有ViewModel   
- [x] 支持对一个或多个数据的监听，可极细力度地完成ui逻辑   

另：除了以上优点外，就没啥牛逼的地方了。。。

##二、示例  
![](./pic/01.jpg)
![](./pic/02.jpg)

##三、添加依赖（暂未完成）

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

##四、已知问题
①因为增加了一个viewModel，所以可能会有一点点重。但是相比起databinding推荐的xml中写java逻辑的写法来看，此框架的复杂度要低很多。  

②目前对于android中默认属性的支持还不是很完全，但大家可以共同完善它。  
完善的方式：  
1. 通过自定义属性对的方式对插件进行扩展  
2. 对pluginLib这个module中的kale.dbinding.parser.TypeFinder中添加case代码，并提交pr   
3. 对dbindingTest这个module中TypeTest进行测试，看是否有未支持的属性  

##五、使用方式
1.编写Layout文件：   

```xml   

<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <!-- 定义viewData: private org.kale.vd.UserViewModel user -->
        <variable  type="org.kale.vd.UserViewModel" name="user"/>
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

    private UserViewModel mUserVm = new UserViewModel();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBinding.bindViewModel(this, R.layout.activity_main, mUserVm); // 将vm和layout进行绑定
		mUserVm.setName("漩涡鸣人");  // textview中就会自动渲染出文字了
    }

```    
##六、详尽文档  

###[DBinding权威使用指南(重要)](https://www.zybuluo.com/shark0017/note/256112)    


##开发者

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
