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

> 提醒: 这个Activity的全部可用代码在FragmentLayout.java里面

主Activity像平常一样提供了一个布局，在onCreate（）时:

> @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_layout);
}

被应用的布局是fragment_layout.xml：
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
>    android:orientation="horizontal"
   android:layout_width="match_parent" android:layout_height="match_parent">

    <fragment class="com.example.android.apis.app.FragmentLayout$TitlesFragment"
            android:id="@+id/titles" android:layout_weight="1"
            android:layout_width="0px" android:layout_height="match_parent" />

    <FrameLayout android:id="@+id/details" android:layout_weight="1"
            android:layout_width="0px" android:layout_height="match_parent"
            android:background="?android:attr/detailsElementBackground" />

</LinearLayout>

这个布局文件指包含TitileFragment,也就是说，当设备是竖屏的时候,仅仅只有剧本的名字列表可见，因此，在这种配置下，当用户点击一个item的时候用户将会开启一个新的Activity来显示摘要，而不是加载第二个Activity.

然后，你可以看见在Fragment类里面是怎么完成的，首先是TitlesFragment,它显示了莎士比亚剧本目录列表，这个fragment继承于ListFragment,并且依赖它处理更多的listview的工作。

当你检查这段代码的时候，注意当用用户点击列表Item的时候，这儿可能有两种行为:取决与在布局在活动，它可以在一个Activity里创建和显示一个新的fragment来显示详细信息。或者开启一个新的activity.

public static class TitlesFragment extends ListFragment {
    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = DetailsFragment.newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.details, details);
                } else {
                    ft.replace(R.id.a_item, details);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}

第二个Fragment，显示从TitleFragment选择的item显示对应剧本的摘要。

public static class DetailsFragment extends Fragment {
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        text.setText(Shakespeare.DIALOGUE[getShownIndex()]);
        return scroller;
    }
}

从TitleFragment 调用，如果用户点击列表项，并且当前的布局不包括R.id.details 这个view,然后应用将会开启DetailActivity 来显示item的内容。

这儿是 DetailActivity,它简单的嵌入了DetailsFragment来显示选中的剧本的摘要内容，当屏幕是竖屏的时候。

public static class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            DetailsFragment details = new DetailsFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}

注意这个Activity finish掉了自己当配置横屏的时候，以至于主Activity可以接收和显示DetailsFragment在 TitleFragment的旁边。这个可以发生在用户以竖屏的方式开启DetailsActivity，然后转换为横屏时。

想要更多的Fragment使用的示例，请看App 示例 APIs Demos，在ApisDemos里.


###完

### 2014.9.22
### Fragment 高级应用

#### FragmentPagerAdapter

实现了PagerAdapter,演示的每一个页面作为一个Fragment,只要用户可以返回到这个页面,它就会持久的保存到Fragment Manager里。

这个版本的pager最好用于这儿有少量的特别的静态Fragment需要翻页。例如一个选项卡集合（Tabs），用户浏览的每一个Fragment page 都将会被保存到内存中去。尽管它的view 层次结构可能被销毁了当不可见的时候。这可能导致使用大量地重要的内存。从fragment 实例可以获取许多状态中的任意一个之后。对于更大的页面集合，考虑使用FragmentStatePagerAdapter.

当主viewPager使用FragmentPagerAdapter的时候，必须有一个可以的ID集

子类仅需实现getItem(int )和getCount（）来得到一个工作的 adapter.

这儿有一个例子实现了pager里包含系列Fragments.

#### FragmentStatePagerAdapter

实现了PagerAdapter，用一个Fragment来管理每一个page(页面),这个类也处理保存和重新存储Fragment的状态。

这个版本的pager对于这儿有大量的页面(page)时非常有用，工作时很像一个ListView，它们的全部的fragment都有可能被销毁，仅仅保留那些fragment被保存的状态。在页面之间切换的时候，相比与FragmentPagerAdapter更加平常的潜在花费。FragmentStatePagerAdapter允许pager持有更少的内存给相关连的每个浏览页面。

当使用FragmentStatePagerAdapter时，宿主viewPager必须有一个可用的ID集

子类仅需实现getItem(int )和getCount（）来得到一个工作的 adapter.

这儿有一个例子实现了pager里包含系列Fragments.


