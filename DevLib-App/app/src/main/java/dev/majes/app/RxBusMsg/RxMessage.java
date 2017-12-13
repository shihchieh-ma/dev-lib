package dev.majes.app.RxBusMsg;


/**
 * @author majes
 * @date 12/12/17.
 */

public class RxMessage implements TestRxMsg {
    private String tag;

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }
}
