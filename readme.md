Fragment
===========

Fragement 代表了用户接口的一部分或一个行为在一个Activity里面，在一个Activity里，你可以结合多个fragment来建立一个多面板UI，
并且可以在多个Activity里重用一个fragment。你可以把fragment想成是一个拥有自己生命周期的Activity片段，接收自己的输入事件，并
且在Activity运行的时候你可添加或是移除它（像一个你可以在不同Activity里重用的子Activity）.

一个Fragment必须嵌入一个Activity,并且这个主Activity的生命周期直接影响到fragment的生命周期。例如，当一个Activity处于Pause
状态的时候，所有在它里面的fragment也处于pause状态。当一个activity被销毁的时候。它里面的所有的fragment都被销毁。但是，当一个
activity运行的时候（在Resume生命周期状态），你可以单独操作它里面的每一个fragment.如增加或移除它们。当你执行一个fragment事务的时候，
你也可以把它添加到一个Activity管理的回退栈中，在Activity里，每一个回退栈的入口就是一条执行的Fragment事务记录。回退栈允许用户反向操作
fragment事务，通过按返回键。

当你添加一个fragment作为你Activity 布局的一部分时，它存在与Activity view层里的viewGroup中，并且fragment定义它自己的布局。你可以添加
一个fragment布局到你的Activity布局里，通过在Activity的布局文件中声明这个fragment用一个<fragment>标签。
或从你的应用程序中编码来添加它到一个存在的viewgroup中，然而，一个fragment不是必须要成为activity布局的一部分，你可以使用一个fragment没有它自己
的UI布局，作为一个为Activity服务的不可见的工作者。

#### Design Philosophy

  Android 引入fagment 是在android3.0（API level11）,主要用来支持在大屏幕上更多动态可变的UI设计。比如说平板，因为平板的屏幕比手机的屏幕大。
  这里有更多的结合或交换UI组件。fragment允许这些设计不需要你管理view层次的复杂变化，通过将activity的布局分割成fragment,你就可以在运行时更新
  activity的UI外观，并且将这些变化加入activity管理的回退栈中。

例如；一个新的应用子啊左边可以用一个fragment来展示一个articles列表，在右边可以用另一个fragment来输出每一个article item,两个fragment 出现
在一个activity里,，每一个fragment有自己的生命周期，回调方法和处理自己的出入事件。因此，相比于用一个activity来选择一个 article item ，而另一
个Activity来显示article,用户可以选择一个article,并且在同一个 Activty里来阅读。

你可以设计每个frasgment作为一个模块和一个可重用的activity组件，因为每个fragment定义自己布局和行为，自己的生命周期和回调方法。你可以在多个activtiy
里包含同一个fragment,因此你应该设计成可重用的，避免直接操作一个fragment来自另一个fragment.这个尤其重要，因为一个模块化的fragment允许你改变你的
fragment结合不同的屏幕尺寸。当你设计的应用同时支持平板和手机时，你可以在不同的布局中重用fragments,最优的用户体验是基于可用的屏幕空间，比如，在手机上，
它可能需要分离fragment来提供一个单一的面板UI而多个却不合适在同一个activity里。

![alt text][id]

[id]:app/iamge/fragments_scene.png "Title"

#### Create Fragment

创建一个fragment，你必须创建一个fragment的子类（或是它已存在的子类），
Fragment类的编写和Activity的类很相似，它包含的回调方法类似与 Activity.列如：
onCreate(),onStart(), onPause().and onStop()，事实上，如果你要改造一个现有的Android 应用使用 fragment,你只需要简单的将Activity里回调方法的代码移动到Fragment各自的回调方法中就行了。

一般地，你至少继承以下生命周期的方法:
>.onCreate()
 当创建fragment的时候系统调用这个方法。在你的实现中，你应该初始化一些你想要在fragment paused或 stopped 时保持的基本组件。然后 resumed.
>.      OnCreateView()
 当fragment第一次画用户界面的时候系统调用这个方法。为了给你的fragment画UI界面，你必须从这个方法返回一个view,这就是你fragment 布局的根，如果你的fragment不许要提供UI,你可以返回一个null.
 >.OnPause()
系统调用这个方法表明用户将离开fragment(尽管经常不是指fragment要被销毁了），这就是你经常需要提交任何修改的地方。应该被保存在当前用户session之上。

![alt text][id]

[id]:app/iamge/fragment_lifecycle.png "Title"

所有的生命周期回调方法在章节 Handing the Fragment Lifecycle 中详细讨论。

这里有一些你想直接继承的子类，代替基类Fragment：
>.DialogFragment：
      显示一个悬浮的对话框(dialog)，用这个类来创建一个dialog是一个非传统的创建dialog帮助方法在Activity里，因为你可以合并一个fragment dialog 到activity管理的Fragment回退栈中，允许用户返回一个关掉的fragment.
 >.ListFragment：
     显示一个adapter管理的item列表，类似与ListActivity,它提供了一些方法管理listview,例如onListItemClick()回调方法。处理点击事件。

#### Adding  a user interface

  一个fragment 通常用于作为activity用户界面的一部分，并且提供自己的布局给activity.

为了提供一个布局给fragment,你必须实现onCreateView（）回调方法，当fragment绘制它的布局的时候android系统会调用它。你实现这个方法必须返回一个view ，就是你的fragment布局的根。
For example：
>.  public static class ExampleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.example_fragment, container, false);
    }

container 参数是父viewgroup,就是你的fragment将要被插入的viewgroup.saveInstanceState 参数是这个fragment里前面实例提供数据的Bundle.

inflate()方法有三个参数:
>. 1 你想要填充的资源文件的ID

>. 2 填充布局文件的父viewgroup

>.3 一布尔变量表明填充的布局是否应该被attached到ViewGroup(第二个参数)，
