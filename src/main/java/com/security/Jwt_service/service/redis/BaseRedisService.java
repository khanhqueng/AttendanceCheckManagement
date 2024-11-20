package com.security.Jwt_service.service.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface BaseRedisService<K,F,V> {
    void set(K key, V value);
    void setTimeToLive(K key, long timeoutInDays);
    void hashSet(K key, F field,V value);
    boolean hashExists(K key, F field);
    V get(K key);
    public Map<F, V> getField(K key);
    V hashGet(K key, F field);
    List<V> hashGetByFieldPrefix(K key, String prefix);
    Set<F> getFieldPrefix(K key);
    void delete(K key);
    void delete(K key, F field);
    void delete(K key, List<F> field);
    Set<K> getKeyByPattern(K pattern);
    boolean acquireLock(K lockKey,V value, long timeout, TimeUnit unit);
    void releaseLock(K lockKey);
}
