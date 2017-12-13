package dev.majes.app.RxBusMsg;

import dev.majes.base.rxbus.IRxMsg;

/**
 * @author majes
 * @date 12/13/17.
 */

public interface TestRxMsg extends IRxMsg {
    String getTag();
    void setTag(String tag);
}
