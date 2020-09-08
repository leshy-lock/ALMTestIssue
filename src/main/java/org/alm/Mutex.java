package org.alm;

import java.util.concurrent.locks.ReentrantLock;

public class Mutex {

    private final ReentrantLock lock;
    private long lockTimeout; // zero means no time out
    private long lockedAt;

    Mutex() {
        lock = new ReentrantLock();
    }

    void lock(long lockTimeout) {
        synchronized (this) {
            this.lockTimeout = lockTimeout;
            this.lockedAt = System.currentTimeMillis();
        }
        lock.lock();
    }

    void unlock() {
        synchronized (this) {
            lock.unlock();
        }
    }

    boolean isLocked() {
        synchronized (this) {
            return lock.isLocked();
        }
    }

    boolean isTimedOut() {
        synchronized (this) {
            if (lockTimeout > 0) {
                return System.currentTimeMillis() > lockedAt + lockTimeout;
            } else {
                return false;
            }
        }
    }

}
