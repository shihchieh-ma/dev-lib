package cn.majes.dev_lib_app.entity;

import dev.majes.base.rxbus.IRxMsg;

/**
 * @author majes
 * @date 12/17/17.
 */

public class TestRxBusMsg2 implements IRxMsg {
    private String string;

    public TestRxBusMsg2(String string) {
        this.string = string;
    }

    public TestRxBusMsg2() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
