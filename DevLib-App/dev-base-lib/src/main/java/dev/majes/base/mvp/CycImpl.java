package dev.majes.base.mvp;

import android.content.Context;

/**
 * @author majes
 * @date 12/11/17.
 */

public class CycImpl implements ICyc {

    private Context context;

    private CycImpl(Context context) {
        this.context = context;
    }

    public static ICyc create(Context context) {
        return new CycImpl(context);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {

    }
}
