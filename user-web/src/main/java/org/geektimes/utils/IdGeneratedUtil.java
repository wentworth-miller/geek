package org.geektimes.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 简单原子化操作
 * 
 * @author xubin
 * @date 2021/3/10 20:21
 */
public class IdGeneratedUtil {

    private static AtomicLong atomicLong = new AtomicLong(1);

    public static Long getNext() {
        return atomicLong.getAndIncrement();
    }
}
