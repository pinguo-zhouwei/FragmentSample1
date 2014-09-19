Fragment(翻译文档)
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

![alt text][id1]

[id1]:app/iamge/fragment_lifecycle.png "Title"

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


文档翻译日期:2014.9.18
=====

####Or, programmatically add the fragment to an existing ViewGroup.(或者程序代码中添加fragment到已存在的ViewGroup)

在你activity运行的任何时间，你都可以添加fragments到你的activity布局。你需要简单的指定一个ViewGroup来放你的fragment.

要在activity中使用fragment事务（如:add(添加)，remove（移除），replace（替换）一个fragment）,你必须使用来自FragmentTransaction的API,你可以在Activity中得到一个FragmentTransaction实例，像这样:

>.FragmentManager fragmentManager = getFragmentManager()

>. FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

然后你就可以用add（）方法来添加一个fragment了，指定fragment的添加，并且插入到这个view.例如:

>.ExampleFragment  fragment = new ExampleFragment();
 >.fragmentTransaction.add(R.id.fragment_container, fragment);
 >.fragmentTransaction.commit();

add（）方法的第一个参数是这个fragment会放在哪的ViewGroup,通过resource Id指定。第二个参数就是要添加的fragment。
 一旦你使FragmentTransaction发生了变化，你必须调用commit（）方法来使这些变化产生影响。

####Adding a fragment without a UI

上面的例子展示了怎样添加一个fragment到你的Activity并提供一个UI，然而，你也可以用fragment为你的Activity提供一个后台行为而没有前端UI

要添加一个没有UI的fragment,从Activity添加fragment用add(Fragment,String)(为fragment提供一个唯一的字符串”tag”,而不是一个view ID). 但是，通过这种方法添加一个fragment，由于它没有结合一个view在Activity的布局里，所以它不会收到onCreateView（）回调。因此你不需要实现那个方法。

提供一个字符串给fragment，并不是严格地为了没有UI的fragment,你也可以为一个有UI的fragment提供字符串 tag, 但是，如果你的fragment没有UI，字符串标签就是是唯一识别它的方法了。如果你想后面 在Activity中得到这个Fragment,你需要以用 FindFragmentByTag().

####Managing Fragments（管理Fragments）

为了在你的Activity里管理Fragments,你需要使用FragmentManager,要得到FragmentManager,在你的Activity里调用getFragemntManager().

你可以用FragmentManager做下面一些事情:
>. 1 得到在Activity里存在的fragments,用findFragmentById()(用于 为Activity提供UI布局的fragment),findFragmentByTag（）（用于没有提供UI的fragment）.

>. 2 将fragment pop到回退栈，用popBackStack()

>. 3 为回退栈的变化注册一个监听器，用 addOnBackStackChangeListener().

 正如前面章节所述，你可以用FragmentManager 打开一个FragmentTransaction,它允许你操作事务，如:添加和移除fragment.


####Performing Fragment Transactions

在Activity中使用fragment一个明显的特征就是可以添加、移除和替换，并且可以在他们上面执行一些其他的动作。响应用户的交互，每个你提交到activity的一系列变化叫做一个transaction（事务）。并且你可以执行它用API FragmentTransaction .你也可以把每一个事务保存到一个Activity管理的回退栈中(back stack),允许用户通过fragmens 的变换导航回去.

你可以从FragmentManager得到一个FragmentTransaction实例像下面这样:

>.FragmentManager fragmentManager = getFragmentManager();
>.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

每一个事务（transaction）是你要同时执行的一个变化的集合。你可以为一个给定的事务设置你想要执行的所有变化。通过使用方法如:add（），remove(),replace(),然后，为了将事务应用到你的Activity上，你必须调用commit（）方法提交。

然而，在你调用commit（）方法之前，你可能想调用 addToBackStack()方法，来把一个事务添加到一个fragment 事务回退栈中，这个回退栈被Activity管理并允许用户回到以前fragment的状态。通过按返回键。

例如,这儿展示了你如何用一个fragment替换另一个fragment,并且保持前面的状态到回退栈(back stack )：

 >. // Create new fragment and transaction
Fragment newFragment = new ExampleFragment();
FragmentTransaction transaction = getFragmentManager().beginTransaction();

>.// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
transaction.replace(R.id.fragment_container, newFragment);
transaction.addToBackStack(null);

>.// Commit the transaction
transaction.commit();

在这个示例中，newFragment 替换当前布局中用ID R.id.fragment_container定义的fragment(或是其他view),通过调用addTobackStack()，这个替换的事务已经保存到了回退栈中，因此用户可以反向操作这个事务，并且回到前面fragment的状态通过按返回键。

