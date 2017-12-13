package dev.majes.base.utils;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import dev.majes.base.log.Log;

/**
 * Util
 * Created by D on 2017/10/25.
 */
public class RxUtil {

    /**
     * 打印当前代码所在线程信息
     */
    public static void printThread(String tag) {
        Log.d(tag + Thread.currentThread().getId() + "--NAME--" + Thread.currentThread().getName());
    }

    /**
     * 获取第一泛型类型 仅限Interface
     */
    public static <T> Class<T> getFirstCls(T t) {
        Type[] types = t.getClass().getGenericInterfaces();
        Type[] params = ((ParameterizedType) types[0]).getActualTypeArguments();
        Class<T> reponseClass = (Class) params[0];
        return reponseClass;
    }

}
