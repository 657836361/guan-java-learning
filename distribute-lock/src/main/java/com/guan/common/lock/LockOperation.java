package com.guan.common.lock;

import java.util.concurrent.TimeUnit;

public interface LockOperation {

    boolean tryLock(String key);

    boolean tryLock(String key, long expire, TimeUnit timeUnit);

    void unlock(String key);
}
