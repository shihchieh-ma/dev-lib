package dev.majes.base.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * @author majes
 * @date 12/11/17.
 */

public interface IView<P> {
    void bindUI(View rootView);

    void initData(Bundle savedInstanceState);

    int getLayoutId();

    P getP();
}