如果你添加了多个变化到一个事务中（如:add() 或 remove())并且调用了addToBackStack()，在你调用commit（）之前，所有被应用的变化作为一个transaction加到了回退栈中。并且按返回键时，它们一起被reverse.

将变化添加到FragmentTransaction的顺序不用关心，除了:

 * 最后你必须调用commit（）
 *  如果你添加多个fragments到同一个容器，你添加它们的顺序决定了他们在view层次结构中出现的顺序。

如果当你执行一个事务来删除一个fragment而没有调用addToBackStack()，当事务提交的时候，这个fragment就会被销毁，并且用户不能导航回原来的状态。相反地，当你移除一个fragment时调用addToBackStack（）,这个fragment会被停止(stopped),当用户返回时会重新与用户交互(Resumed).

> 建议:对于每一个fragment事务，你可一应用一个事务动画通过调用setTransition()，在你提交之前。

 调用commit（）后不会立即执行事务，它按进度运行在Activity的UI线程里，直到这个线程有能力区执行它。但是，如果有需要，你可以从UI线程调用executePendingTransactions()来立即执行事务的提交。这样做通常是不必要的，除非你的事务是其他线程工作所依赖的。

> Caution（警告）: 你可以用一个commit（）来提交一个事务仅优先于Activity保存它的状态(当用户离开Activity的时候)。如果你尝试在那个点之后提交，将会抛出一个异常，这是因为这个状态在提交之后可能会丢失，如果Activity需要重新保存，在这种情况下——丢失提交的情况下正常执行，用commitAllowingStareLoss().


####Communicating with the Activity(与Activity交互)

尽管一个fragment独立与Activity作为一个Object被实现，并且可以用于多个Activity里，但是，一个给定的Fragment实例直接tied（系）于Activity之上。也就是包含它。

特别地，fragment可以访问Activity实例用getActivity(),并且很容易执行任务，如:在Activity布局中找一个view.

> View listView = getActivity().findViewById(R.id.list);

同样的，你的Activity可以调用fragment里的方法，通过从FragmentManager返回一个引用，用findFragmentById()或findFragmentByTag(),例如:

> ExampleFragment fragment = (ExampleFragment) getFragmentManager().findFragmentById(R.id.example_fragment);

#####Creating event callbacks to the activity(为Activity创建回调事件)

在一些情况下，你可能需要fragment分享一些回调事件给Activity,一个好的实现方式就是在fragment里定义一个回调接口，并且需要宿主Activity实现它。当activity从接口收到一个回调的时候，它可以分享信息给其他的fragment.

例如；如果一个应用有两个fragment在一个Activity中，一个显示articles列表(Fragment A)，另一个输出一个article(Fragment B),然后，fragment A 必须告诉Activity 什么时候一个list的item被选中，以至于它可以告诉Fragment B 来显示article,在这种情况下，OnArticleSelectedListener 接口在fragment A 中被定义:

> public static class FragmentA extends ListFragment {
    ...
    // Container Activity must implement this interface
    public interface OnArticleSelectedListener {
        public void onArticleSelected(Uri articleUri);
    }
    ...
}

然后，Fragment宿主的Activity 需要实现这个接口，并且重写fragment A 的onArticleSelected()方法来通知fragment B ，为了保证宿主Activity实现这个接口，Fragment A的onAttach()回调方法实例化一个OnArticleSelectedListener实例通过强制转换 Activity.

> public static class FragmentA extends ListFragment {
    OnArticleSelectedListener mListener;
    ...
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    ...
}

如果这个Activity没有实现这个接口，这时这个fragment 抛出一个类型转换异常(ClassCastException),如果实现了接口，the mListener 变量持有一个 OnArticleSelectedListener的引用。因此，fragment A 可以分享事件给Activity通过调用定义在OnArticleSelectedListener接口里面的方法。例如,如果fragment A 是一个可扩张的ListFragment，每次用户点击一个item项，系统调用fragment 中 onListItemClick（） 方法，然后这个方法调用OnArticleSelected（）方法分享事件给Activity.

> public static class FragmentA extends ListFragment {
    OnArticleSelectedListener mListener;
    ...
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Append the clicked item's row ID with the content provider Uri
        Uri noteUri = ContentUris.withAppendedId(ArticleColumns.CONTENT_URI, id);
        // Send the event and Uri to the host activity
        mListener.onArticleSelected(noteUri);
    }
    ...
}


