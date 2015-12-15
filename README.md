# LoopViewPager
一个可循环播放的ViewPager

##使用方法

###绑定数据

LoopViewPager继承自ViewPager,myPageAdapter继承自PagerAdapter，使用方法与父类相同

```java
viewPager = (LoopViewPager) findViewById(R.id.viewpager);
myPageAdapter = new MyPageAdapter(mlist,this);
viewPager.setAdapter(myPageAdapter);
```

###自动播放

绑定数据后只完成了循环播放的功能，自动播放需要调用

```java
viewpager.start(int times);
```
参数times表示自动播放间隔时间，单位为ms,不加参数默认为5000ms

###停止播放
调用
```java

viewpager.stop();
```

### 回收

自动播放使用Handler和Message机制，为了防止内存泄露，需要在onDestory()方法中调用
```java
viewpager.onDestor();
···