### Fragment源码学习文档
####Fragment实现添加、替换、移除操作的流程

 在Activity中，要操作Fragment，必须拿到一个Fragment的管理器FragmentManager,在Activity中，可以通过getFragmentManager()或者getSupportFragmentManager（）来得到一个FragmentManager的实例。我们得到的实际上是一个FragmentManager的子类实例FragmentManagerImpl.

 关键代码:
  final FragmentManagerImpl mFragments = new FragmentManagerImpl();
   /**
     * Return the FragmentManager for interacting with fragments associated
     * with this activity.
     */
    public FragmentManager getSupportFragmentManager() {
        return mFragments;
    }


   关键代码:
        @Override
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

  而返回的是一个继承FragmentTransaction的子类实例BackStackRecord,
   BackStackRecord实现了FragmentManager.BackStackEntry和Runnable，重写了run方法。BackStackRecord里面有一个重要的类Op，Op就是记录每一个操作，如add(),remove(),replace().

  关键代码:

   static final class Op {
        Op next;
        Op prev;
        int cmd;
        Fragment fragment;
        int enterAnim;
        int exitAnim;
        int popEnterAnim;
        int popExitAnim;
        ArrayList<Fragment> removed;
    }


  BackStackRecord 还有一些其他属性:

    Op mHead;//记录操作的链表

    Op mTail;

    //操作命令
    static final int OP_NULL = 0;

    static final int OP_ADD = 1;

    static final int OP_REPLACE = 2;

    static final int OP_REMOVE = 3;

    static final int OP_HIDE = 4;

    static final int OP_SHOW = 5;

    static final int OP_DETACH = 6;
    static final int OP_ATTACH = 7;
   //动画
    int mEnterAnim;
    int mExitAnim;
    int mPopEnterAnim;
    int mPopExitAnim;
    int mTransition;
    int mTransitionStyle;

   boolean mAddToBackStack;//是否加入回退栈
   boolean mAllowAddToBackStack = true;

 弄清了BackStackRecord的结构之后，来看看它是怎么执行具体操作的，这里有两个重要的方法，addOp()和doAddOp()：

  关键代码:

    void addOp(Op op) {
        if (mHead == null) {
            mHead = mTail = op;
        } else {
            op.prev = mTail;
            mTail.next = op;
            mTail = op;
        }
        op.enterAnim = mEnterAnim;
        op.exitAnim = mExitAnim;
        op.popEnterAnim = mPopEnterAnim;
        op.popExitAnim = mPopExitAnim;
        mNumOp++;
    }
    这就是添加一个操作的核心代码。还有一个doAddOp（）:

    private void doAddOp(int containerViewId, Fragment fragment, String tag, int opcmd) {
        fragment.mFragmentManager = mManager;

        if (tag != null) {
            if (fragment.mTag != null && !tag.equals(fragment.mTag)) {
                throw new IllegalStateException("Can't change tag of fragment "
                        + fragment + ": was " + fragment.mTag
                        + " now " + tag);
            }
            fragment.mTag = tag;
        }

        if (containerViewId != 0) {
            if (fragment.mFragmentId != 0 && fragment.mFragmentId != containerViewId) {
                throw new IllegalStateException("Can't change container ID of fragment "
                        + fragment + ": was " + fragment.mFragmentId
                        + " now " + containerViewId);
            }
            fragment.mContainerId = fragment.mFragmentId = containerViewId;
        }

        Op op = new Op();
        op.cmd = opcmd;
        op.fragment = fragment;
        addOp(op);
    }
这些所有的操作其实都是调用这两个方法来完成，例如:

 添加操作:

