Fragment
===========

readme

![alt text][id]

[id]:/app/src/main/res/drawable-hdpi/ic_launcher.png "Title"


fragment

    在你activity运行的任何时间，你都可以添加fragments到你的activity布局。你需要简单的指定一个ViewGroup来放你的fragment.
    要在activity中使用fragment事务（如:add(添加)，remove（移除），replace（替换）一个fragment）,你必须使用来自FragmentTransaction的API,你可以在Activity中得到一个FragmentTransaction实例，像这样:

  FragmentManager fragmentManager = getFragmentManager()

  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
