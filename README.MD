## devlib使用
两个module,一个dev-base-lib,一个dev-ui-lib,
包含mvp,rxjava2+retrofit2,rxbus,greendao等等，
具体请看demo，simple有网络数据，nodata只有界面。
</br>



**BaseActivity,BaseFragment,BaseLazyFragment**

BaseActivity,BaseFragment,BaseLazyFragment中通用方法,包含RxPermission,RxBus.泛型是你的presenter.
```
1 获取RxPermission直接getRxPermissions() 例如
getRxPermissions().request(Manifest.permission.CAMERA)
               .subscribe(granted -> {
                   if (granted) {
                       Log.e("检测到被授予权限，功能将可用。");
                   } else {
                       Log.e("检测到未被授予权限，功能将不可使用。");
                   }
               });
```
2 RxBus发送消息  TestRxBusMsg自定义 例如：
```
  RxBus.getIntanceBus().post(new TestRxBusMsg("this is test msg form main activity."));
```
3 注册RxBus 例如
```
  registerRxBus(TestRxBusMsg.class, new Consumer<TestRxBusMsg>() {
           @Override
           public void accept(@NonNull TestRxBusMsg iRxMsg) throws Exception {
               Log.e(iRxMsg.getString());
           }
       });
```
4 重写getP 返回你的presenter 例如
```
public class MyFragment extends BaseLazyFragment<MyPresenter>{
        @Override
        public MyPresenter getP() {
            return new MyPresenter();
    }
}
```
5 当返回了presenter后调用getCorrespondingP() 例如
```
  getCorrespondingP().loadData();
```
6 normal

普通activity继承BaseActivity实现的方法
``` @Override
    public void initData(Bundle savedInstanceState) {
        //findViewById,setListener，loadData等等操作
    }

    @Override
    public int getLayoutId() {
      //返回layout such as R.layout.activity_main
        return 0;
    }
```
普通fragment继承BaseFragment实现的方法
``` @Override
    public void bindUI(View rootView) {
        //findViewById eg:
        Recycleview recyclerView = rootView.findViewById(R.id.recycleview);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
      //数据，例如调用persenter.loadData()
    }

    @Override
    public int getLayoutId() {
      //同activity
        return 0;
    }
```
普通fragment继承BaseLazyFragment实现的方法
```
同BaseFragment处理
    @Override
    public void bindUI(View rootView) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
```

**Presenter**

继承BasePresenter，泛型是和其绑定的view

**Application**

需要继承DevLibApplication 里面初始化了数据库、Log设置、上拉加载，下拉刷新的默认header,footer、网络和CacheWebView。


**GreenDao使用**

已做安全升级处理 当DaoMaster等生成后 使用DaoManager 例如
```
DaoManager.getDaoManager().insertOrReplace(...);
```

**网络请求**

定义Api和ApiService类 例如
```
public class Api {
    public static final String API_BASE_URL = "------";

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    apiService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(ApiService.class);
                }
            }
        }
        return apiService;
    }
}

public interface ApiService {

    @GET
    Flowable<你的解析Bean> getData(...);
}
```
使用
```
>在presenter中getV()可以拿到与之绑定的view

Api.getApiService().getData(...)
                .compose(XApi.<你的解析Bean>getScheduler())
                .compose(getV().<你的解析Bean>bindToLifecycle())
                .subscribe(new ApiSubscriber<你的解析Bean>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(你的解析Bean entity) {
                      getV().你的方法;
                    }
                });
```

**Route**

跳转使用 支持动画等例如
```
Router.newIntent(this)
                .to(X.class)
                .putString(..., ...)
                .anim(...,...)
                .launch();
```

**常用Utils**

包含了一些常用的工具类 其中WindowUtils是用来判断有没有悬浮窗权限的，并且支持跳转到相应打开悬浮窗权限的位置，例如
```
if (!WindowUtils.checkFloatWindowPermission(this)){
            WindowUtils.showDialogTipUserRequestPermission(this);
}else {}
```
关于悬浮窗需要注意下8.0的变化：

<center class="half">
    <img src="https://github.com/Marlborn/dev-lib/blob/master/pic/windowmanager.png" width="260"/>
</center>

我在dev-ui-lib做过处理了，如果要使用悬浮窗改布局就行了，启动：
```
WindowManagerCtroller.getWindowManagerCtroller(this).createWindowView();
```