#### Adding items to the Action Bar

 你的fragments 可以给Activity的Options Menu 提供菜单项，通过实现onCreateOptionsMenu(),为了这个方法可以收到回调，在onCreate(）的时候必须调用setHasOptionsMenu(),来表明这个Fragment会添加items 到 Options Menu（否则，Fragment不会收到调用到OnCreateOptionsMenu()）.

任何你从fragment添加到Options Menu的item将会被追加到已经存在的菜单项后面，当一个菜单项被点击后，fragment会收到回调到OnOptionsItemSelected().

你也可以在fragment布局里注册一个view来提供一个上下文菜单通过调用registerForContextMenu(),当用户打开上下文菜单，fragment将会收到一个调用onCreateContextMenu().当用户选择一个item，fragment收到一个调用 onContextItemSelected(),

#### 2014.9.19

#### Handing the Fragment Lifecycle(处理fragment的生命周期)

管理fragment的生命周期类似于管理activity的生命周期，和Activity一样，fragment存在的三个状态:

>* Resumed
>       在运行的activity中，fragment处于可见状态
> + Paused
>     另一个Activity出现在屏幕上，并且获得了焦点。但是fragment宿主的activity仍然是可见的(屏幕上的activitty是透明的或是没有覆盖完全屏)。
> + Stopped
>  Fragment不可见，宿主的Activity也Stopped了,或者Fragment已经从Actvity里移除，但是加入到了回退找中。一个处于stopped状态的Fragment仍然活着（所有的状态和变量被系统保存着）。但是，对于用户来说它是不可见的，并且当Activity被killed掉的时候，它也会被killed掉。

像Activity一样，你可以用Bundle来保存一个fragment的状态，在Activity的进程在被Killed掉的情况下，当Activity recreate（重新创建）的时候，你需要重新保存fragment的状态。你可以在Fragment onSaveInstanceState() 方法回调的时候保存状态，也可以在 onCreate() ,onCreateView（），onActivityCreated（）的时候重新存储状态。了解更多的关于 存储状态的信息，请看Activities的文档。

一个 Activity和一个Fragment之间最重要的区别就是Fragment保存在各自的回退栈中。当一个activity stopped的时候，它被放到了一个被系统管理的Activity回退栈中，但是，默认情况下，一个Fragment被放到一个被宿主Activity管理的回退栈中，仅仅当你明确的请求调用addToBackStack()来保存，当一个事务移除这个fragment的时候。

另外,管理fragment的方法与管理Activity的方法非常相似，因此，管理Activity生命周期的相同特征同样也可以用于Fragments.你也需要理解的是。Activity的生命是怎么影响 Fragments 的生命的。

> 警告：在你的Fragment中如果你需要一个Context对象，你可以调用getActivity(),但是，谨慎调用getActivity（）仅当你的Fragment已经Attached到Activity,当你的fragment没有Attached到Activity或者在它生命周期结束的时候detached，getActivity（）将会返回null.

##### Coordinating with the activity lifecycle(与Activity的生命周期相配合)

Fragment宿主的Activity的生命周期直接影响到fragment的生命周期，每一个Activity生命周期方法的回调导致Fragment中相似生命周期的方法回调。例如，当Activity收到onPause().Activity里面的每一个Fragment都会收到onPause().

但是，Fragment有另外一些生命周期方法，他们处理唯一的与Activity交互，为了执行一些动作如build（创建）和destoryed(销毁)fragment的UI。这些增加的回调方法是:

>* onAttach()
>  当Fragment与Activity发生来联系的时候调用。
>* onCreateView（）
> 创建与Fragment结合的View层次结构时调用
> * onActivityCreated()
> 当Activity的oncreate（）方法返回时调用。
> * onDestroyView（）
> 当与Fragment结合的View被移除的时候调用。
> * onDettach()
>  当Activity与fragment断开联系的时候调用。

Fragment的整个生命周期流，被它宿主的Activity所影响，
通过下面的图解表明，在这个图解中，你可以看见Activity里的每一个成功的状态决定着Fragment中哪些回调会被收到。例如，当一个Activity收到它的Activity回调，这个Activity里的Fragment不止会收到onActivityCreate()回调。

一旦Activity到达Resumed状态，你可以自由地添加或移除Fragment到Activity,因此，只有Activity在Resumed状态，Fragment的生命周期才能独立变化。

然而，当activity离开resumed状态，fragment再次被Activity推入自己的生命周期。

![alt text][id2]

[id2]:app/iamge/activity_fragment_lifecycle.png "Title"

#### Example(示例)

为了带来本文档所一起讨论的，这儿有一个Activity用两个fragment来创建一个two-pane（两个面板）的布局的示例，Activity包含一个显示莎士比亚剧本目录列表的Fragment和另一个显示剧本摘要当点击list时，它也展示了如何提供不同的fragment配置，基于屏幕的配置。
