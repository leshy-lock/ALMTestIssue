package org.alm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReentrantLocker implements EntityLocker {

    private final Map<Object, Mutex> storage = new ConcurrentHashMap<>();

    @Override
    public void lock(Object id, long lockTimeout) {
        Mutex mutex = getMutex(id);
        synchronized (mutex) {
            if (mutex.isTimedOut() && mutex.isLocked()) {
                mutex.unlock();
            }
        }
        mutex.lock(lockTimeout);
    }

    @Override
    public void unlock(Object id) {
        Mutex mutex = getMutex(id);
        synchronized (mutex) {
            if (mutex.isLocked()) {
                mutex.unlock();
            }
        }
    }

    private synchronized Mutex getMutex(Object id) {
        return storage.computeIfAbsent(id, key -> new Mutex());
    }

}