>    public FragmentTransaction add(int containerViewId, Fragment fragment, String tag) {
        doAddOp(containerViewId, fragment, tag, OP_ADD);
        return this;
    }

   替换操作replace：

   > public FragmentTransaction replace(int containerViewId, Fragment fragment, String tag) {
        if (containerViewId == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        doAddOp(containerViewId, fragment, tag, OP_REPLACE);
        return this;
    }

最后是提交，调用commit（）方法提交

 关键代码；

  public int commit() {
        return commitInternal(false);
    }

    public int commitAllowingStateLoss() {
        return commitInternal(true);
    }

    int commitInternal(boolean allowStateLoss) {
        if (mCommitted) throw new IllegalStateException("commit already called");
        if (FragmentManagerImpl.DEBUG) {
            Log.v(TAG, "Commit: " + this);
            LogWriter logw = new LogWriter(TAG);
            PrintWriter pw = new PrintWriter(logw);
            dump("  ", null, pw, null);
        }
        mCommitted = true;
        if (mAddToBackStack) {
            mIndex = mManager.allocBackStackIndex(this);
        } else {
            mIndex = -1;
        }
        mManager.enqueueAction(this, allowStateLoss);
        return mIndex;
    }

  有两种提交方式，不允许丢失的用commit（）提交，允许丢失的用commitAllowingStateLoss（）提交。提交之后会把事务放入FragmentManager的执行队列中去执行。任务队列中有把Runnbale添加到一个List里面.然后调用Handler执行

关键代码：
           mPendingActions.add(action);
            if (mPendingActions.size() == 1) {
                mActivity.mHandler.removeCallbacks(mExecCommit);
                mActivity.mHandler.post(mExecCommit);
            }

 Runnable mExecCommit = new Runnable() {
        @Override
        public void run() {
            execPendingActions();
        }
    };

然后在 execPendingActions();方法里面遍历Runnable List，执行他们的run（）方法，就是BackStackRecord里面的run（）方法，最终区执行添加的任务。
关键代码:
   for (int i=0; i<numActions; i++) {
                mTmpActions[i].run();//执行run方法
                mTmpActions[i] = null;
            }

 BackStackRecord的run（）方法里实现了真正的添加、移除、替换等这些操作，他是遍历mHead这个双向链表来完成的。

以上就是Fragment的add,remove,replace等操作的实现流程。


### 2014.9.24

#### Back Stack

我们在使用Fragment的时候，如果在提交了一个 Fragment Transaction 之前调用了addToBackStack(null),就会把当前的Transaction放入回退栈，当用户按下手机上的返回键的时候，就可以回到以前的一个状态。接下来看一下实现的源码:

首先，Activity里面有一个返回键的监听，监听到用户按下返回键时，会调用一个onBackPressed()方法，

#### 关键代码:
 /**
     * Take care of calling onBackPressed() for pre-Eclair platforms.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (android.os.Build.VERSION.SDK_INT < 5 /* ECLAIR */
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

/**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    public void onBackPressed() {
        if (!mFragments.popBackStackImmediate()) {
            finish();
        }
}

如果返回 false ，直接finish掉当前的Activity,然后会执行popBackStackState(mActivity.mHandler, null, -1, 0);方法，在这个方法里回调用popFromBackStack（true）;

##### 关键代码:

final BackStackRecord bss = mBackStack.remove(last);
            bss.popFromBackStack(true);


在popFromBackStack（）方法里，会循环记录操作的链表，拿出每一个操作的类型，然后做相反的操作，如：如果是add,现在就做Remove操作，如果是show,现在就做hide操作，如果是Replace，先remove当前的Fragment，然后依次拿出Op里removed List里面的所有Fragment，依次做添加操作。

##### 关键代码:

public void popFromBackStack(boolean doStateMove) {
        if (FragmentManagerImpl.DEBUG) {
            Log.v(TAG, "popFromBackStack: " + this);
            LogWriter logw = new LogWriter(TAG);
            PrintWriter pw = new PrintWriter(logw);
            dump("  ", null, pw, null);
        }

        bumpBackStackNesting(-1);

        Op op = mTail;
        while (op != null) {
            switch (op.cmd) {
                case OP_ADD: {
                    Fragment f = op.fragment;
                    f.mNextAnim = op.popExitAnim;
                    mManager.removeFragment(f,
                            FragmentManagerImpl.reverseTransit(mTransition),
                            mTransitionStyle);
                } break;
                case OP_REPLACE: {
                    Fragment f = op.fragment;
                    if (f != null) {
                        f.mNextAnim = op.popExitAnim;
                        mManager.removeFragment(f,
                                FragmentManagerImpl.reverseTransit(mTransition),
                                mTransitionStyle);
                    }
                    if (op.removed != null) {
                        for (int i=0; i<op.removed.size(); i++) {
                            Fragment old = op.removed.get(i);
                            old.mNextAnim = op.popEnterAnim;
                            mManager.addFragment(old, false);
                        }
                    }
                } break;
                case OP_REMOVE: {
                    Fragment f = op.fragment;
                    f.mNextAnim = op.popEnterAnim;
                    mManager.addFragment(f, false);
                } break;
                case OP_HIDE: {
                    Fragment f = op.fragment;
                    f.mNextAnim = op.popEnterAnim;
                    mManager.showFragment(f,
                            FragmentManagerImpl.reverseTransit(mTransition), mTransitionStyle);
                } break;
                case OP_SHOW: {
                    Fragment f = op.fragment;
                    f.mNextAnim = op.popExitAnim;
                    mManager.hideFragment(f,
                            FragmentManagerImpl.reverseTransit(mTransition), mTransitionStyle);
                } break;
                case OP_DETACH: {
                    Fragment f = op.fragment;
                    f.mNextAnim = op.popEnterAnim;
                    mManager.attachFragment(f,
                            FragmentManagerImpl.reverseTransit(mTransition), mTransitionStyle);
                } break;
                case OP_ATTACH: {
                    Fragment f = op.fragment;
                    f.mNextAnim = op.popEnterAnim;
                    mManager.detachFragment(f,
                            FragmentManagerImpl.reverseTransit(mTransition), mTransitionStyle);
                } break;
                default: {
                    throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                }
            }

            op = op.prev;
        }








