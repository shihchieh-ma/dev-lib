package cn.majes.dev_lib_app.view.activity;

import android.os.Bundle;
import android.os.CountDownTimer;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.TestRxBusMsg;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseActivity;
import dev.majes.base.router.Router;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author majes
 */
public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.framelayout_splash_activity;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Router.newIntent(SplashActivity.this)
                        .to(Main2Activity.class)
                        .launch();
            }
        }.start();
        registerRxBus(TestRxBusMsg.class, new Consumer<TestRxBusMsg>() {
            @Override
            public void accept(@NonNull TestRxBusMsg iRxMsg) throws Exception {
                Log.e(iRxMsg.getString());
                SplashActivity.this.finish();
            }
        });
    }

}
