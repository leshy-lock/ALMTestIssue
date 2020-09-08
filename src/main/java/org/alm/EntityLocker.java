package org.alm;

public interface EntityLocker {

    void lock(Object id, long lockTimeout);
    void unlock(Object id);

    default void lock(Object id) {
        lock(id, 0);
    }

}
