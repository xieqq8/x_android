2016-12-02
	|——转私有库
	|
2016-12-01

	AppCompatActivity
	butterknife
	gson
	自定义Toolbar

	底部加载 


2016-11-30 
	再次构架appxxx
	okhttputils

	目录结构整理
	|
	|——appxxx
	|———————cofig
	|———————ui
	|———————
	|———————
	|
	|——base 
	|
	|
	|——utils 工具类集合
	|	Gson 稳定
	|
	|——widget 可复用的view集合
	|
	|
	|——
	|
	|——
	|
	|——
	|
	|——

2016-07-01
	EventBus
	自定义弹出框
	DialogFragment 创建对话框  
 
2016-06-30
	下载、断点续传

2016-06-29	(over)
	软件更新
2016-06-28	(over)
	评价、分享可以用系统自带的
	评价
	Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	分享
	Intent sendIntent = new Intent();
	sendIntent.setAction(Intent.ACTION_SEND);
	sendIntent.setType("text/*");
2016-06-16	(over)
	listview  ITEM里面的点击与ITEM点击
	Item布局的根布局加上android:descendantFocusability=”blocksDescendants”

	beforeDescendants：viewgroup会优先其子类控件而获取到焦点
        afterDescendants：viewgroup只有当其子类控件不需要获取焦点时才获取焦点
        blocksDescendants：viewgroup会覆盖子类控件而直接获得焦点

2016-05-25	(over)
	基本架构

	|000 首页
	|001 002 003 004 启动页、登录、注册、找回密码
	|
	|--100 fragment 1
	|----110 111 112...... 业务分支1
	|----120 121 122......
	|--200 frgament 2
	|--300 fragment 3
	|
	|--400 fragment 4
	|

	DEMO

2016-05-05
	greenDAO、EventBus官网
	http://greenrobot.org/

2016-04-18
	进入退出动画 （over）
	进入 startActivity后加 overridePendingTransition
	退出 finish后加 overridePendingTransition
2016-04-08
	标题栏通透
	用

2016-03-22
	加载更多 (over)
	上拉会回到最上面 是因为没有用 notifyDataSetChanged

